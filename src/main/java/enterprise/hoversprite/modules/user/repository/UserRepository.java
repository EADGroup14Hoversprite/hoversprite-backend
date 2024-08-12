package enterprise.hoversprite.modules.user.repository;

import enterprise.hoversprite.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    User findByEmailConfirmationToken(String token);
}
