package com.example.chatIslem.controllers;

import java.util.Enumeration;
import java.util.List;

import com.example.chatIslem.DTOs.views.Views;
import com.example.chatIslem.repositoies.ConversationRepository;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.chatIslem.DTOs.request.ConversationRequest;
import com.example.chatIslem.services.chat.ConversationService;
import com.example.chatIslem.models.chat.Conversation;
import com.example.chatIslem.models.user.UserModel;
import com.example.chatIslem.services.user.UserService;
import com.example.chatIslem.utils.TokenUtils;


@RestController
@RequestMapping("/api/conv")

public class ConversationController {

    @Autowired
    ConversationService conversationService;
    @Autowired
    UserService userService;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ConversationRepository conversationRepository;

        @PostMapping("/create")
        public ResponseEntity<?> createConversation(@RequestBody ConversationRequest request, HttpServletRequest httpRequest) {
            String idConect = tokenUtils.ExtractId();
            System.out.print("Id: "+idConect);

        if (request.getSelectedUserIds().size() >= 1) {
            if (!request.getSelectedUserIds().contains(idConect)) {

                Conversation conv = new Conversation();

                conv.setChatName(request.getChatName());
                conv.getParticipants().add(userService.getUser(idConect));
                for (String userId : request.getSelectedUserIds()) {
                    UserModel user = userService.getUser(userId);

                    if (user != null) {
                        conv.getParticipants().add(user);

                    }


                }
                Conversation conv2 = conversationService.createNewConv(conv);

                return ResponseEntity.ok("Conversation crée avec succes ");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("l'utilisateur connecte ne peut pas etre inclus dans la liste id ");
            }


        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La liste de participants doit contenir au moins deux utilisateur ");


        }
    }


    @JsonView(Views.Public.class)
    @GetMapping("/Conversations")
    public ResponseEntity<List<Conversation>> getConversationByUserId() {
        return ResponseEntity.ok().body(conversationService.findconversationbyuserid(tokenUtils.ExtractId()));

    }


    @JsonView(Views.Public.class)
    @GetMapping("/getAllConversation")
    public ResponseEntity<?> getAllConversationsofUSerConcte() {


        String idConect = tokenUtils.ExtractId();
        List<Conversation> Conversations = conversationRepository.findByParticipantsId(idConect);

        if (Conversations.size() > 0) {
            return new ResponseEntity<List<Conversation>>(Conversations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Conversation available", HttpStatus.NOT_FOUND);
        }

    }

    @JsonView(Views.Public.class)
    @GetMapping("/{id}")
    public Conversation getConversationById(@PathVariable String id) {
        // Utilisez le service pour récupérer la conversation par son ID
        return conversationService.getConversationById(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConversation(@PathVariable String id) {

        String userId = tokenUtils.ExtractId();

        Conversation conversation = conversationService.getConversationById(id);

        if (conversation == null) {
            return new ResponseEntity<>("La conversation n'a pas été trouvée", HttpStatus.NOT_FOUND);
        }

        if (!conversation.getParticipants().contains(userService.getUser(userId))) {
            return new ResponseEntity<>("Vous n'êtes pas autorisé à supprimer cette conversation", HttpStatus.FORBIDDEN);
        }

        boolean conversationDeleted = conversationService.deleteConversation(id);

        if (conversationDeleted) {
            return new ResponseEntity<>("Conversation supprimée avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("La conversation n'a pas pu être supprimée", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
