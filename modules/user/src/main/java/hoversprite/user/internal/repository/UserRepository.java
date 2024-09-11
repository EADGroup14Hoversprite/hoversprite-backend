package hoversprite.user.internal.repository;

import hoversprite.user.internal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import hoversprite.common.external.enums.UserRole;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAddress(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findByUserRole(UserRole userRole);
}
