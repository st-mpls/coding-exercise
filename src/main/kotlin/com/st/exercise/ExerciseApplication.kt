package com.st.exercise

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ExerciseApplication {
	@Bean
	// Defines the high-level swagger / open api config details
	fun openApiSpec(): OpenAPI {
		val info = Info()
				.title("Coding Exercise API")
				.description("Interact with Github")
		return OpenAPI().info(info)
	}
}

fun main(args: Array<String>) {
	runApplication<ExerciseApplication>(*args)
}