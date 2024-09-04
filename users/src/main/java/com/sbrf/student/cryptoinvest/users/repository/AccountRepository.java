package com.sbrf.student.cryptoinvest.users.repository;

import com.sbrf.student.cryptoinvest.users.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий счетов.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}