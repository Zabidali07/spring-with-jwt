package com.zabid.threadhouse.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zabid.threadhouse.exception.UserAuthenticationException;
import com.zabid.threadhouse.pojo.UserDTO;
import com.zabid.threadhouse.security.utils.JwtTokenUtils;
import com.zabid.threadhouse.services.UserServices;

@RestController
@RequestMapping("api/v1/authentication")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired 
	private JwtTokenUtils jwtTokenUtils;
	
	@Autowired
	private UserServices userService;
	
	@PostMapping("/authenticate")
	public UserDTO createAuthenticationToken(@RequestBody UserDTO authentcationRequest) {
		
		authenticate(authentcationRequest.getUsername(), authentcationRequest.getPassword());
		
		UserDTO userDTO = userService.getUserDtoByUsername(authentcationRequest.getUsername());
		
		final String token = jwtTokenUtils.createToken(authentcationRequest.getUsername(), userDTO.getRoles());
		
		userDTO.setToken(token);
		return userDTO;
	}
	
	private void authenticate(String username, String password) {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}catch (BadCredentialsException e) {
			// TODO: handle exception
			throw new UserAuthenticationException("Invalid username and password");
		}
	}
}
