package com.example.chatIslem.services.user;

import com.example.chatIslem.exceptions.EntityNotFoundException;
import com.example.chatIslem.models.chat.Conversation;
import com.example.chatIslem.models.user.UserModel;
import com.example.chatIslem.repositoies.UserRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;


    public UserModel saveUser(UserModel user) {
        return userRepository.save(user);
    }


    public boolean deleteUser(String id) {
    	userRepository.deleteById(id);
		return userRepository.existsById(id);
	

    }


    public UserModel getUser(String id) {
        UserModel user=userRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("user Not Found with id : " + id));
        return user;
    }
    
    
    
   //cna9es lEnable lahna 
    public UserModel getUserById(String recipient_id) {
        UserModel user=userRepository.findById(recipient_id)
                .orElseThrow(()->new EntityNotFoundException("user Not Found with recipient_id : " + recipient_id));
        return user;
    }



    public List<UserModel> getAllUser() {

        return userRepository.findAll();
    }
    public List<UserModel> findAllUserEnabled() {
        // Your custom query to find all enabled users
        Query query = new Query(Criteria.where("enabled").is(false));
        return mongoTemplate.find(query, UserModel.class);
    }

    public UserModel getUserByEmail(String email) {
        UserModel user=userRepository.findByEmail(email)
                .orElseThrow(()->new EntityNotFoundException("user Not Found with email : " + email));
        return user;

    }


    public UserModel getUserByUsername(String username) {
        UserModel user=userRepository.findByUsername(username)
                .orElseThrow(()->new EntityNotFoundException("user Not Found with username : " + username));
        return user;

    }


    public Boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean existsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    
    public UserModel getUserByUsername1(String username) {
        Optional<UserModel> userOptional = userRepository.findByUsername(username);
        
        // Vérifier si l'utilisateur existe
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé
            // Peut-être renvoyer null ou lever une exception
            return null; // Ou throw new UserNotFoundException("User not found");
        }
    }
    
    public UserModel updateUser(String id, UserModel user) {
		 UserModel existingUser = userRepository.findById(id).orElse(null);
		 if (existingUser != null) {
	            existingUser.setFirstName(user.getFirstName());
	            existingUser.setLastName(user.getLastName());
	            existingUser.setUsername(user.getUsername());
	            existingUser.setEmail(user.getEmail());
	            existingUser.setPassword(user.getPassword());
	          
	            return userRepository.save(existingUser);
	        }
		return null;
	}
    
    public UserModel updateUserByUsername(String username, UserModel updatedUser) {
        Optional<UserModel> existingUserOptional = userRepository.findByUsername(username);
        
        if (existingUserOptional.isPresent()) {
            UserModel existingUser = existingUserOptional.get();
            
            // Mettez à jour les champs pertinents de existingUser avec les valeurs de updatedUser
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setUsername(updatedUser.getUsername());
            // Continuez de mettre à jour d'autres champs si nécessaire
            
            // Enregistrez les modifications dans la base de données
            return userRepository.save(existingUser);
        } else {
            return null; // Utilisateur non trouvé, renvoyez null
        }
    }
    
    
    
    public List<UserModel> findUsersByIds(List<String> userIds) {
		return userRepository.findByIdIn(userIds);
    	
    }
}
