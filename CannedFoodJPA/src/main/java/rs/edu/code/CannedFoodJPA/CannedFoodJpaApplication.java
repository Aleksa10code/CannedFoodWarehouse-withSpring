package rs.edu.code.CannedFoodJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rs.edu.code.CannedFoodJPA.model.Can;
import rs.edu.code.CannedFoodJPA.service.WarehouseService;

import java.time.LocalDate;
import java.util.Set;

@SpringBootApplication
public class CannedFoodJpaApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(CannedFoodJpaApplication.class, args);
	}

	@Autowired
	private WarehouseService service;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Spring Boot app started!");
		System.out.println("Brzi test");
		service.brziTest();
		System.out.println("Dodajemo konzervu");
		service.addCan("cola", LocalDate.now().plusYears(1));
		Set<Can> cans  = service.getAllCansByType("cola");
		for (Can can : cans) {
			System.out.println(can.getId() + " " + can.getExpirationDate() + " " + can.getType());
		}

		Can can = service.getFirstCanByType("cola");
		System.out.println(can.getId() + " " + can.getType() + " " + can.getExpirationDate());

		/*
		Set<Can> cans  = service.getAllCansByTypeJPQL("cola");
		for (Can can : cans) {
			System.out.println(can.getId() + " " + can.getExpirationDate() + " " + can.getType());
		}
		 */

	}
}
