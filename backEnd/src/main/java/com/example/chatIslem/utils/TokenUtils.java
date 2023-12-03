package com.example.chatIslem.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.chatIslem.security.services.UserDetailsImpl;

@Service
public class TokenUtils {
	
	public TokenUtils() {
		
	}
	
	   public String ExtractId(){

		   UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   return userDetailsImpl.getId();

	    }
}
