package com.example.chatIslem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.chatIslem.models.user.ERole;
import com.example.chatIslem.models.user.Role;
import com.example.chatIslem.models.user.UserModel;
import com.example.chatIslem.repositoies.RoleRepository;
import com.example.chatIslem.repositoies.UserRepository;
import com.example.chatIslem.services.user.RoleService;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ChatIslemApplication {

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(ChatIslemApplication.class, args);
	}


	@Bean
	CommandLineRunner run(RoleRepository roleRpository , RoleService roleService, UserRepository userRepository){
		return args -> {

			if (roleRpository.count()<1) {
				roleRpository.save(new Role(null, ERole.ROLE_ADMIN));
				roleRpository.save(new Role(null,ERole.ROLE_USER));
			}
			if(!userRepository.existsByEmail("Admin")){
				UserModel user = new UserModel(
						"Admin",

						encoder.encode("password")
				);
				Role userRole = roleRpository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));

				/*
					Role userRole2 = roleService.findRoleByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					user.getRoles().add(userRole);
					user.getRoles().add(userRole2);
				*/

				user.getRoles().add(userRole);
				System.out.println(user);
				userRepository.save(user);
			}
		};
	}

}
