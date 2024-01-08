package com.atendestartup.authentication.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atendestartup.authentication.DTO.AuthenticationDTO;
import com.atendestartup.authentication.DTO.LoginResponseDTO;
import com.atendestartup.authentication.DTO.RegisterDTO;
import com.atendestartup.authentication.Entities.User;
import com.atendestartup.authentication.InfraSecurity.TokenService;
import com.atendestartup.authentication.Services.AuthorizationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping(value="/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO body) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(body.login(), body.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = this.tokenService.generateToken( (User)auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	@PostMapping(value="/register")
	public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO body){
		if(this.authorizationService.loadUserByUsername(body.login()) !=null )
			return ResponseEntity.badRequest().build();
		else
			this.authorizationService.register(body);
			return ResponseEntity.ok("status: 200, Usário cadastrado com sucesso.");
	}

}
