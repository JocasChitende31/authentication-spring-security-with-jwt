package com.atendestartup.authentication.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.atendestartup.authentication.Entities.User;

public interface UserRepository extends JpaRepository<User, String> {

	UserDetails findByLogin(String login);
	
//	@Modifying
//	void register(User data);
}
