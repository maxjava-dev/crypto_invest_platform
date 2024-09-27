package com.sbrf.student.cryptoinvest.crypto.service.impl;

import com.sbrf.student.cryptoinvest.crypto.api.CoinMarketCapApi;
import com.sbrf.student.cryptoinvest.crypto.api.CryptoCompareApi;
import com.sbrf.student.cryptoinvest.crypto.api.impl.CryptoCompareApiImpl;
import com.sbrf.student.cryptoinvest.crypto.model.CryptoCurrency;
import com.sbrf.student.cryptoinvest.crypto.model.HistoryItem;
import com.sbrf.student.cryptoinvest.crypto.model.data.coinmarketcap.CoinMarketCapIdListElement;
import com.sbrf.student.cryptoinvest.crypto.model.data.coinmarketcap.CoinMarketCapPriceElement;
import com.sbrf.student.cryptoinvest.crypto.model.data.coinmarketcap.CoinMarketCapPriceQuote;
import com.sbrf.student.cryptoinvest.crypto.model.data.cryptocompare.CCHistoryResponse;
import com.sbrf.student.cryptoinvest.crypto.model.entity.CryptoCurrencyEntity;
import com.sbrf.student.cryptoinvest.crypto.model.entity.HistoryItemEntity;
import com.sbrf.student.cryptoinvest.crypto.repository.CryptoCurrencyRepository;
import com.sbrf.student.cryptoinvest.crypto.repository.PriceHistoryRepository;
import com.sbrf.student.cryptoinvest.crypto.service.CryptoCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Реализация {@link CryptoCurrencyService}.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    private final CoinMarketCapApi coinMarketCapApi;
    private final CryptoCompareApi cryptoCompareApi;
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final PriceHistoryRepository priceHistoryRepository;

    private Map<Long, CryptoCurrencyEntity> metadataCache = null;
    private Map<String, CryptoCurrencyEntity> metadataCacheBySymbol = null;

    @Override
    public List<CryptoCurrency> getAllCryptoCurrencies() {
        log.info("getAllCryptoCurrencies called");

        updateMetadataIfNeeded();

        var entities = metadataCache.values();
        var externalIdList = entities.stream().map(CryptoCurrencyEntity::getExternalId).toList();
        var priceElementList = coinMarketCapApi.getPriceList(externalIdList);

        return entities
                .stream()
                .map((entity) -> {
                            CryptoCurrency cryptoCurrency = new CryptoCurrency(entity.getId(), entity.getSymbol(),
                                    entity.getName(), entity.getDescription(), entity.getLogo());
                            fillPriceData(cryptoCurrency, priceElementList.getData().get(String.valueOf(entity.getExternalId())));
                            return cryptoCurrency;
                        }
                )
                .sorted(Comparator.comparing(CryptoCurrency::getId))
                .toList();
    }

    @Override
    public Optional<CryptoCurrency> getCryptoCurrency(Long cryptoId) {
        log.info("getCryptoCurrency called");

        updateMetadataIfNeeded();

        var entity = metadataCache.get(cryptoId);
        if (entity != null) {
            return Optional.of(getCryptoCurrencyByEntity(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<CryptoCurrency> getCryptoCurrencyBySymbol(String symbol) {
        log.info("getCryptoCurrencyBySymbol called");

        updateMetadataIfNeeded();

        var entity = metadataCacheBySymbol.get(symbol);
        if (entity != null) {
            return Optional.of(getCryptoCurrencyByEntity(entity));
        } else {
            return Optional.empty();
        }
    }

    private CryptoCurrency getCryptoCurrencyByEntity(CryptoCurrencyEntity entity) {
        try {
            var priceList = coinMarketCapApi.getPriceList(List.of(entity.getExternalId()));
            var priceElement = priceList
                    .getData()
                    .values()
                    .stream()
                    .findFirst()
                    .get();

            CryptoCurrency result = new CryptoCurrency(entity.getId(), entity.getSymbol(), entity.getName(), entity.getDescription(),
                    entity.getLogo());
            fillPriceData(result, priceElement);
            return result;
        } catch (Throwable e) {
            log.error("getCryptoCurrencyByEntity error", e);
            throw e;
        }
    }

    @Override
    public List<HistoryItem> getHistoryData(String symbol) {
        log.info("getHistoryData called");
        var toTimeStamp = new Date().getTime();
        var cachedItems = priceHistoryRepository.findAllBySymbolOrderByTimeAsc(symbol);
        if (cachedItems.isEmpty()) {
            log.info("getHistoryData cache is empty");
            var dataFromApi = fetchHistoryData(symbol, 8000, toTimeStamp);
            savePriceHistoryToCache(symbol, dataFromApi);
            return dataFromApi;
        } else {
            var items = cachedItems.stream().map(entity -> {
                        var item = new HistoryItem();
                        item.setTime(entity.getTime());
                        item.setPrice(entity.getPrice());
                        return item;
                    }
            ).toList();
            var result = new ArrayList<HistoryItem>(items);
            var lastTs = cachedItems.get(cachedItems.size()-1).getTime() * 1000;
            var firstTs = cachedItems.get(0).getTime();
            log.info("getHistoryData got data from cache from {} to {}, count = {}", firstTs, lastTs, cachedItems.size());
            long count = (toTimeStamp - lastTs) / 600000;
            if (count > 0) {
                var dataFromApi = fetchHistoryData(symbol, count, toTimeStamp);
                var newDataFromApi = dataFromApi.stream().filter( item ->
                        item.getTime() > lastTs / 1000
                ).toList();
                savePriceHistoryToCache(symbol, newDataFromApi);
                result.addAll(newDataFromApi);
            }
            return result;
        }
    }

    private void savePriceHistoryToCache(String symbol, List<HistoryItem> dataFromApi) {
        var entities = dataFromApi.stream().map(item -> {
                    var entity = new HistoryItemEntity();
                    entity.setSymbol(symbol);
                    entity.setTime(item.getTime());
                    entity.setPrice(item.getPrice());
                    return entity;
                }
        ).collect(Collectors.toList());
        priceHistoryRepository.saveAll(entities);
    }

    private List<HistoryItem> fetchHistoryData(String symbol, long count, long toTimeStamp) {
        log.info("fetchHistoryData called, count = {}, toTs = {}", count, toTimeStamp);
        var resultList = new ArrayList<List<HistoryItem>>();
        var remaining = count;
        while (remaining > 0) {
            var requestedCount = Math.min(remaining, CryptoCompareApiImpl.MAX_COUNT);
            remaining = remaining - requestedCount;
            var response = cryptoCompareApi.getHourlyHistoryData(symbol, requestedCount, toTimeStamp);
            var list = convertHistoryResponseToHistoryItemList(response);
            toTimeStamp = list.get(0).getTime();
            resultList.add(0, list);
        }
        var result = new ArrayList<HistoryItem>();
        resultList.forEach(result::addAll);

        log.info("fetchHistoryData finished, count = {}", result.size());
        return result;
    }

    private List<HistoryItem> convertHistoryResponseToHistoryItemList(CCHistoryResponse response) {
        return response
                .getData()
                .getData()
                .stream()
                .map((entity) -> {
                            var price = BigDecimal.valueOf(entity.getOpen());
                            return new HistoryItem(entity.getTime(), price);
                        }
                )
                .sorted(Comparator.comparing(HistoryItem::getTime))
                .toList();
    }

    private void updateMetadataIfNeeded() {
        if (metadataCache == null) {
            metadataCache = new TreeMap<>();
            metadataCacheBySymbol = new TreeMap<>();

            var entities = cryptoCurrencyRepository.findAll();
            if (entities.isEmpty()) {
                log.info("fetching metadata from external api");
                entities = fetchEntities();
                log.info("got metadata from external api");
            } else {
                log.info("got metadata from db");
            }

            metadataCache = entities
                    .stream()
                    .collect(
                            Collectors.toMap(
                                    CryptoCurrencyEntity::getId,
                                    Function.identity()
                            )
                    );
            metadataCacheBySymbol = entities
                    .stream()
                    .collect(
                            Collectors.toMap(
                                    CryptoCurrencyEntity::getSymbol,
                                    Function.identity()
                            )
                    );
        } else {
            log.info("got metadata from cache");
        }
    }

    @NonNull
    private List<CryptoCurrencyEntity> fetchEntities() {
        var ids = coinMarketCapApi.getIdList(CRYPTOCURRENCY_COUNT);
        var idList = ids.getData().stream().map(CoinMarketCapIdListElement::getId).toList();
        var metadata = coinMarketCapApi.getMetadata(idList);

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

        return cryptoCurrencyRepository.saveAll(entities);
    }

    private static final int CRYPTOCURRENCY_COUNT = 50;
    private static final String BASE_CURRENCY = "USD";

    private static void fillPriceData(CryptoCurrency cryptoCurrency, CoinMarketCapPriceElement priceElement) {
        CoinMarketCapPriceQuote quote = priceElement
                .getQuote()
                .get(BASE_CURRENCY);
        cryptoCurrency.setPrice(quote.getPrice());
        cryptoCurrency.setMarketCap(quote.getMarketCap());
        cryptoCurrency.setPercentChange1h(quote.getPercentChange1h());
        cryptoCurrency.setPercentChange24h(quote.getPercentChange24h());
        cryptoCurrency.setPercentChange7d(quote.getPercentChange7d());
        cryptoCurrency.setPercentChange30d(quote.getPercentChange30d());
    }
}
