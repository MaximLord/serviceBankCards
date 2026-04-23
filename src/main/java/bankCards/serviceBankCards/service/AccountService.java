package bankCards.serviceBankCards.service;

import bankCards.serviceBankCards.entity.Account;
import bankCards.serviceBankCards.entity.TypeCard;
import bankCards.serviceBankCards.entity.TypeUser;
import bankCards.serviceBankCards.entity.User;
import bankCards.serviceBankCards.repository.AccountRepository;
import bankCards.serviceBankCards.repository.UserRepository;
import bankCards.serviceBankCards.requestDto.UserRegistrationRequestDto;
import bankCards.serviceBankCards.responceDto.AccountResponseDto;
import bankCards.serviceBankCards.responceDto.UserResponseDto;

import java.math.BigDecimal;

public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public UserResponseDto registerUser(UserRegistrationRequestDto dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setType(TypeUser.GUEST);
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());

        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getEmail());
    }

    public AccountResponseDto openAccount(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        user.setType(TypeUser.CLIENT);
        userRepository.save(user);

        Account account = new Account();
        account.setOwner(user);
        account.setBalance(BigDecimal.ZERO);
        account.setAccountType(TypeCard.DEBIT);

        Account savedAccount = accountRepository.save(account);

        return new AccountResponseDto(
                savedAccount.getId(),
                user.getFirstName() + " " + user.getLastName(),
                savedAccount.getBalance(),
                user.getType());
    }
}
