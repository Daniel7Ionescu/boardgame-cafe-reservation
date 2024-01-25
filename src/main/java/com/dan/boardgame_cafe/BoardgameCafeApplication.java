package com.dan.boardgame_cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class BoardgameCafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardgameCafeApplication.class, args);
	}

}
