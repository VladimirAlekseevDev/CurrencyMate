package dev.sgd.currencymate.alphavantage.currency;

import com.opencsv.CSVReader;
import dev.sgd.currencymate.domain.enums.CurrencyType;
import dev.sgd.currencymate.domain.error.common.InternalException;
import dev.sgd.currencymate.domain.model.Currency;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static dev.sgd.currencymate.domain.enums.CurrencyType.CRYPTO;
import static dev.sgd.currencymate.domain.enums.CurrencyType.FIAT;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Getter
//@Component TODO disabled, needs payment
@RequiredArgsConstructor
public class AlphavantageCurrencyHandler {

    private static final String START_LOADING_CURRENCIES_LOG_MSG =
            """
            🪙 Loading Alphavantage {} and {} currencies from CSV files: {}
            """;
    private static final String FINISHED_LOADING_CURRENCIES_LOG_MSG =
            """
            ☑️ Finished loading Alphavantage {} and {} currencies from CSV files, {} currencies count: {}, {} currencies count: {}
            """;

    private static final String ERROR_LOADING_CURRENCIES_LOG_MSG =
            """
            ❌ Failed to load Alphavantage {} currencies from CSV file, error message: {}
            """;

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

    //@EventListener(ApplicationReadyEvent.class) TODO disabled, needs payment
    public void loadCurrenciesFromCsv() {
        log.info(START_LOADING_CURRENCIES_LOG_MSG,
                FIAT, CRYPTO, List.of(FIAT_CURRENCIES_CSV_PATH, CRYPTO_CURRENCIES_CSV_PATH));

        fiatCurrencies = loadCurrenciesFromCSV(FIAT_CURRENCIES_CSV_PATH, FIAT);
        cryptoCurrencies = loadCurrenciesFromCSV(CRYPTO_CURRENCIES_CSV_PATH, CRYPTO);

        log.info(FINISHED_LOADING_CURRENCIES_LOG_MSG, FIAT, CRYPTO,
                FIAT, fiatCurrencies.size(),
                CRYPTO, cryptoCurrencies.size());
    }

    private List<Currency> loadCurrenciesFromCSV(String filePath, CurrencyType currencyType) {
        List<Currency> currencies = new ArrayList<>(100);
        Resource fiatCurrencyResource = new ClassPathResource(filePath);
        try (Reader reader = new InputStreamReader(fiatCurrencyResource.getInputStream(), UTF_8);
             CSVReader csvReader = new CSVReader(reader)) {

            // Skip header row
            csvReader.skip(1);

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                if (nextRecord.length != 2) {
                    log.error(ERROR_LOADING_CURRENCIES_LOG_MSG, currencyType,
                            "Invalid CSV record: expected 2 columns (currency code, currency name), record: " + Arrays.toString(nextRecord));
                    throw new InternalException();
                }

                String code = nextRecord[0].trim();
                String name = nextRecord[1].trim();

                if (code.isEmpty() || name.isEmpty()) {
                    log.error(ERROR_LOADING_CURRENCIES_LOG_MSG, currencyType,
                            "Invalid CSV record: currency code or currency name is empty, record: " + Arrays.toString(nextRecord));
                    throw new InternalException();
                }
                currencies.add(new Currency(code, name, currencyType));
            }

            return currencies;
        } catch (Exception e) {
            log.error(ERROR_LOADING_CURRENCIES_LOG_MSG, currencyType, e.getMessage());

            throw new InternalException();
        }
    }

}