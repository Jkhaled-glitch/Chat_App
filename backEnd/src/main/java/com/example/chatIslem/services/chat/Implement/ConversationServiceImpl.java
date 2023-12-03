package com.example.chatIslem.services.chat.Implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatIslem.DTOs.request.ConversationRequest;
import com.example.chatIslem.services.chat.ConversationService;
import com.example.chatIslem.exceptions.EntityNotFoundException;
import com.example.chatIslem.models.chat.Conversation;
import com.example.chatIslem.models.chat.Messages;
import com.example.chatIslem.models.user.UserModel;
import com.example.chatIslem.repositoies.ConversationRepository;
import com.example.chatIslem.repositoies.UserRepository;
import com.example.chatIslem.utils.TokenUtils;


@Service
public class ConversationServiceImpl implements ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired

    private UserRepository userRepository;


    private List<Conversation> conversations = new ArrayList<>();


    @Override
    public boolean deleteConversation(String id) {
        conversationRepository.deleteById(id);
        return !conversationRepository.existsById(id);

    }

	/*@Override
    public void sendMessage(String conversationId, Messages message) {
        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);

        if (conversationOptional.isPresent()) {
            Conversation conversation = conversationOptional.get();
            List<Messages> messages = conversation.getMessages();
            messages.add(message);
            conversation.setMessages(messages);
            conversationRepository.save(conversation);
        } else {
            throw new ConversationNotFoundException("La conversation avec l'ID " + conversationId + " n'a pas été trouvée.");
        }
    }*/


    public List<Messages> getConversationMessages(String conversationId) {
        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);

        if (conversationOptional.isPresent()) {
            Conversation conversation = conversationOptional.get();
            return conversation.getMessage();
        } else {
            throw new EntityNotFoundException("La conversation avec l'ID " + conversationId + " n'a pas été trouvée.");
        }
    }

    private String generateChatId(String senderId, String recipientId) {
        // Implémentez la logique pour générer un identifiant de conversation unique
        return senderId + "-" + recipientId;
    }

	/*@Override
	public List<Conversation> getAllConversations() {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	
/*	public Conversation getConversationBetweenUsers(UserModel user1 , UserModel user2) {
	  return conversationRepository.findByParticipantsIn(Arrays.asList(user2, user2)) ;
}*/


    ////////////////creation de conversation //////////////////////////////
	
	/*public Conversation createCv( UserModel sender,UserModel receiver) {
		
		Conversation cv=ConversationRepository.findBySenderAndRecipient(sender, receiver);
		
		if(cv != null) {
			return cv;
			
			
		}else {
			Conversation conversationOfSender = Conversation
					.builder()
					.sender(sender)
					.recipient(receiver)
			       
			  .build();
			
		
			Conversation conversationOfReciver = Conversation
					.builder()
			         .sender(receiver)
                     .recipient(sender)
				     .build();
			conversationRepository.save(conversationOfSender);
			conversationRepository.save(conversationOfReciver);
		
		return conversationOfSender;
	}

	
	}*/

    @Override
    public void addParticipantToConversation(String conversationId, UserModel participantId) {

    }


    /*	@Override
        public Conversation findConversationList(UserModel sender, UserModel recipient) {
            // TODO Auto-generated method stub
            return null;
        }*/
/*
	@Override
	public Conversation createConversation(Conversation conversation) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
    @Override
    public Conversation createConversation(ConversationRequest request) {
        Conversation conversation = new Conversation();
        conversation.setChatName(request.getChatName());

        List<UserModel> participants = userRepository.findByIdIn(request.getSelectedUserIds());
        conversation.setParticipants(participants);

        return conversationRepository.save(conversation);
    }

    @Override
    public Conversation getConversationBetweenUsers(UserModel user1, UserModel user2) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addMessageToConversation(String conversationId, Messages message) {

    }

    @Override
    public Conversation createNewConv(Conversation coversation) {


        return conversationRepository.save(coversation);
    }

    @Override
    public Conversation getConversationById(String id) {
        Conversation conv = conversationRepository.findById(id).get();
        //.orElseThrow(()-> new EntityNotFoundException("Conversation Not Found with id : " +id));
        return conv;
    }


    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }

    @Override
    public List<UserModel> getAllUsers() {
        // TODO Auto-generated method stub
        return userRepository.findAll();
    }

    @Override
    public List<Conversation> findconversationbyuserid(String extractId) {
        return conversationRepository.findconversationbyuserid(extractId);
    }


}