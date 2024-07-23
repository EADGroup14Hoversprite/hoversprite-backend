package enterprise.hoversprite.modules.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import enterprise.hoversprite.modules.user.model.User;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
}
