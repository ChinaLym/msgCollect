package com.msgc.controller;

import com.msgc.config.LoggedUserSessionContext;
import com.msgc.constant.FilePath;
import com.msgc.constant.SessionKey;
import com.msgc.constant.response.ResponseWrapper;
import com.msgc.entity.AnswerRecord;
import com.msgc.entity.Table;
import com.msgc.entity.User;
import com.msgc.entity.UserCookie;
import com.msgc.entity.dto.RecentTable;
import com.msgc.exception.ResourceNotFoundException;
import com.msgc.service.IAnswerRecordService;
import com.msgc.service.ITableService;
import com.msgc.service.IUserCookieService;
import com.msgc.service.IUserService;
import com.msgc.utils.*;
import com.msgc.utils.csv.CsvUtilAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
*Type: UserController
* Description: 用户模块
* @author LMM
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	private final IUserService userService;
	private final IUserCookieService userCookieService;
	private final ITableService tableService;
	private final IAnswerRecordService answerRecordService;

	@Autowired
	public UserController(IUserCookieService userCookieService, IUserService userService, ITableService tableService, IAnswerRecordService answerRecordService) {
		this.userCookieService = userCookieService;
		this.userService = userService;
		this.tableService = tableService;
		this.answerRecordService = answerRecordService;
	}

	/**
	 * 登录
	 * @param userParam 接收传来的 user
     * @param model 设置提示信息
	 * @return 重定向至首页
	 */
    @PostMapping(value = "/login.action")
    public String login(@ModelAttribute User userParam, Model model) {
        User user = userService.login(userParam);
        if(user == null) {
            // 登录失败，返回登录界面
            model.addAttribute("msg", "账号或密码错误");
            return "login";
        } else {
            // 登录成功
			//勾选了自动登录
			if("on".equalsIgnoreCase(WebUtil.getRequest().getParameter("autoLogin"))){
				try{
					UserCookie mapping = new UserCookie();
					mapping.setCookie(UUID.randomUUID().toString().replace("-", ""));
					mapping.setUserId(user.getId());
					// 保存至数据库
					long twoWeeks_milli = 1000 * 60 * 60 * 24 * 14;
					mapping.setExpirationDate(new Date(System.currentTimeMillis() + twoWeeks_milli));
					userCookieService.save(mapping);
					// 将凭据保存至浏览器 cookie
					int twoWeeks_second = 60 * 60 * 24 * 14;
					WebUtil.setCookie(SessionKey.AUTO_LOGIN, mapping.getCookie(), twoWeeks_second);
				}catch (Exception e){
					e.printStackTrace();
					//写数据库 cookieUserMapping 失败
				}
			}
			HttpSession session = WebUtil.setSessionUser(user);
            if(session.getAttribute(SessionKey.REDIRECT_URL) != null){
				String redirect = "redirect:" + String.valueOf(session.getAttribute(SessionKey.REDIRECT_URL));
				session.removeAttribute(SessionKey.REDIRECT_URL);
            	return redirect;
			}
            return "redirect:/index";
        }
    }

	/**
	 * 用户注册
	 * @param user	接收的用户类
	 * @param model	视图返回字段
	 * @return 原本请求的 url 或 index
	 */
	@PostMapping(value = "/register.action")
	public String register(@ModelAttribute User user, Model model) {
		User registerUser = userService.register(user);
		if(registerUser == null) {
			// 注册失败
			model.addAttribute("msg", "账号已存在");
			return "register";
		} else {
			// 注册成功
			HttpSession session = WebUtil.getRequest().getSession();
			session.setAttribute(SessionKey.USER, user);
			LoggedUserSessionContext.putIfAbsent(user.getId(), session);
			if(session.getAttribute(SessionKey.REDIRECT_URL) != null){
				String redirect = "redirect:" + String.valueOf(session.getAttribute(SessionKey.REDIRECT_URL));
				session.removeAttribute(SessionKey.REDIRECT_URL);
				return redirect;
			}
			return "redirect:/index";
		}
	}


	// 退出登录，清空 session 中的 user, 清除cookie
	@GetMapping(value = "/logout.action")
	public String logout(HttpSession session) {
		User user = (User)session.getAttribute(SessionKey.USER);
		userCookieService.deleteByCookie(WebUtil.getCookie(SessionKey.AUTO_LOGIN));
		LoggedUserSessionContext.remove(user.getId());
		WebUtil.expireCookie(SessionKey.AUTO_LOGIN);
		session.invalidate();
		return "redirect:/login";
	}

    //到个人中心页profile
    @GetMapping(value = "/profile")
    public String profilePage() {
        return "user/profile";
    }

    //到他人介绍页 info
    @GetMapping(value = "/info/{userId}")
    public String infoPage(@PathVariable("userId") Integer userId, Model model) {
		User aimUser = userService.findById(userId);
		if(aimUser == null)
			throw new ResourceNotFoundException();
		int limitNum = 5;
		// ta 最近发起的5个表
		List<Table> recentCreateTableList = tableService.findRecentCreateByOwner(userId, limitNum);
		// 最近参与的 5 条记录
		List<AnswerRecord> recentFilledRecordList = answerRecordService.findRecentCreateByUserId(userId, limitNum);
		Map<Integer, AnswerRecord> recordMap = new HashMap<>(recentFilledRecordList.size());
		recentFilledRecordList.forEach(t -> {
			recordMap.put(t.getTableId(), t);
		});
		// Map
		// 根据这些记录找出相关表
		List<Table> recentFilledTableList = tableService.findAllById(new ArrayList<>(recordMap.keySet()));
		List<RecentTable> recentTableList = new ArrayList<>(recentCreateTableList.size() + recentFilledTableList.size());
		recentCreateTableList.forEach(t -> {
			recentTableList.add(new RecentTable(t));
		});
		recentFilledTableList.forEach(t -> {
			recentTableList.add(new RecentTable(t, recordMap.get(t.getId()).getUpdateTime()));
		});
		recentTableList.sort(new Comparator<RecentTable>() {
			@Override
			public int compare(RecentTable o1, RecentTable o2) {
				// 倒序排序
				return -o1.getOperateTime().compareTo(o2.getOperateTime());
			}
		});

		// 三类：表主人，正在收集
		// 表主人，已截止
		// 非表主人，正在收集
		// 非表主人，已截止
		// 按照创建日期的年份分组 CreteY, list(group)_index
		/*Map<Integer, Integer> map = new HashMap<>(limitNum);
		List<List<Table>> tableListList = new ArrayList<>(limitNum);
		int index = 0;
		for(Table t : recentCreateTableList){
			int createYear = t.getCreateTime().getYear();
			if(map.containsKey(createYear)){
				tableListList.get(map.get(createYear)).add(t);
			}else {
				List<Table> tableList = new ArrayList<>(limitNum);
				tableList.add(t);
				tableListList.add(tableList);
				map.put(createYear, index ++);
			}
		}*/
		model.addAttribute("aimUser", aimUser);
		model.addAttribute("recentTableList", recentTableList);
        return "user/info";
    }

    // 到修改密码页
    @GetMapping(value = "/changepasswd")
    public String changePasswdPage() {
        return "user/modifyPassword";
    }

	// 修改密码提交
	@ResponseBody
	@PostMapping(value = "/changepasswd.action")
    public String changePasswd() {
		HttpServletRequest request = WebUtil.getRequest();
		User user = (User)request.getSession().getAttribute(SessionKey.USER);
		if(user != null){
			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");
			if(user.getPassword().equals(oldPassword) && RegexCheckUtils.checkUserPassword(newPassword)){
				user.setPassword(newPassword);
				user = userService.save(user);
				request.getSession().setAttribute(SessionKey.USER, user);
				return JsonUtil.toJson(ResponseWrapper.success("ok"));
			}
            return JsonUtil.toJson(ResponseWrapper.fail("密码格式错误或不正确"));
		}
        return JsonUtil.toJson(ResponseWrapper.fail("未登录"));
    }

    // 修改个人信息
	@PostMapping(value = "/updateProfile.action")
    public String updateProfile(User userParam) {
		HttpSession session = WebUtil.getRequest().getSession();
		User user = (User)session.getAttribute(SessionKey.USER);
		if(user != null){
			if(StringUtils.isNotBlank(userParam.getHeadImage())){
				user.setHeadImage(userParam.getHeadImage());
			}
			if(StringUtils.isNotBlank(userParam.getNickname())){
				user.setNickname(userParam.getNickname());
			}
			//user.setRealname(userParam.getRealname());
			//user.setSex(userParam.getSex());
			//user.setTel(userParam.getTel());
			//user.setIdcard(userParam.getIdcard());
			user.setQq(userParam.getQq());
			user.setWechat(userParam.getWechat());
			user.setEmail(userParam.getEmail());
			user = userService.save(user);
			session.setAttribute(SessionKey.USER, user);
			return "user/profile";
		}
		return "login";
    }

	@PostMapping("/updateHeadImage.action")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public String cropper(@RequestParam("base64Image") String base64Image) throws IOException {

		String imageName = UUID.randomUUID().toString() + ".jpg";
		String filePath = FilePath.UPLOAD_HEAD_IMAGE_FULL_DIR + imageName;
		FileUtil.ensureExist(new File(filePath));
		try (FileOutputStream out = new FileOutputStream(filePath)){
			byte[] imageBytes = FileUtil.getByteFromBase64(base64Image);
			// 如果过大则先压缩
			out.write(imageBytes);
			String imageUrl = FilePath.getHeadImageUrl(imageName);
			User user = (User)WebUtil.getSessionKey(SessionKey.USER);
			String oldHeadUrl = user.getHeadImage();
			// 保存到数据库，更新session
			user.setHeadImage(imageUrl);
			WebUtil.getSession().setAttribute(SessionKey.USER, userService.save(user));
			if(!oldHeadUrl.startsWith(FilePath.BASE_HEAD_IMAGE_URL_PERFFIX)){
				// 找到原头像实际文件，异步删除。
				String oldHeadImageFilePath = FilePath.UPLOAD_HEAD_IMAGE_FULL_DIR + oldHeadUrl.substring(FilePath.UPLOAD_HEAD_IMAGE_URL_PREFFIX.length());
				File oldImage = new File(oldHeadImageFilePath);
				oldImage.delete();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return "success";
	}

//---------------------------------------------------
    // 批量导入，只测试用
	@ResponseBody
	@GetMapping(value = "/import")
    public String importUsers(){
		HttpSession session = WebUtil.getRequest().getSession();
		User user = (User)session.getAttribute(SessionKey.USER);
		if(!user.getId().equals(1)){
			throw new ResourceNotFoundException();
		}
		List<User> nonExistUserList;
		try {
			List<User> userList = CsvUtilAdapter.read("C:\\Users\\14278\\Desktop\\importUser.csv");
			List<User> allUser = userService.findAll();
			Map<String, User> allUserMap = new HashMap<>(userList.size() + allUser.size());
			allUser.forEach(u -> {
				if (u != null)
					allUserMap.put(u.getAccount(), u);
			});
			nonExistUserList = userList.stream().filter(u -> !allUserMap.containsKey(u.getAccount())).collect(Collectors.toList());
			userService.save(nonExistUserList);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("保存失败");
		}
		return "saved " + nonExistUserList.size() + " records: \n" + nonExistUserList.toString();
	}

	// 更新所有用户的头像 仅测试使用
	@ResponseBody
	@GetMapping(value = "/updateHeadImage")
    public String updateHeadImage(){
		HttpSession session = WebUtil.getRequest().getSession();
		User user = (User)session.getAttribute(SessionKey.USER);
		if(!user.getId().equals(1)){
			throw new ResourceNotFoundException();
		}
		List<User> userList = userService.findAll();
		userList.forEach(u -> u.setHeadImage(RandomHeadImageUtil.next()));
		userService.save(userList);
		return "ok";
	}

}
