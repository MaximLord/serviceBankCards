package bankCards.serviceBankCards.responceDto;

import java.math.BigDecimal;

public record TransactionResponseDto(String CardNumber, BigDecimal amount) {

}
