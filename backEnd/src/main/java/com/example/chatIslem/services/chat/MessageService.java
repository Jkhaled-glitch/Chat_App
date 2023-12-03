package com.example.chatIslem.services.chat;

import java.util.List;

import com.example.chatIslem.models.chat.Messages;


public interface MessageService {


    Messages getMessageById(String id);

    List<Messages> getAllMessages();

  List<Messages> getMessagesBySenderId(String senderId);

   List<Messages> getMessagesByRecipientId(String recipientId);

    List<Messages> getMessageByConvId(String convId);




    static void saveMessage(Messages msgContent) {
        MessageService messageService;
        MessageService.saveMessage(msgContent); // Enregistrement dans MongoDB

    }

    

}