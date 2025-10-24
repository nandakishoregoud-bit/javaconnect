package com.javaConnect.web.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class EnvLoader {

	@PostConstruct 
	public void loadEnv() { 
		Path envPath = Path.of("db.env"); 
	  //check if file exists 
	  if (Files.exists(envPath)) { 
		  Dotenv dotenv =Dotenv.configure() .filename("db.env") .load();
		  dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
	  
	  System.out.println("Loaded db.env locally"); 
	  } else { 
		  System.out.println("db.env not found, skipping EnvLoader (Render environment)"); } }

}
