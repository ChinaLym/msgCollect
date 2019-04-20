package com.msgc.controller;

import com.msgc.constant.enums.MessageTypeEnum;
import com.msgc.constant.response.ResponseWrapper;
import com.msgc.entity.Comment;
import com.msgc.entity.Table;
import com.msgc.service.ICommentService;
import com.msgc.service.IMessageService;
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
	private final IMessageService messageService;

	@Autowired
	public CommentController(ICommentService commentService, ITableService tableService, IMessageService messageService) {
		this.commentService = commentService;
		this.tableService = tableService;
		this.messageService = messageService;
	}

	/**
	 * 添加一条评论
	 * @param tableId 评论表 Id
	 */
	@PostMapping("/table/{tableId}")
	@ResponseBody
	public String addComment(@PathVariable("tableId") Integer tableId){
		Table table = tableService.findById(tableId);
		if(table != null){
			Comment comment = commentService.addComment(tableId);
			//发送消息
			messageService.sendMessage(
					comment.getParentId() == -1
							? MessageTypeEnum.COMMENT
							: MessageTypeEnum.REPLY,
					table);
		}
		return JsonUtil.toJson(ResponseWrapper.success("评论成功"));
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
        //TODO 判断是否删除成功
		return JsonUtil.toJson(ResponseWrapper.success("删除成功"));
	}

}
