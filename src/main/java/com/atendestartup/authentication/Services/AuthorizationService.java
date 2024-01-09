package com.atendestartup.authentication.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public void register(RegisterDTO body) {
		String encryptPassword = new BCryptPasswordEncoder().encode(body.password());
		User user = new User(body.id(), body.login(), encryptPassword, body.role());
		this.userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public List<RegisterDTO> findAll() {
		List<User> resul = userRepository.findAll();
		List<RegisterDTO> data = resul.stream()
				.map(x -> new RegisterDTO(x.getId(), x.getLogin(), x.getPassword(), x.getRole())).toList();
		return data;
	}

	@Transactional
	public void update(String id, RegisterDTO body) {
		String newLogin = body.login();
		String newPasswordEncrypted = new BCryptPasswordEncoder().encode(body.password());
		String newRole = body.role().toString();
		userRepository.update(id, newLogin, newPasswordEncrypted, newRole);
	}
	@Transactional 
	public void delete(String id) {
		User resul = userRepository.findById(id).get();
		this.userRepository.delete(resul);
	}
}
