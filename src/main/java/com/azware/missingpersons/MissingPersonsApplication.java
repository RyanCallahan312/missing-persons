package com.azware.missingpersons;

import java.util.UUID;

import com.azware.missingpersons.context.RequestContext;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;

@SpringBootApplication
public class MissingPersonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MissingPersonsApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	@RequestScope
	public RequestContext requestContext(){
		return new RequestContext(UUID.randomUUID().toString());
	}

}
