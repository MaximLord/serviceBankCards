package bankCards.serviceBankCards.service;

import bankCards.serviceBankCards.entity.*;
import bankCards.serviceBankCards.repository.AccountRepository;
import bankCards.serviceBankCards.repository.CardRepository;
import bankCards.serviceBankCards.repository.UserRepository;
import bankCards.serviceBankCards.responceDto.CardResponseDto;

import java.math.BigDecimal;
import java.util.Random;

public class CardService {
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardService(AccountRepository accountRepository, CardRepository cardRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public CardResponseDto createCard(Long userId, TypeCard typeCard, String pin) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (TypeUser.GUEST.equals(user.getType()) && TypeCard.CREDIT.equals(typeCard)) {
            throw new RuntimeException("Гости не могут иметь кредитные карты!");
        }

        Account account = accountRepository.findByOwnerAndAccountType(user, typeCard)
                .orElseGet(() -> {
                    Account newAcc = new Account();
                    newAcc.setOwner(user);
                    newAcc.setAccountType(typeCard);
                    newAcc.setBalance(typeCard == TypeCard.CREDIT ? new BigDecimal("50000") : BigDecimal.ZERO);
                    return accountRepository.save(newAcc);
                });

        Card card = new Card();

        String cardNumber = String.format("%04d %04d %04d %04d",
                new Random().nextInt(10000),
                new Random().nextInt(10000),
                new Random().nextInt(10000),
                new Random().nextInt(10000));

        card.setNumber(cardNumber);
        card.setType(typeCard);
        card.setPin(pin);
        card.setAccount(account);

        Card savedCard = cardRepository.save(card);

        return new CardResponseDto(
                savedCard.getNumber(), savedCard.getType(), account.getBalance()
        );
    }
}
