package com.zabid.threadhouse.security.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zabid.threadhouse.models.Users;
import com.zabid.threadhouse.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository ur;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		final Users user = ur.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		user.getRoles().forEach(r  -> {
			authorities.add(new SimpleGrantedAuthority(r.getRole()));
		});
		
		return User
				.withUsername(user.getUsername())
				.password(user.getPassword())
				.authorities(authorities)
				.build();
	}
}
