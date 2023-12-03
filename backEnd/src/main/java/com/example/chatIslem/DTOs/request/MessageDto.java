package com.example.chatIslem.DTOs.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDto {
	
	
	
	private String content;

	@NotNull
	private String conv_id;

}
