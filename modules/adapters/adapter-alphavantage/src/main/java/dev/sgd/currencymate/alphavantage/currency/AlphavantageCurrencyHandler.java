package dev.sgd.currencymate.alphavantage.currency;

import com.opencsv.CSVReader;
import dev.sgd.currencymate.domain.enums.CurrencyType;
import dev.sgd.currencymate.domain.error.common.InternalException;
import dev.sgd.currencymate.domain.model.Currency;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.sgd.currencymate.domain.enums.CurrencyType.FIAT;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class AlphavantageCurrencyHandler {

    private static final String FIAT_CURRENCIES_CSV_PATH = "physical_currency_list.csv";
    private static final String CRYPTO_CURRENCIES_CSV_PATH = "digital_currency_list.csv";

    private List<Currency> fiatCurrencies;
    private List<Currency> cryptoCurrencies;

    public Optional<Currency> getCurrencyByCode(String currencyCode) {
        Optional<Currency> fiatCurrencyOptional = fiatCurrencies.stream()
                .filter(currency -> currency.getCode().equals(currencyCode))
                .findFirst();

        if (fiatCurrencyOptional.isPresent()) {
            return fiatCurrencyOptional;
        }

        return cryptoCurrencies.stream()
                .filter(currency -> currency.getCode().equals(currencyCode))
                .findFirst();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadCurrenciesFromCsv() {
        fiatCurrencies = loadCurrenciesFromCSV(FIAT_CURRENCIES_CSV_PATH, FIAT);
        cryptoCurrencies = loadCurrenciesFromCSV(CRYPTO_CURRENCIES_CSV_PATH, CurrencyType.CRYPTO);

        log.info("Loaded Alphavantage {} {} currencies and {} crypto currencies from CSV files",
                fiatCurrencies.size(), FIAT, cryptoCurrencies.size());
    }

    private List<Currency> loadCurrenciesFromCSV(String filePath, CurrencyType currencyType) {
        log.info("Loading Alphavantage {} currencies from CSV file: {}", currencyType, filePath);

        List<Currency> currencies = new ArrayList<>(100);
        Resource fiatCurrencyResource = new ClassPathResource(filePath);
        try (Reader reader = new InputStreamReader(fiatCurrencyResource.getInputStream(), UTF_8);
             CSVReader csvReader = new CSVReader(reader)) {

            // Skip header row
            csvReader.skip(1);

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                if (nextRecord.length != 2) {
                    log.error("Invalid CSV record: '{}', expected 2 columns (currency code, currency name), skipping",
                            String.join(",", nextRecord));
                    continue;
                }

                String code = nextRecord[0].trim();
                String name = nextRecord[1].trim();

                if (code.isEmpty() || name.isEmpty()) {
                    log.error("Invalid CSV record: '{}', code or name is empty, skipping",
                            String.join(",", nextRecord));
                    continue;
                }
                currencies.add(new Currency(code, name, currencyType));
            }

            log.info("Read {} {} currencies from CSV file: {}", currencies.size(), currencyType, filePath);

            return currencies;
        } catch (Exception e) {
            log.error("Error loading Alphavantage {} currencies from CSV file: {}, message: {}",
                    currencyType, filePath, e.getMessage(), e);

            throw new InternalException();
        }
    }

}