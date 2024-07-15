package utilities;

import java.util.Map;

public record Moedas(String result, String baseCode, Map<String, Double> conversionRates) {
}