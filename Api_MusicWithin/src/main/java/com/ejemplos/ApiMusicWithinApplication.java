package com.ejemplos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ApiMusicWithinApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ApiMusicWithinApplication.class, args);
		
		ApplicationContext context = SpringApplication.run(ApiMusicWithinApplication.class, args);
		
		PasswordEncoder passwordEncoder= context.getBean(PasswordEncoder.class);
		
		String encodedPassword= passwordEncoder.encode("password123");
		
		System.out.println("Contraseña encriptada: "+encodedPassword);
	}

}
