package com.example.chatIslem.models.user;

import com.example.chatIslem.DTOs.views.Views;
import com.example.chatIslem.models.BaseEntity;
import com.example.chatIslem.models.chat.Conversation;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserModel extends BaseEntity {

    @JsonView(Views.Public.class)
    private String firstName;
    @JsonView(Views.Public.class)
    private String lastName;
    @JsonView(Views.Public.class)
    private String username;
    @JsonView(Views.Public.class)
    private String email;
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();
    

	//private List<Conversation> conversations=new ArrayList<>();
    
    
    public UserModel(String firstName, String lastName, String username, String email, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email=email;
        this.password = password;

    }

    public UserModel(String email, String password) {

        this.email = email;
        this.password = password;
    }
}
