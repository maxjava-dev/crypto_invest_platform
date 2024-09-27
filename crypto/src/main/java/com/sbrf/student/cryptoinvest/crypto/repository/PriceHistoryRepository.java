package com.sbrf.student.cryptoinvest.crypto.repository;

import com.sbrf.student.cryptoinvest.crypto.model.entity.HistoryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий истории цен.
 */
@Repository
public interface PriceHistoryRepository extends JpaRepository<HistoryItemEntity, Long> {

    List<HistoryItemEntity> findAllBySymbolOrderByTimeAsc(String symbol);
}
