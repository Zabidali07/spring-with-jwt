package com.zabid.threadhouse.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.transform.ToListResultTransformer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zabid.threadhouse.exception.DuplicateRecordException;
import com.zabid.threadhouse.exception.ResourceNotFoundException;
import com.zabid.threadhouse.models.Roles;
import com.zabid.threadhouse.models.Users;
import com.zabid.threadhouse.pojo.UserDTO;
import com.zabid.threadhouse.repositories.RolesRepository;
import com.zabid.threadhouse.repositories.UserRepository;

@Service
public class UserServices  {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private RolesRepository rr;
	
	
	
	
	public Users registerNewUser(UserDTO userDTO) {
		if(emailExists(userDTO.getEmailId()) || usernameExists(userDTO.getUsername())) {
			throw new DuplicateRecordException("User already Exist");
		}
		Users users = convertDtoToEntity(userDTO);
		
		users.getRoles().addAll(getRolesFromDTO(userDTO.getRoles()));
		return ur.saveAndFlush(users);
		
		
	}
	
	public UserDTO getUserDtoByUsername(String username) {
		Users user = findByUsername(username);
		UserDTO userDTO = new UserDTO();
		userDTO.setEmailId(user.getEmailId());
		userDTO.setPassword(user.getPassword());
		userDTO.setUsername(user.getUsername());
		userDTO.setRoles(getRoleName(user.getRoles()));
		return userDTO;
		
	}
	
	private List<String> getRoleName(Set<Roles> roles){
		List<String> roleName = new ArrayList<>();
		
		roles.forEach(r -> {
			roleName.add(r.getRole());
		});
		return roleName;
	}
	
	public boolean emailExists(String emailId) {
		return emailId != null && ur.findByEmailId(emailId) != null;
	}
	
	public boolean usernameExists(String username) {
		return findByUsername(username) != null;
	}
	
	public Users findByUsername(String username) {
		return ur.findByUsername(username);
	}
	
	public Users convertDtoToEntity(UserDTO userDTO) {
		Users u = new Users();
		u.setUsername(userDTO.getUsername());
		u.setEmailId(userDTO.getEmailId());;
		u.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		u.setRoles(new HashSet<>());
		
		
//		Users users = modelMapper.map(userDTO, Users.class);
//		users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//		users.setRoles(new HashSet<>());
		
		return u;
	}
	
	public UserDTO convertEntityToDto(Users users) {
		UserDTO userDTO = convertEntityToDto(users);
		return userDTO;
	}
	
	public Set<Roles> getRolesFromDTO(List<String> rolesDto){
		Set<Roles> roles = new HashSet<>();
		for(String r: rolesDto) {
			Roles fromreolRepo = rr.findByRole(r);
			if(fromreolRepo == null) {
				throw new ResourceNotFoundException("Cannot find the role" + r);
			}
			roles.add(fromreolRepo);
		}
		return roles;
	}
}
