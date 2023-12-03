package com.example.chatIslem.models.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.chatIslem.DTOs.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.chatIslem.models.user.UserModel;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

@Builder
@Document(collection="message")
public class Messages{
	@JsonView(Views.Public.class)
	@Id
	private String id;
	@JsonView(Views.Public.class)
	private String messageContent;
	@JsonView(Views.Public.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date DateEnvoie;
	@JsonView(Views.Public.class)
	private String convId;
	@JsonView(Views.Public.class)
	private UserModel sender;
	@JsonView(Views.Public.class)
	private List<UserModel> Recipients=new ArrayList<>();
	@JsonView(Views.Public.class)
    private Status status;

	public Messages() {
		this.id= UUID.randomUUID().toString();
	}
}