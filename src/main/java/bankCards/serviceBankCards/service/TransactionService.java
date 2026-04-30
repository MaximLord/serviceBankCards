package bankCards.serviceBankCards.service;

import bankCards.serviceBankCards.entity.*;
import bankCards.serviceBankCards.repository.AccountRepository;
import bankCards.serviceBankCards.repository.CardRepository;
import bankCards.serviceBankCards.repository.TransactionRepository;
import bankCards.serviceBankCards.responceDto.TransactionResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class TransactionService {
    public final AccountRepository accountRepository;
    public final CardRepository cardRepository;
    public final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository, CardRepository cardRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionResponseDto deposit(String cardNumber, BigDecimal amount) {
        Card card = cardRepository.findByNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Карта не найдена!"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Сумма должна быть больше 0");
        }

        Account account = card.getAccount();
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setType(TypeTransaction.DEPOSIT);
        transaction.setRecipient(account);
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransactionResponseDto(
                card.getNumber(),
                savedTransaction.getAmount(),
                savedTransaction.getType(),
                savedTransaction.getDate());
    }

    @Transactional
    public TransactionResponseDto withdraw(String cardNumber, String pin, BigDecimal amount) {
        Card card = cardRepository.findByNumber(cardNumber).orElseThrow(() -> new RuntimeException("Карта не существует"));

        if (!card.getPin().equals(pin)) {
            throw new RuntimeException("Неверный pin");
        }

        Account account = card.getAccount();
        BigDecimal currentBalance = account.getBalance();

        if (TypeCard.DEBIT.equals(account.getAccountType())) {
            if (currentBalance.compareTo(amount) < 0) {
                throw new RuntimeException("Недостаточно средств на дебетовом счету");
            } else if (TypeCard.CREDIT.equals(account.getAccountType())) {
                BigDecimal limit = new BigDecimal("-50000");
                if (currentBalance.subtract(amount).compareTo(limit) < 0)
                    throw new RuntimeException("Превышен кредитный лимит (макс. долг -50 000)");
            }
        }

        account.setBalance(currentBalance.subtract(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setType(TypeTransaction.WITHDRAW);
        transaction.setSender(account);
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transactionRepository.save(transaction);

        return new TransactionResponseDto(
                card.getNumber(),
                amount,
                TypeTransaction.WITHDRAW,
                transaction.getDate()
        );
    }
}
