package com.esprit.pidev.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	@NotBlank
   private String username;

	@NotBlank
	private String password;


}
