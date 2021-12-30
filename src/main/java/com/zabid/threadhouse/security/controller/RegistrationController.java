package com.zabid.threadhouse.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zabid.threadhouse.models.Users;
import com.zabid.threadhouse.pojo.UserDTO;
import com.zabid.threadhouse.services.UserServices;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {

	@Autowired
	private UserServices userService;
	
	@PostMapping("/register")
	public Users register(@RequestBody UserDTO userDTO) {
		return userService.registerNewUser(userDTO);
	}
}
