package com.atendestartup.authentication.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping(value="/users")
	public List<RegisterDTO> findAll(){
		List<RegisterDTO> data = authorizationService.findAll();
		return data;
	}
	@PutMapping(value="/user/{id}/update")
	public ResponseEntity<String> update( @PathVariable String id, @RequestBody @Valid RegisterDTO body){
		
		this.authorizationService.update(id, body);
		return ResponseEntity.ok("'status: 200', Usuário actualizado com sucesso.");
	}
	@DeleteMapping(value="/user/{id}/delete")
	public ResponseEntity<String> delete(@PathVariable String id){
		this.authorizationService.delete(id);
		return ResponseEntity.ok("'status: 200', Usuário delectado com sucesso.");
	}
	
}
