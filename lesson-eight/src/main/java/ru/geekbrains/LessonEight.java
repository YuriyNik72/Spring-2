package ru.geekbrains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableWebSocketMessageBroker
public class LessonEight {
	public static void main(String[] args) {
		SpringApplication.run(LessonEight.class, args);
	}

//	private TestRepository repository;
//
//	public GeekMarketWinterApplication(@Autowired TestRepository repository) {
//		this.repository = repository;
//	}

//	@Override
//	public void run(String... args) throws Exception {
//		List<ProductDTO> res = repository.getProducts(1).stream().collect(toCollection(ArrayList::new));
//		System.out.println(res);
//	}
}