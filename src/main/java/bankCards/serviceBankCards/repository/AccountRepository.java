package bankCards.serviceBankCards.repository;

import bankCards.serviceBankCards.entity.Account;
import bankCards.serviceBankCards.entity.TypeCard;
import bankCards.serviceBankCards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByOwnerAndAccountType(User owner, TypeCard accountType);

}
