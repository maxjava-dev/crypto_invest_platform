package com.sbrf.student.cryptoinvest.crypto.repository;

import com.sbrf.student.cryptoinvest.crypto.model.entity.CryptoCurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий метаданных о криптовалюте.
 */
@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrencyEntity, Long> {
}
