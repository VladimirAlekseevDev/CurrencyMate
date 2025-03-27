package dev.sgd.currencymate.adapteralphavantage;

import com.opencsv.CSVReader;
import dev.sgd.currencymate.domain.error.common.InternalException;
import dev.sgd.currencymate.domain.model.Currency;
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

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyLoader {

    private String fiatCurrenciesCsvPath = "physical_currency_list.csv";
    private String cryptoCurrenciesCsvPath = "digital_currency_list.csv";

    private List<Currency> fiatCurrencies;
    private List<Currency> cryptoCurrencies;

    @EventListener(ApplicationReadyEvent.class)
    public void loadCurrenciesFromCsv() {
        fiatCurrencies = loadCurrenciesFromCSV(fiatCurrenciesCsvPath);
        cryptoCurrencies = loadCurrenciesFromCSV(cryptoCurrenciesCsvPath);

        log.info("Loaded {} fiat currencies and {} crypto currencies from CSV files", fiatCurrencies.size(), cryptoCurrencies.size());}

    private List<Currency> loadCurrenciesFromCSV(String filePath) {
        log.info("Loading Alphavantage currencies from CSV file: {}", filePath);

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
                currencies.add(new Currency(code, name));
            }

            log.info("Read {} currencies from CSV file: {}", currencies.size(), filePath);

            return currencies;
        } catch (Exception e) {
            log.error("Error loading Alphavantage currencies from CSV file: {}, message: {}",
                    filePath, e.getMessage(), e);

            throw new InternalException();
        }
    }

}