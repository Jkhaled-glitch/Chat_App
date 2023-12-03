package com.example.chatIslem.DTOs.request;

import com.example.chatIslem.models.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDto {
    private String content;
    private String senderID;
    private List<UserModel> recipients=new ArrayList<>();
}
