package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.IUserService;

@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	//esta clase es para encriptar la contraseña que es mas seguro que el hash actualmente
	//puede dar diferentes resultados pero conoce que los resultados salen de la mismo origen
	private BCryptPasswordEncoder brCryptPasswordEncoder;
	
	@GetMapping("/users")
	public ResponseEntity<?> obtenerUsuarios(){
		List<User> users = userService.findAll();
		if (users != null) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/create_user")
	public ResponseEntity<?> create(@RequestBody User user) {
		//encriptamos la contraseña
		user.setPassword(brCryptPasswordEncoder.encode(user.getPassword()));
		userService.save(user);
		//deberíamos comprobar que no guardamos el mismo usuario dos veces y que es correcto
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}
