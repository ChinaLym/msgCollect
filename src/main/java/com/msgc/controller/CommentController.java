package com.msgc.controller;

import com.msgc.constant.SessionKey;
import com.msgc.constant.enums.MessageTypeEnum;
import com.msgc.constant.response.ResponseWrapper;
import com.msgc.entity.Message;
import com.msgc.entity.Table;
import com.msgc.entity.User;
import com.msgc.service.ICommentService;
import com.msgc.service.IMessageService;
import com.msgc.service.ITableService;
import com.msgc.utils.JsonUtil;
import com.msgc.utils.WebUtil;
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
	 * @return
	 */
	@PostMapping("/table/{tableId}")
	@ResponseBody
	public String addComment(@PathVariable("tableId") Integer tableId){
		Table table = tableService.findById(tableId);
		if(table != null){
			Integer owner = table.getOwner();
			commentService.addComment(tableId);
			User user = (User)WebUtil.getSessionKey(SessionKey.USER);
			Message message = new Message();
			message.setReceiver(owner);
			message.setType(MessageTypeEnum.COMMENT.getCode());
			message.setTitle(user.getNickname() + " 评论了您的收集表 " + table.getName());
			message.setHref("/collect/data/" + table.getId());
			messageService.createMessage(message);

		}

		return JsonUtil.toJson(ResponseWrapper.success("评论成功"));
	}

}
