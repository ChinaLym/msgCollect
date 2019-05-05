package com.msgc.controller;

import com.msgc.constant.SessionKey;
import com.msgc.constant.enums.MessageTypeEnum;
import com.msgc.constant.response.ResponseWrapper;
import com.msgc.entity.Message;
import com.msgc.entity.User;
import com.msgc.service.IMessageService;
import com.msgc.utils.JsonUtil;
import com.msgc.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Comparator;
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


	/**
	 * 删除自己的消息
	 * @param messageId 消息 Id
	 * @return 返回是否删除成功
	 */
	@PostMapping("/delete/{messageId}")
	@ResponseBody
	public String deleteMessage(@PathVariable("messageId") Integer messageId){
		messageService.deleteById(messageId);
		return JsonUtil.toJson(ResponseWrapper.success("删除成功"));
	}

	/**
	 * 删除自己所有的消息
	 * @param typeCode 消息类型
	 * @return 返回是否删除成功
	 */
	@PostMapping("/deleteAll/{typeCode}")
	@ResponseBody
	public String deleteAllMessage(@PathVariable("typeCode") Integer typeCode){
		messageService.deleteAllByType(typeCode);
		return JsonUtil.toJson(ResponseWrapper.success("删除成功"));
	}

	/**
	 * 消息界面
	 * @param typeCode 消息类型
	 * @return 对应类型的消息界面
	 */
	@GetMapping("/{typeCode}")
	public String messagePage(@PathVariable Integer typeCode, Model model) {
		HttpSession session = WebUtil.getRequest().getSession();
		session.setAttribute(SessionKey.NEW_MESSAGES_FLAG, false);
		User user = (User)session.getAttribute(SessionKey.USER);
		List<Message> messageList = messageService.findAllByReceiverAndType(user.getId(), typeCode);
		messageList.sort(Comparator.comparingInt(Message::getId));
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

	/**
	 * 标记已读
	 * @param messageId 消息 Id
	 * @return 返回是否删除成功
	 */
	@PostMapping("/read/{messageId}")
	@ResponseBody
	public String readMessage(@PathVariable("messageId") Integer messageId){
		messageService.read(messageId);
		return JsonUtil.toJson(ResponseWrapper.success("删除成功"));
	}

	/**
	 * 全部标记已读
	 * @param typeCode 消息 Id
	 * @return 返回是否删除成功
	 */
	@PostMapping("/readAll/{typeCode}")
	@ResponseBody
	public String readAllMessage(@PathVariable("typeCode") Integer typeCode){
		messageService.readAll(typeCode);
		return JsonUtil.toJson(ResponseWrapper.success("删除成功"));
	}
}
