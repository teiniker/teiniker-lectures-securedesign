package org.se.lab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringBootJpaApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SpringBootJpaApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ArticleRepository repository)
	{
		return args -> {
			repository.save(new Article("Design Patterns", 4295));
			repository.save(new Article("Effective Java", 3336));

			List<Article> students = repository.findAll();
			students.forEach(System.out::println);
		};
	}
}
