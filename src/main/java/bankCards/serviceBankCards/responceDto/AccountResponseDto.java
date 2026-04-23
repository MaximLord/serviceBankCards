package bankCards.serviceBankCards.responceDto;

import java.math.BigDecimal;

public record AccountResponseDto(Long id, String ownerFullName, BigDecimal balance, bankCards.serviceBankCards.entity.TypeUser type) {
}
