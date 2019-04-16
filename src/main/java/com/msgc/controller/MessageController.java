package com.msgc.controller;

import com.msgc.constant.SessionKey;
import com.msgc.constant.enums.MessageTypeEnum;
import com.msgc.entity.Message;
import com.msgc.entity.User;
import com.msgc.service.IMessageService;
import com.msgc.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
*Type: MessageController
* Description: 消息模块（目前业务，只实现短消息，未实现组消息等广播消息）
* @author LMM
 */
@Controller
@RequestMapping("/message")
public class MessageController {

	private final IMessageService messageService;

	@Autowired
	public MessageController(IMessageService messageService) {
		this.messageService = messageService;
	}

	@GetMapping("/detail/{messageId}")
	public String MessageDetailPage(@PathVariable("messageId") String messageId) {
		messageService.findById(messageId);
		return "message/detail";
	}

	@GetMapping("/{typeCode}")
	public String messagePage(@PathVariable Integer typeCode, Model model) {
		HttpSession session = WebUtil.getRequest().getSession();
		User user = (User)session.getAttribute(SessionKey.USER);
		Message messageExample = new Message();
		messageExample.setType(typeCode);
		messageExample.setReceiver(user.getId());
		messageExample.setDelete(false);
		List<Message> messageList = messageService.findAll(messageExample);
		model.addAttribute("messageType", MessageTypeEnum.getNameBy(typeCode));
		model.addAttribute("messageList", messageList);
		return "message/myMessage";
	}

	//查看 session 中是否有新消息
	@RequestMapping("/ifNewMessage")
	@ResponseBody
	public Boolean ifNewMessage(){
		HttpSession session = WebUtil.getRequest().getSession();
		return (Boolean)session.getAttribute(SessionKey.NEW_MESSAGES_FLAG);
	}
}
