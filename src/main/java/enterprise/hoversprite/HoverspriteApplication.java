package enterprise.hoversprite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "enterprise.hoversprite")
public class HoverspriteApplication {

  public static void main(String[] args) {
    SpringApplication.run(HoverspriteApplication.class, args);
  }

}
