package bankCards.serviceBankCards.service;

import bankCards.serviceBankCards.repository.AccountRepository;
import bankCards.serviceBankCards.repository.CardRepository;
import bankCards.serviceBankCards.responceDto.TransactionResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class TransactionService {
    public final AccountRepository accountRepository;
    public final CardRepository cardRepository;

    public TransactionService(AccountRepository accountRepository, CardRepository cardRepository) {
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
    }

    @Transactional
    public TransactionResponseDto deposit (String CardNumber, BigDecimal amount){

    }
}
