package org.sid.userManagement_service;

import org.sid.userManagement_service.repositories.UserRepo;
import org.sid.userManagement_service.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class UserManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner runner (UserRepo userRepo, UserService userService){
		return args -> {
//			UserModel user1 = UserModel.builder()
//					.email("abdelmounime@gmail.com")
//					.role("admin")
//					.password("12345")
//					.name("abdelmounime")
//					.build();
//			UserModel user2 = UserModel.builder()
//					.email("manal@gmail.com")
//					.role("user")
//					.password("12345")
//					.name("manal")
//					.build();
//			userRepo.save(user1);
//			userRepo.save(user2);

//			UserModel userModel = userService.addUserApi(Long.valueOf(1), "06738d07-bfbc-43de-b57f-e4db047211cb");
//			System.out.println(userModel.getApiModels());
//			System.out.println(userModel.getEmail());
//			System.out.println(userModel.getPassword());
//			System.out.println(userModel.getName());
//			System.out.println(userModel.getId());
		};



	}
}
