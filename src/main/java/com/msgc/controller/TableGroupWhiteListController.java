package com.msgc.controller;

import com.msgc.service.ITableGroupWhiteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
*Type: TableGroupWhiteListController
* Description: 收集表 - 组 关系
* @author LMM
 */
@Controller
public class TableGroupWhiteListController {
	@Autowired
	private ITableGroupWhiteListService tablegroupwhitelistService;
	
    

}
