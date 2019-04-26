package com.msgc.controller;

import com.msgc.config.WebMvcConfig;
import com.msgc.constant.SessionKey;
import com.msgc.constant.enums.MessageTypeEnum;
import com.msgc.constant.enums.TableStatusEnum;
import com.msgc.entity.*;
import com.msgc.entity.dto.TableDTO;
import com.msgc.service.*;
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
import java.util.Comparator;
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
    private final IMessageService messageService;

    @Autowired
    public BaseController(IUserService userService, IUserCookieService userCookieService, ITableService tableService, IUnfilledRecordService unfilledRecordService, IMessageService messageService) {
        this.userService = userService;
        this.userCookieService = userCookieService;
        this.tableService = tableService;
        this.unfilledRecordService = unfilledRecordService;
        this.messageService = messageService;
    }


    @GetMapping("/")
    public String defaultPage() {
        return "redirect:login";
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, HttpSession httpSession) {
        if (StringUtils.isNotEmpty(request.getParameter("redirectUrl")))
            httpSession.setAttribute(SessionKey.REDIRECT_URL, request.getParameter("redirectUrl"));
        String autoLoginCookie = WebUtil.getCookie("auto-login");
        // 有自动登录的 cookie
        if(autoLoginCookie != null){
            UserCookie cuMapping = userCookieService.findByCookie(autoLoginCookie);
            if(cuMapping != null){
                int userId = cuMapping.getUserId();
                long nowTime = System.currentTimeMillis();
                // cookie 有效
                if(nowTime < cuMapping.getExpirationDate().getTime()){
                    User u = userService.findById(userId);
                    if(u != null){
                        HttpSession session = WebUtil.setSessionUser(u);
                        if (StringUtils.isNotEmpty(request.getParameter("redirectUrl"))){
                            String redirect = "redirect:" + request.getParameter("redirectUrl");
                            session.removeAttribute(SessionKey.REDIRECT_URL);
                            return redirect;
                        }
                        return "redirect:/index";
                    }
                }else {
                    // 自动登录凭证 过期，从数据库删除
                    userCookieService.deleteByCookie(autoLoginCookie);
                }
            }
        }
        // 没有自动登录成功，需要跳转到登录页面
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
        User user = (User)session.getAttribute(SessionKey.USER);
        // 查询未读消息
        List<Message> unReadMessageList = messageService.findUnreadByReceiverAndLimit(user.getId(), 5);

        // 找出所有待填写的收集表
        List<UnfilledRecord> recordList = unfilledRecordService.findAllByUserId(user.getId());
        List<Integer> unfilledTableIdList = recordList.stream()
                .map(UnfilledRecord::getTableId)
                .collect(Collectors.toList());
        List<Table> unfilledTableList = tableService.findAllById(unfilledTableIdList);
        // 从收藏中移除不可填写的表记录（状态为已经截止,或者删除）
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
        List<TableDTO> unfilledTableDTOList = tableService.constructTableDTO(accessibleUnfilledTableList);
        // 近期收集
        Table tableExample = new Table();
        tableExample.setOwner(user.getId());
        tableExample.setState(TableStatusEnum.COLLECTING.getValue());
        List<Table> tableList = tableService.findAllByExample(tableExample);
        List<Table> recentCollectList = new LinkedList<>();
        //将已超出截止时间，但状态未变更的表状态变更为已截止
        long nowTime = System.currentTimeMillis();
        List<Table> endTableList = new LinkedList<>();
        tableList.forEach(table -> {
            if(table.getEndTime().getTime() < nowTime){
                table.setState(TableStatusEnum.END.getValue());
                endTableList.add(table);
                //发送消息提醒用户表截止
                messageService.sendMessage(MessageTypeEnum.TABLE_END, table);
            }else {
                recentCollectList.add(table);
            }
        });
        recentCollectList.sort(Comparator.comparing(Table::getEndTime));
        if (endTableList.size() > 0){
            //回写数据库，保存已截止状态
        }
        tableService.save(endTableList);
        session.setAttribute(SessionKey.UNREAD_MESSAGE, unReadMessageList);
        session.setAttribute(SessionKey.RECENT_COLLECT, recentCollectList);
        model.addAttribute("tableList", unfilledTableDTOList);
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
        //TODO 验证表存在
        //验证身份是否是表的拥有者 先 session 后 cookie ，都不通过则需要登录
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
        model.addAttribute("tableList", tableDTOList);
        return "collect/searchResult";
    }
}
