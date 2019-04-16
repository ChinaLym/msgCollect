package com.msgc.controller;

import com.msgc.config.WebMvcConfig;
import com.msgc.constant.SessionKey;
import com.msgc.constant.enums.TableStatusEnum;
import com.msgc.entity.*;
import com.msgc.entity.dto.TableDTO;
import com.msgc.service.ITableService;
import com.msgc.service.IUnfilledRecordService;
import com.msgc.service.IUserCookieService;
import com.msgc.service.IUserService;
import com.msgc.utils.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Type: BaseController
 * Description: 基本跳转
 *
 * @author LMM
 */
@Controller
public class BaseController {

    private final IUserService userService;
    private final IUserCookieService userCookieService;
    private final ITableService tableService;
    private final IUnfilledRecordService unfilledRecordService;

    @Autowired
    public BaseController(IUserService userService, IUserCookieService userCookieService, ITableService tableService, IUnfilledRecordService unfilledRecordService) {
        this.userService = userService;
        this.userCookieService = userCookieService;
        this.tableService = tableService;
        this.unfilledRecordService = unfilledRecordService;
    }


    @GetMapping("/")
    public String defaultPage() {
        return "redirect:login";
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, HttpSession session) {
        if (StringUtils.isNotEmpty(request.getParameter("redirectUrl")))
            session.setAttribute(SessionKey.REDIRECT_URL, request.getParameter("redirectUrl"));
        String autoLoginCookie = WebUtil.getCookie("auto-login");
        if(autoLoginCookie != null){
            UserCookie cuMapping = userCookieService.findByCookie(autoLoginCookie);
            if(cuMapping != null){
                int userId = cuMapping.getUserId();
                long nowTime = System.currentTimeMillis();
                if(nowTime < cuMapping.getExpirationDate().getTime()){
                    User u = userService.findById(userId);
                    if(u != null){
                        session.setAttribute(SessionKey.USER, u);
                        if(session.getAttribute(SessionKey.REDIRECT_URL) != null){
                            String redirect = "redirect:" + String.valueOf(session.getAttribute(SessionKey.REDIRECT_URL));
                            session.removeAttribute(SessionKey.REDIRECT_URL);
                            return redirect;
                        }
                        return "redirect:/index";
                    }
                }else {
                    userCookieService.deleteByCookie(autoLoginCookie);
                }
            }
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(HttpServletRequest request, HttpSession session) {
        if (request.getParameter("redirectUrl") != null && !request.getParameter("redirectUrl").isEmpty())
            session.setAttribute(SessionKey.REDIRECT_URL, request.getParameter("redirectUrl"));
        return "register";
    }

    @GetMapping(value = {"/index", "/main"})
    public String indexPage(HttpSession session, Model model) {

        //TODO 查询未读消息
        Message unMsg = new Message();
        List<Message> unReadMessageList = new ArrayList<>();

        User user = (User)session.getAttribute(SessionKey.USER);
        //找出所有待填写的收集表
        List<UnfilledRecord> recordList = unfilledRecordService.findAllByUserId(user.getId());
        List<Integer> unfilledTableIdList = recordList.stream()
                .map(UnfilledRecord::getTableId)
                .collect(Collectors.toList());
        List<Table> unfilledTableList = tableService.findAllById(unfilledTableIdList);
        //从收藏中移除不可填写的表记录（状态为已经截止,或者删除）
        List<Integer> inAccessibleTableIdList = new LinkedList<>();
        List<Table> accessibleUnfilledTableList = new LinkedList<>();
        unfilledTableList.forEach(table -> {
            if(!TableStatusEnum.COLLECTING.equal(table.getState())){
                inAccessibleTableIdList.add(table.getId());
            }else{
                accessibleUnfilledTableList.add(table);
            }
        });
        unfilledRecordService.deleteByTableIds(inAccessibleTableIdList);
        List<TableDTO> resultList = tableService.constructTableDTO(accessibleUnfilledTableList);
        //近期收集
        List<Table> recentCollectList = new LinkedList<>();

        session.setAttribute(SessionKey.UNREAD_MESSAGE, unReadMessageList);
        session.setAttribute(SessionKey.RECENT_COLLECT, recentCollectList);
        model.addAttribute("tableList", resultList);
        return "main";
    }

    /**
     * 转发下载资源请求，为了不让用户看到真正的虚拟路径
     * @return 转至下载资源的真正路径
     */
    @RequestMapping("/api/v1/download/table/{tableId}/field/{fieldIdAndName}/{fileName}")
    public String downloadResource(@PathVariable("tableId") String tableId,
                                   @PathVariable("fieldIdAndName") String fieldIdAndName,
                                   @PathVariable("fileName") String fileName){
        //验证表存在************************
        //验证身份是否是表的拥有者 先 session 后 cookie
        return "forward:" + WebMvcConfig.VIRTUL_DIR +
                "upload/" + tableId + "/" + fieldIdAndName + "/" + fileName;
        //返回 permission deny
    }

    @GetMapping("/settings")
    public String settingsPage() {
        return "settings/settings";
    }

    @RequestMapping("/search")
    public String searchResultPage(String tableName, Model model) {
        //若查询名称为空，则返回空结果
        List<TableDTO> tableDTOList = new ArrayList<>();
        if(StringUtils.isNotBlank(tableName)){
            List<Table> tableList = tableService.searchByNameAndState(tableName, TableStatusEnum.COLLECTING);
            tableDTOList = tableService.constructTableDTO(tableList);
        }
        model.addAttribute("tableList",tableDTOList);
        return "collect/searchResult";
    }
}
