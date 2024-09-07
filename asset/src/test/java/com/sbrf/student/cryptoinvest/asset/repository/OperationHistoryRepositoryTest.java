package com.sbrf.student.cryptoinvest.asset.repository;

import com.sbrf.student.cryptoinvest.asset.model.Asset;
import com.sbrf.student.cryptoinvest.asset.model.OperationHistory;
import com.sbrf.student.cryptoinvest.asset.model.OperationType;
import com.sbrf.student.cryptoinvest.asset.repository.AssetRepository;
import com.sbrf.student.cryptoinvest.asset.repository.OperationHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестирование {@link OperationHistoryRepository}
 */
// TODO: В следующем комите подробней рассмотреть историю операций
@DataJpaTest
public class OperationHistoryRepositoryTest {

    @Autowired
    private OperationHistoryRepository operationHistoryRepository;

    @Autowired
    private AssetRepository assetRepository;

    private Asset asset;
    private OperationHistory operation1;
    private OperationHistory operation2;

    /**
     * Очистка бд и инициализация ее тестовыми данными
     */
    @BeforeEach
    public void setUp() {
        operationHistoryRepository.deleteAll();
        assetRepository.deleteAll();

        asset = new Asset();
        asset.setUserId(1L);
        asset.setCryptoId(100L);
        asset.setQuantity(BigDecimal.valueOf(10.5));
        assetRepository.save(asset);

        operation1 = new OperationHistory();
        operation1.setAsset(asset);
        operation1.setOperationType(OperationType.buy);
        operation1.setSumOperation(BigDecimal.valueOf(100));
        operation1.setPurchaseDate(LocalDateTime.now());
        operation1.setQuantity(BigDecimal.valueOf(10.0));
        operationHistoryRepository.save(operation1);

        operation2 = new OperationHistory();
        operation2.setAsset(asset);
        operation2.setOperationType(OperationType.sell);
        operation2.setSumOperation(BigDecimal.valueOf(50));
        operation2.setPurchaseDate(LocalDateTime.now());
        operation2.setQuantity(BigDecimal.valueOf(5.0));
        operationHistoryRepository.save(operation2);
    }

    /**
     * Проверка метода {@link OperationHistoryRepository#findByAsset_AssetId})
     * Получение списка операций с существующим активом
     */
    @Test
    public void testFindByAsset_AssetId() {
        /**
         * Получение списка операций с существующим активом
         */
        List<OperationHistory> operations = operationHistoryRepository.findByAsset_AssetId(asset.getAssetId());

        /**
         * Проверка что список содержит 2 операции(согласно инициализации бд {@link #setUp})
         */
        assertThat(operations).hasSize(2);

        /**
         * Проверяет, что возвращаемый список содержит только operation1 и operation2, без учета порядка.
         *
         * Данные согласно инициализации бд {@link #setUp}
         */
        assertThat(operations).containsExactlyInAnyOrder(operation1, operation2);
    }

    /**
     * Проверка метода {@link OperationHistoryRepository#findByAsset_AssetId})
     *
     * Получение списка операций по несуществующему активу
     */
    @Test
    public void testFindByAssetId_NoResults() {
        /**
         * Получение списка операций в которых участвует не существующий актив
         */
        List<OperationHistory> operations = operationHistoryRepository.findByAsset_AssetId(999L);

        /**
         * Проверяет, что возвращаемый список операций пуст
         */
        assertThat(operations).isEmpty();
    }
}
