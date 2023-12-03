package com.example.chatIslem.services.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.chatIslem.DTOs.request.ConversationRequest;
import com.example.chatIslem.models.chat.Conversation;
import com.example.chatIslem.models.chat.Messages;
import com.example.chatIslem.models.user.UserModel;





public interface ConversationService {

	
	
	 
	 List<UserModel> getAllUsers();

	Conversation getConversationById(String id);
	
	 void addMessageToConversation(String conversationId, Messages message);
	 
	 List<Conversation> getAllConversations();
	
    List<Messages> getConversationMessages(String conversationId);

	
	//Conversation createConversation(Conversation conversation);

    boolean deleteConversation(String id);

    void addParticipantToConversation(String conversationId, UserModel participantId);
    
   // List<Conversation> getAllConversations();
    
Conversation getConversationBetweenUsers(UserModel user1, UserModel user2);
   
//Conversation findConversationList(UserModel sender, UserModel recipient);



Conversation createConversation(ConversationRequest request);

/*public static Optional<String> getChatId(UserModel sender, UserModel recipient, boolean createIfNotExist) {

  //Optional<String> existingChat = ConversationRepository.findBySenderAndRecipient(sender, recipient){
	  
  
    if (existingChat.isPresent()) {
        return existingChat;
    }
        if(!createIfNotExist) {
            return  Optional.empty();
        }
        String chatId =
                String.format(KafkaTopic.ROOM_ID_SEPARATOR, sender, recipient);





} }}*/


/*public static Optional<String> getChatId(UserModel sender, UserModel recipient, boolean createIfNotExist) {
    Optional<String> existingChat = ConversationRepository.findBySenderAndRecipient(sender, recipient)
            .map(Conversation::getChatId);

    if (existingChat.isPresent()) {
        return existingChat;
    }

    if (!createIfNotExist) {
        return Optional.empty();
    }

    String chatId = String.format(KafkaTopic.ROOM_ID_SEPARATOR, sender.getId(), recipient.getId());
    
    // You can save the chatId or do any other necessary logic here, like creating a conversation

    return Optional.of(chatId);
}
*/


Conversation createNewConv (Conversation coversation);

 List<Conversation> findconversationbyuserid(String extractId);







}



