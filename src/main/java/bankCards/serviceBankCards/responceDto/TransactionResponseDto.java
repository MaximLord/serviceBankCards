package bankCards.serviceBankCards.responceDto;

import bankCards.serviceBankCards.entity.TypeTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDto(String CardNumber, BigDecimal amount, TypeTransaction typeTransaction, LocalDateTime dateTime) {

}
