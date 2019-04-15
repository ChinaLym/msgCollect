package com.msgc.controller;

import com.msgc.constant.response.ResponseWrapper;
import com.msgc.service.ICommentService;
import com.msgc.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
*Type: CommentController
* Description: 评论相关
* @author LMM
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private ICommentService commentService;

	/**
	 * 添加一条评论
	 * @param tableId 评论表 Id
	 * @return
	 */
	@PostMapping("/table/{tableId}")
	@ResponseBody
	public String addComment(@PathVariable("tableId") Integer tableId){
		commentService.addComment(tableId);
		return JsonUtil.toJson(ResponseWrapper.success("评论成功"));
	}

}
