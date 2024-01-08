package com.atendestartup.authentication.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atendestartup.authentication.DTO.RegisterDTO;
import com.atendestartup.authentication.Entities.User;
import com.atendestartup.authentication.Repositories.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {

	@Autowired
	public UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return this.userRepository.findByLogin(username);
	}

	public void register(RegisterDTO body) {
		String encryptPassword = new BCryptPasswordEncoder().encode(body.password());
		User user = new User(body.id(), body.login(), encryptPassword, body.role());
		this.userRepository.save(user);
	}

}
