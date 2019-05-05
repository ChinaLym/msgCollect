package com.msgc.controller;

import com.msgc.constant.response.ResponseWrapper;
import com.msgc.entity.Table;
import com.msgc.exception.ResourceNotFoundException;
import com.msgc.service.ICommentService;
import com.msgc.service.ITableService;
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
	private final ICommentService commentService;
	private final ITableService tableService;

	@Autowired
	public CommentController(ICommentService commentService, ITableService tableService) {
		this.commentService = commentService;
		this.tableService = tableService;
	}

	/**
	 * 添加一条评论
	 * @param tableId 评论表 Id
	 */
	@PostMapping("/table/{tableId}")
	public String addComment(@PathVariable("tableId") Integer tableId){
		Table table = tableService.findById(tableId);
		if(table == null){
			throw new ResourceNotFoundException();
		}
		commentService.addComment(table);
		return "redirect:/collect/data/" + tableId;
	}

	/**
	 * 删除自己的评论
	 * @param commentId 评论 Id
	 * @return 返回是否删除成功
	 */
	@PostMapping("/delete/{commentId}")
	@ResponseBody
	public String deleteComment(@PathVariable("commentId") Integer commentId){
        commentService.deleteById(commentId);
		return JsonUtil.toJson(ResponseWrapper.success("删除成功"));
	}

}
