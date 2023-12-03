package com.example.chatIslem.models.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.chatIslem.DTOs.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.example.chatIslem.models.user.UserModel;
import com.example.chatIslem.utils.TokenUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "conversation")
public class Conversation {
    @JsonView(Views.Public.class)
    @Id
    private String id;
    @JsonView(Views.Public.class)
    private String chatName;
    @JsonView(Views.Public.class)
    @DBRef
    private List<UserModel> participants = new ArrayList<>();
    @JsonView(Views.Public.class)
    @DBRef
    public List<Messages> message = new ArrayList<>();

}
