package hoversprite.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "hoversprite")
@EnableJpaRepositories(basePackages = {"hoversprite.user.internal.repository", "hoversprite.feedback.internal.repository", "hoversprite.order.internal.repository", "hoversprite.notification.internal.repository"})
@EntityScan(basePackages = {"hoversprite.user.internal.model", "hoversprite.feedback.internal.model", "hoversprite.order.internal.model", "hoversprite.notification.internal.model"})
public class HoverspriteApplication {
    public static void main(String[] args) {
    SpringApplication.run(HoverspriteApplication.class, args);
  }
}
