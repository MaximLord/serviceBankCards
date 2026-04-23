package bankCards.serviceBankCards.repository;

import bankCards.serviceBankCards.entity.Account;
import bankCards.serviceBankCards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    // Поиск конкретной карты по номеру
    Optional<Card> findByNumber(String number);

    // Поиск всех карт по конкретному счету
    List<Card> findAllByAccount(Account account);
}
