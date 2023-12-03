package com.example.chatIslem.kafka;

import com.example.chatIslem.DTOs.request.NotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.chatIslem.services.chat.MessageService;
import com.example.chatIslem.models.chat.Messages;

public class KafkaMessageConsumer {
	
	
	  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageConsumer.class);

	    @KafkaListener(
				topics="kafkachat",
				groupId="myGroup"
		)

		public void consume(Messages msg) {
			LOGGER.info(String.format("Json message received -> %s", msg.toString()));
	        MessageService.saveMessage(msg);
		}

		@KafkaListener(
				topics="kafkachat",
				groupId="myGroup"
		)
		public void consume(NotificationDto msg) {
			LOGGER.info(String.format("Json message received -> %s", msg.getContent()));
		}

}

