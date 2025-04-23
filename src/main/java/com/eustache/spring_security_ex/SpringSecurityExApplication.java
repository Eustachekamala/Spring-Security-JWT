package com.eustache.spring_security_ex;

import com.eustache.spring_security_ex.models.Student;
import com.eustache.spring_security_ex.repositories.StudentRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringSecurityExApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityExApplication.class, args);
	}

	//@Bean
	public CommandLineRunner commandLineRunner(
			StudentRepository studentRepository
	) {
		return args -> {
			for( int i = 0; i < 2; i++ ) {
				Faker faker = new Faker();
				var student = Student.builder()
						.username(faker.name().username())
						.email(faker.internet().emailAddress())
						.password(faker.internet().password())
						.build();
				studentRepository.save(student);
			};
		};
	}

}
