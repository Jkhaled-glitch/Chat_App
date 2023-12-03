package com.example.chatIslem.repositoies;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.chatIslem.models.chat.Messages;

@Repository
public interface MessageRepository extends MongoRepository<Messages,String> {


    List<Messages> findBySenderId(String senderId);
/*
    @Query("{'sender':?0 or Recipients.id': ?0}")
    List<Messages> findBySenderOrRecipients_Id(String senderId, String recipientId);
*/



    @Query("{'Recipients.id': ?0}")
    List<Messages> findByRecipientId(String recipientId);

    @Query("{ 'convId' : ?0 }")
    List<Messages> findByConvId(String convId);
}


