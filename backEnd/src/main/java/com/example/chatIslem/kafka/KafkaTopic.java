package com.example.chatIslem.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

	@Bean
	public NewTopic MessagesEnvoyes() {
		return TopicBuilder.name("kafkachat")
				.build();
	}
	
	


   // public static final String ROOM_ID_SEPARATOR = "test";
}



/*	@Bean
	 public NewTopic Notifications () {
		return TopicBuilder.name("Notifications").build();
	}*/