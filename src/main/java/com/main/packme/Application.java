package com.main.packme;

import com.main.packme.dao.entity.Role;
import com.main.packme.dao.entity.User;
import com.main.packme.repository.RoleRepository;
import com.main.packme.repository.UserRepository;
import com.main.packme.services.Translate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

@SpringBootApplication
public class Application {
		public static void main(String[] args) {
			SpringApplication.run(Application.class, args);
		}
	@Bean
	public Translate translateBean(){
		return new Translate();
	}
}
