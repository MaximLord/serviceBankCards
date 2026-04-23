package bankCards.serviceBankCards.repository;

import bankCards.serviceBankCards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    // Для проверки регистрации
    Optional<User> findByEmail(String email);
}
