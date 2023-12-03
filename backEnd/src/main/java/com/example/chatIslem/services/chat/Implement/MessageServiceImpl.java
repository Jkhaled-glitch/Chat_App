package com.example.chatIslem.services.chat.Implement;

import java.util.ArrayList;
import java.util.List;

import com.example.chatIslem.models.user.UserModel;
import com.example.chatIslem.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatIslem.services.chat.MessageService;
import com.example.chatIslem.models.chat.Messages;
import com.example.chatIslem.repositoies.MessageRepository;




@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private  MessageRepository messageRepository;
	@Autowired
	TokenUtils tokenUtils;
  
    


	@Override
	public List<Messages> getAllMessages() {
		return messageRepository.findAll();
	}



	@Override
	public Messages getMessageById(String id) {
		return messageRepository.findById(id).orElse(null);
	}



	@Override
	public List<Messages> getMessagesBySenderId(String senderId) {
		return messageRepository.findBySenderId(senderId);
	}

	@Override
	public List<Messages> getMessageByConvId(String convId) {
		return messageRepository.findByConvId(convId);
	}


	@Override
	public List<Messages> getMessagesByRecipientId(String recipientId) {
		List<Messages> allMessages = messageRepository.findBySenderId(tokenUtils.ExtractId());
		List<Messages> filteredMessages = new ArrayList<>();


		for (Messages message : allMessages) {
			List<UserModel> recipients = message.getRecipients();
			if (recipients != null && recipients.stream().anyMatch(user -> user.getId().equals(recipientId))) {
				filteredMessages.add(message);
			}
		}

		return filteredMessages;
	}






}
