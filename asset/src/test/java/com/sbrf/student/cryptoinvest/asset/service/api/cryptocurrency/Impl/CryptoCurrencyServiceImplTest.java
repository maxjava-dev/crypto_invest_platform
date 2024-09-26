package com.sbrf.student.cryptoinvest.asset.service.api.cryptocurrency.Impl;

import com.sbrf.student.cryptoinvest.asset.dto.api.cryptocurrencyservice.CryptoServiceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Тестирование класса {@link CryptoCurrencyServiceImpl}
 */
public class CryptoCurrencyServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CryptoCurrencyServiceImpl cryptoCurrencyService;

    @Value("${CRYPTO_URL}")
    private String CRYPTOCURRENCY_SERVICE_URL;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchCryptoData_Success() {
        Long cryptoId = 1L;
        CryptoServiceResponse expectedResponse = new CryptoServiceResponse();
        expectedResponse.setId(cryptoId);
        expectedResponse.setName("Bitcoin");
        expectedResponse.setPrice(BigDecimal.valueOf(45000.00));

        // Настраиваем мок для возврата ожидаемого ответа
        when(restTemplate.getForObject(CRYPTOCURRENCY_SERVICE_URL + "/crypto/" + cryptoId,
                CryptoServiceResponse.class))
                .thenReturn(expectedResponse);

        // Вызов метода
        CryptoServiceResponse actualResponse = cryptoCurrencyService.fetchCryptoData(cryptoId);

        // Проверка, что ответ соответствует ожидаемому
        assertEquals(expectedResponse, actualResponse);

        // Проверка, что метод getForObject был вызван один раз
        verify(restTemplate, times(1)).getForObject(CRYPTOCURRENCY_SERVICE_URL + "/crypto/" + cryptoId,
                CryptoServiceResponse.class);
    }

    @Test
    public void testFetchCryptoData_Failure() {
        Long cryptoId = 1L;

        // Настраиваем мок для выбрасывания исключения
        when(restTemplate.getForObject(anyString(), eq(CryptoServiceResponse.class)))
                .thenThrow(new RestClientException("Service unavailable"));

        // Проверка, что выбрасывается RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cryptoCurrencyService.fetchCryptoData(cryptoId);
        });

        // Проверка сообщения об ошибке
        assertEquals("Не удалось получить данные о криптовалюте", exception.getMessage());

        // Проверка, что метод getForObject был вызван один раз
        verify(restTemplate, times(1)).getForObject(CRYPTOCURRENCY_SERVICE_URL + "/crypto/" + cryptoId,
                CryptoServiceResponse.class);
    }
}
