package com.example.chatIslem.DTOs.request;

import java.util.List;

import com.example.chatIslem.models.chat.Messages;
import com.example.chatIslem.models.user.UserModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationRequest {
	private String chatName;
	private List<String> selectedUserIds;
    
}
