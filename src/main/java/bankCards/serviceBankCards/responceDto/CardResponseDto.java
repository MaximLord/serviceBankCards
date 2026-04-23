package bankCards.serviceBankCards.responceDto;

import bankCards.serviceBankCards.entity.Account;
import bankCards.serviceBankCards.entity.TypeCard;

import java.math.BigDecimal;

public record CardResponseDto(String numberCard, TypeCard type, BigDecimal balance) {
}
