package ch.boogaga.crystals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrystalsApplication {

	public static void main(String[] args) {
		final SpringApplication app = new SpringApplication(CrystalsApplication.class);
		app.setAdditionalProfiles("dev");
		app.run(args);
	}

}
