package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.entity.User;
import com.example.repository.UserRepository;

public class LoginUserService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	@Autowired
	public LoginUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public LoginUser loadUserByUsername(String email) throws UsernameNotFoundException{
		
		User user = this.userRepository.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException("ユーザーが見つかりません。");
		}
		return new LoginUser(user);
	}
}
