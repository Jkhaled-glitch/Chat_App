package com.example.chatIslem.kafka;

import com.example.chatIslem.DTOs.request.NotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.example.chatIslem.models.chat.Messages;
import com.example.chatIslem.repositoies.MessageRepository;



@Service
public class KafkaMessageProducer {

	private static final Logger LOGGER =  LoggerFactory.getLogger(KafkaMessageProducer.class);
	
	@Autowired
	private KafkaTemplate<String, Messages> kafkaTemplate;
	@Autowired
	 private  MessageRepository messageRepository1;

	

	public void sendMessage(Messages data) {
    	LOGGER.info(String.format("message sent -> %s", data.toString()));
    	Message <Messages> message = MessageBuilder
				.withPayload(data)
				.setHeader(KafkaHeaders.TOPIC, "kafkachat")
				.build();
    	kafkaTemplate.send(message);
    	messageRepository1.save(data);
	}
	public void sendNotification(NotificationDto data) {
		LOGGER.info(String.format("message sent -> %s", data.toString()));
		Message <NotificationDto> message = MessageBuilder
				.withPayload(data)
				.setHeader(KafkaHeaders.TOPIC, "kafkachat")
				.build();
		kafkaTemplate.send(message);

	}
}



