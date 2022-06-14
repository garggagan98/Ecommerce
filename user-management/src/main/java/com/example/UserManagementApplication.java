package com.example;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.entity.Role;
import com.example.repository.RoleRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class UserManagementApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role role = new Role();
			role.setId(501);
			role.setName("ROLE_ADMIN");
			Role role1 = new Role();
			role1.setId(502);
			role1.setName("ROLE_USER");
			List<Role> roles = List.of(role, role1);
			roleRepository.saveAll(roles);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
