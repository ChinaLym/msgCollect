package com.msgc.controller;

import com.msgc.service.IGroupToUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
*Type: GroupToUserController
* Description: 组 - 用户 关系 香瓜吗
* @author LMM
 */
@Controller
public class GroupToUserController {
	@Autowired
	private IGroupToUserService grouptouserService;
	
    
    
}
