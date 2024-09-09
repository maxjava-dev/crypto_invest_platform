package com.sbrf.student.cryptoinvest.crypto.service.impl;

import com.sbrf.student.cryptoinvest.crypto.api.CoinMarketCapApi;
import com.sbrf.student.cryptoinvest.crypto.model.CryptoCurrency;
import com.sbrf.student.cryptoinvest.crypto.model.data.CoinMarketCapIdListElement;
import com.sbrf.student.cryptoinvest.crypto.model.data.CoinMarketCapPriceElement;
import com.sbrf.student.cryptoinvest.crypto.model.entity.CryptoCurrencyEntity;
import com.sbrf.student.cryptoinvest.crypto.repository.CryptoCurrencyRepository;
import com.sbrf.student.cryptoinvest.crypto.service.CryptoCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Реализация {@link CryptoCurrencyService}.
 */
@Service
@RequiredArgsConstructor
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    private final CoinMarketCapApi api;
    private final CryptoCurrencyRepository repository;

    private Map<Long, CryptoCurrencyEntity> metadataCache = null;

    @Override
    public List<CryptoCurrency> getAllCryptoCurrencies() {
        updateMetadataIfNeeded();

        var entities = metadataCache.values();
        var externalIdList = entities.stream().map(CryptoCurrencyEntity::getExternalId).toList();
        var priceElementList = api.getPriceList(externalIdList);

        return entities
                .stream()
                .map((entity) -> {
                            var price = getPrice(
                                    priceElementList
                                            .getData()
                                            .get(String.valueOf(entity.getExternalId()))
                            );
                            return new CryptoCurrency(entity.getId(), entity.getSymbol(), entity.getName(), entity.getDescription(),
                                    entity.getLogo(), price);
                        }
                )
                .sorted(Comparator.comparing(CryptoCurrency::getId))
                .toList();
    }

    @Override
    public CryptoCurrency getCryptoCurrency(Long cryptoId) {
        updateMetadataIfNeeded();

        var entity = metadataCache.get(cryptoId);
        var priceList = api.getPriceList(List.of(entity.getExternalId()));
        var priceElement = priceList
                .getData()
                .values()
                .stream()
                .findFirst()
                .get();

        return new CryptoCurrency(entity.getId(), entity.getSymbol(), entity.getName(), entity.getDescription(),
                entity.getLogo(), getPrice(priceElement));
    }

    private void updateMetadataIfNeeded() {
        if (metadataCache == null) {
            metadataCache = new TreeMap<>();

            var entities = repository.findAll();
            if (entities.isEmpty()) {
                entities = fetchEntities();
            }

            metadataCache = entities
                    .stream()
                    .collect(
                            Collectors.toMap(
                                    CryptoCurrencyEntity::getId,
                                    Function.identity()
                            )
                    );
        }
    }

    @NonNull
    private List<CryptoCurrencyEntity> fetchEntities() {
        var ids = api.getIdList(CRYPTOCURRENCY_COUNT);
        var idList = ids.getData().stream().map(CoinMarketCapIdListElement::getId).toList();
        var metadata = api.getMetadata(idList);

        var entities = metadata
                .getData()
                .values()
                .stream()
                .map(elementValue -> {

                            var entity = new CryptoCurrencyEntity();
                            entity.setExternalId(elementValue.getId());
                            entity.setName(elementValue.getName());
                            entity.setSymbol(elementValue.getSymbol());
                            entity.setDescription(elementValue.getDescription());
                            entity.setLogo(elementValue.getLogo());

                            return entity;
                        }
                )
                .toList();

        return repository.saveAll(entities);
    }

    private static final int CRYPTOCURRENCY_COUNT = 50;
    private static final String BASE_CURRENCY = "USD";

    private static BigDecimal getPrice(CoinMarketCapPriceElement element) {
        return element
                .getQuote()
                .get(BASE_CURRENCY)
                .getPrice();
    }
}
