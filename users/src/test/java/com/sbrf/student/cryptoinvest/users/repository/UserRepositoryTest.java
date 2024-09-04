package com.sbrf.student.cryptoinvest.users.repository;

import com.sbrf.student.cryptoinvest.users.model.Account;
import com.sbrf.student.cryptoinvest.users.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    private User user;

    @BeforeEach
    public void setUp() {
        // Создаем новый счет
        account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));

        // Создаем нового клиента
        user = new User();
        user.setUserName("ivan123");
        user.setFullName("Ivanov Ivan");
        user.setEmail("ivanov.ivan@example.com");
        user.setPassword("password123");

        // Устанавливаем этот счет для клиента
        user.setAccount(account);
    }

    @Test
    public void testCreateUserWithAccount() {
        // Сохраняем клиента
        User savedUser = userRepository.save(user);

        // Проверяем, что клиент и счет были сохранены
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getAccount()).isNotNull();
        assertThat(savedUser.getAccount().getId()).isNotNull();
        assertThat(savedUser.getAccount().getBalance()).isEqualByComparingTo(account.getBalance().toString());
    }

    @Test
    public void testFindUserById() {
        // Сохраняем клиента
        User savedUser = userRepository.save(user);

        // Находим клиента по ID
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Проверяем, что клиент был найден и данные совпадают
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserName()).isEqualTo(user.getUserName());
        assertThat(foundUser.get().getAccount().getBalance()).isEqualByComparingTo(account.getBalance().toString());
    }

    @Test
    public void testDeleteUserAlsoDeletesAccount() {
        // Сохраняем пользователя
        User savedUser = userRepository.save(user);

        // Удаляем пользователя
        userRepository.delete(savedUser);

        // Проверяем, что пользователь был удален
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        assertThat(deletedUser).isNotPresent();

        // Проверяем, что счет также был удален
        Optional<Account> deletedAccount = accountRepository.findById(account.getId());
        assertThat(deletedAccount).isNotPresent();
    }

    @Test
    public void testUpdateUserAccount() {
         // Сохраняем пользователя
        User savedUser = userRepository.save(user);

        // Проверяем, что пользователь был сохранен
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getAccount().getBalance()).isEqualByComparingTo(account.getBalance().toString());

        // Обновляем счет пользователя
        BigDecimal newBalance = BigDecimal.valueOf(1500);
        savedUser.getAccount().setBalance(newBalance);
        User updatedUser = userRepository.save(savedUser);

        // Проверяем, что баланс счета был обновлен
        assertThat(updatedUser.getAccount().getBalance()).isEqualByComparingTo(newBalance.toString());
    }

    // TODO: Тесты на валидацию (@NotNull, @NotEmpty, @Email, @Size)
}