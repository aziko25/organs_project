package organs.organs;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class OrgansApplication {

	public static void main(String[] args) {

		SpringApplication.run(OrgansApplication.class, args);
	}

	@PostConstruct
	public void init() {

		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tashkent"));
	}
}