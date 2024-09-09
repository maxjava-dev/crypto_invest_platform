package com.sbrf.student.cryptoinvest.users.repository;

import com.sbrf.student.cryptoinvest.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий клиентов.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
}
