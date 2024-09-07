package com.sbrf.student.cryptoinvest.asset.api.cryptocurrencyservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbrf.student.cryptoinvest.asset.dto.api.cryptocurrencyservice.CryptoServiceResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестирование класса {@link CryptoServiceResponse}
 */
public class CryptoServiceResponseTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Проверка, что класс {@link CryptoServiceResponse} корректно десериализует JSON-ответ с дополнительными полями.
     * @throws Exception
     */
    @Test
    public void testDeserializationWithAdditionalProperties() throws Exception {
        String jsonWithExtraFields = "{\n" +
                "    \"id\": 1,\n" +
                "    \"symbol\": \"BTC\",\n" +
                "    \"name\": \"Bitcoin\",\n" +
                "    \"description\": \"A decentralized digital currency\",\n" +
                "    \"logo\": \"https://example.com/logo.png\",\n" +
                "    \"price\": 50000.00,\n" +
                "    \"marketCap\": 900000000000,\n" + // Дополнительное поле
                "    \"volume24h\": 35000000000\n" +   // Дополнительное поле
                "}";

        /**
         * Десериализация json обратно в объект
         */
        CryptoServiceResponse response = objectMapper.readValue(jsonWithExtraFields, CryptoServiceResponse.class);

        /**
         * Проверка строго типизированных полей
         */
        assertEquals(1L, response.getId());
        assertEquals("BTC", response.getSymbol());
        assertEquals("Bitcoin", response.getName());
        assertEquals("A decentralized digital currency", response.getDescription());
        assertEquals("https://example.com/logo.png", response.getLogo());
        assertEquals(new BigDecimal("50000.00"), response.getPrice());

        /**
         * Проверка дополнительных полей
         */
        Map<String, Object> additionalProps = response.getAdditionalProperties();
        assertEquals(2, additionalProps.size());
        assertEquals(900000000000L, additionalProps.get("marketCap"));
        assertEquals(35000000000L, additionalProps.get("volume24h"));
    }

    /**
     * Проверка, что класс {@link CryptoServiceResponse} корректно сериализует объект с дополнительными полями в JSON.
     * @throws Exception
     */
    @Test
    public void testSerializationWithAdditionalProperties() throws Exception {
        /**
         * Создание объекта и инициализация строго типизированных полей
         */
        CryptoServiceResponse response = new CryptoServiceResponse();
        response.setId(2L);
        response.setSymbol("ETH");
        response.setName("Ethereum");
        response.setDescription("A decentralized platform for smart contracts");
        response.setLogo("https://example.com/eth_logo.png");
        response.setPrice(new BigDecimal("4000.00"));

        /**
         * Добавление дополнительных свойств
         */
        response.setAdditionalProperty("marketCap", 400000000000L);
        response.setAdditionalProperty("volume24h", 25000000000L);

        /**
         * Сериализация объекта в JSON
         */
        String serializedJson = objectMapper.writeValueAsString(response);

        /**
         * Десериализация обратно для проверки
         */
        CryptoServiceResponse deserializedResponse = objectMapper.readValue(serializedJson, CryptoServiceResponse.class);

        /**
         * Проверка строго типизированных полей
         */
        assertEquals(2L, deserializedResponse.getId());
        assertEquals("ETH", deserializedResponse.getSymbol());
        assertEquals("Ethereum", deserializedResponse.getName());
        assertEquals("A decentralized platform for smart contracts", deserializedResponse.getDescription());
        assertEquals("https://example.com/eth_logo.png", deserializedResponse.getLogo());
        assertEquals(new BigDecimal("4000.00"), deserializedResponse.getPrice());

        /**
         * Проверка дополнительных полей
         */
        Map<String, Object> additionalProps = deserializedResponse.getAdditionalProperties();
        assertEquals(2, additionalProps.size());
        assertEquals(400000000000L, additionalProps.get("marketCap"));
        assertEquals(25000000000L, additionalProps.get("volume24h"));
    }
}
