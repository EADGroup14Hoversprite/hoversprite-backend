import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "internal", "api", "exception"})
@EnableJpaRepositories("internal.repository")
@EntityScan("internal.model")
public class HoverspriteApplication {

  public static void main(String[] args) {
    SpringApplication.run(HoverspriteApplication.class, args);
  }

}