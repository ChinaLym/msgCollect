package com.msgc.controller;

import com.msgc.constant.SessionKey;
import com.msgc.constant.response.ResponseWrapper;
import com.msgc.entity.Group;
import com.msgc.entity.User;
import com.msgc.entity.dto.GroupDTO;
import com.msgc.service.IGroupService;
import com.msgc.service.IGroupToUserService;
import com.msgc.service.IUserService;
import com.msgc.utils.JsonUtil;
import com.msgc.utils.RandomHeadImageUtil;
import com.msgc.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
*Type: GroupController
* Description: 组相关
* @author LMM
 */
@Controller
@RequestMapping("/group")
public class GroupController {

	private final IGroupService groupService;
	private final IGroupToUserService groupToUserService;
	private final IUserService userService;

	@Autowired
	public GroupController(IGroupService groupService, IGroupToUserService groupToUserService, IUserService userService) {
		this.groupService = groupService;
		this.groupToUserService = groupToUserService;
		this.userService = userService;
	}

	@GetMapping(value = {"", "/my"})
    public String myGroupPage(Model model){
		HttpSession session = SpringUtil.getRequest().getSession();
		User user = (User)session.getAttribute(SessionKey.USER);
		List<Group> myGroupList = groupService.findByMasterId(user.getId());
		model.addAttribute("myGroupList", myGroupList);
    	return "group/myGroup";
	}

	@GetMapping(value = {"/detail/{groupId}"})
    public String myGroupPage(@PathVariable("groupId") Integer groupId, Model model){
		Group group = groupService.findById(groupId);
		GroupDTO groupDTO = new GroupDTO(group);
		User user = userService.findById(group.getId());
		groupDTO.setMasterName(user.getNickname());
		model.addAttribute("group", groupDTO);
    	return "group/groupDetail";
	}

	@Transactional
	@PostMapping(value = {"/new.action"})
    public String createNewGroup(Model model){
		HttpServletRequest request = SpringUtil.getRequest();
		User user = (User)request.getSession().getAttribute(SessionKey.USER);
		Group group = new Group();
		group.setName(request.getParameter("newGroupName"));
		group.setMemberNumber(1);
		group.setMasterId(user.getId());
		group.setCreateTime(new Date());
		group.setIcon(RandomHeadImageUtil.next());
		group = groupService.createNewGroup(group);
		groupToUserService.newRelationShip(user, group);
		model.addAttribute("group", group);
		return "group/groupDetail";
	}

	@PostMapping(value = {"/join.action/{groupId}"})
	public String joinGroup(@PathVariable Integer groupId){
		Group group = groupService.findById(groupId);
		HttpServletRequest request = SpringUtil.getRequest();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(SessionKey.USER);
		groupToUserService.newRelationShip(user, group);
		//TODO 改为管理员同意
		return JsonUtil.toJson(ResponseWrapper.success("加入成功！"));
	}

}
