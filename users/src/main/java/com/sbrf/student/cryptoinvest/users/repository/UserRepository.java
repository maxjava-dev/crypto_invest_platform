package com.sbrf.student.cryptoinvest.users.repository;

import com.sbrf.student.cryptoinvest.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий пользователей.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
