package com.example.chatIslem.controllers;


import com.example.chatIslem.DTOs.request.MessageDto;
import com.example.chatIslem.DTOs.request.NotificationDto;
import com.example.chatIslem.DTOs.views.Views;
import com.example.chatIslem.kafka.KafkaMessageProducer;
import com.example.chatIslem.models.chat.Conversation;
import com.example.chatIslem.models.chat.Messages;
import com.example.chatIslem.models.user.UserModel;
import com.example.chatIslem.repositoies.ConversationRepository;
import com.example.chatIslem.services.chat.ConversationService;
import com.example.chatIslem.services.chat.MessageService;
import com.example.chatIslem.services.user.UserService;
import com.example.chatIslem.utils.TokenUtils;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    TokenUtils tokenUtils;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    ConversationService conversationService;
    @Autowired
    ConversationRepository conversationRepository;

    Logger logger;

    private KafkaMessageProducer kafkaMessageProducer;
    private SimpMessageSendingOperations messagingTemplate; // Ajout de ce membre

    @Autowired
    public MessageController(
            KafkaMessageProducer kafkaMessageProducer,
            SimpMessageSendingOperations messagingTemplate) {
        this.kafkaMessageProducer = kafkaMessageProducer;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Messages broadcastGroupMessage(@Payload Messages message) {
        logger.info("Received WebSocket message: " + message.getMessageContent());
        messagingTemplate.convertAndSend("/topic/group", message);

        return message;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    @PostMapping("/publish")
    public ResponseEntity<?> publish(@RequestBody MessageDto msgRequest) {

        String id_Connect = tokenUtils.ExtractId();
        // Récupérez la conversation en fonction de l'ID de la conversation fourni
        if (msgRequest.getConv_id() != null) {
            Conversation conversation = conversationService.getConversationById(msgRequest.getConv_id());

            if (conversation != null) {
                // Vérifiez si l'utilisateur connecté fait partie des participants de la conversation
                if (conversation.getParticipants().stream().anyMatch(user -> user.getId().equals(id_Connect))) {
                    // Créez un nouvel objet Messages (message)
                    Messages msg = new Messages();
                    msg.setMessageContent(msgRequest.getContent());

                    msg.setDateEnvoie(new Date());
                    msg.setConvId(msgRequest.getConv_id());
                    msg.setSender(userService.getUser(id_Connect));

                    // Obtenez tous les participants de la conversation, sauf l'utilisateur connecté
                    List<UserModel> participants = conversation.getParticipants().stream()
                            .filter(user -> !user.getId().equals(id_Connect))
                            .collect(Collectors.toList());

                    // Assurez-vous qu'il y a au moins un destinataire
                    if (!participants.isEmpty()) {
                        msg.getRecipients().addAll(participants);
                    } else {
                        // Gérez le cas où il n'y a aucun autre participant dans la conversation
                        return new ResponseEntity<>("Vous êtes le seul participant dans la conversation.", HttpStatus.BAD_REQUEST);
                    }

                    // Ajoutez le message à la liste de messages de la conversation
                    conversation.getMessage().add(msg);

                    // Enregistrez la conversation mise à jour
                    conversationRepository.save(conversation);
                    NotificationDto notif=new NotificationDto();
                    notif.setContent(msg.getSender().getUsername()+" send new message ");
                    notif.setSenderID(id_Connect);
                    notif.getRecipients().addAll(participants);
                    // Envoyez le message au topic Kafka
                    kafkaMessageProducer.sendMessage(msg);
                    kafkaMessageProducer.sendNotification(notif);
                    return ResponseEntity.ok("Message envoyé à la conversation avec succès. Expéditeur : " + msg.getSender().getUsername());
                } else {
                    return new ResponseEntity<>("Vous n'êtes pas autorisé à envoyer un message dans cette conversation.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("La conversation n'existe pas.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("L'identifiant de la conversation est null.", HttpStatus.BAD_REQUEST);
        }

    }



    @JsonView(Views.Public.class)
    @GetMapping("/me")
    public ResponseEntity<List<Messages>> getMessagesForLoggedInUser() {
        String userId = tokenUtils.ExtractId();

        // Récupérez tous les messages où l'utilisateur connecté est soit l'expéditeur, soit le destinataire
        List<Messages> sentMessages = messageService.getMessagesBySenderId(userId);
        List<Messages> receivedMessages = messageService.getMessagesByRecipientId(userId);

        // Fusionnez les deux listes de messages
        List<Messages> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(receivedMessages);

        // Triez les messages par date d'envoi (du plus récent au plus ancien)
        allMessages.sort(Comparator.comparing(Messages::getDateEnvoie).reversed());

        return ResponseEntity.ok(allMessages);
    }

    @JsonView(Views.Public.class)
    @GetMapping("/{id}")
    public ResponseEntity<Messages> getMessageById(@PathVariable String id) {
        Messages message = messageService.getMessageById(id);
        if (message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @JsonView(Views.Public.class)
    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<Messages>> getMessagesBySenderId(@PathVariable String senderId) {
        List<Messages> messages = messageService.getMessagesBySenderId(senderId);
        return ResponseEntity.ok(messages);
    }
    @JsonView(Views.Public.class)
    @GetMapping("/recipient/{recipientId}")
    public ResponseEntity<List<Messages>> getMessagesByRecipientId(@PathVariable String recipientId) {
        List<Messages> messages = messageService.getMessagesByRecipientId(recipientId);
        return ResponseEntity.ok(messages);
    }

    @JsonView(Views.Public.class)
    @GetMapping("/conv/{id}")
    public ResponseEntity<List<Messages>> getMessageByConvId(@PathVariable String convId)  {
        List<Messages> messages = messageService.getMessageByConvId(convId);
        return ResponseEntity.ok(messages);
    }





}

