package utilities;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.Map;

public class ApiRequest {
    public String paraConverter(String moedaRecebida, String moedaConvertida, double valorConversao) throws ConnectException {
        final String chaveApiKey = "0498b788b4805623865d1acc";

        try {
            final URI uri = URI.create("https://v6.exchangerate-api.com/v6/" + chaveApiKey + "/latest/" + moedaRecebida);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            if (response.statusCode() != 200) {
                throw new ConnectException("Erro na resposta da API: Código " + response.statusCode());
            }

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();
            Moedas moedaRecebidaObj = gson.fromJson(json, Moedas.class);
            double coinValue = getCoinValue(moedaConvertida, valorConversao, moedaRecebidaObj);
            String coinValueFinal = formatValue(coinValue);
            String valueFinal = formatValue(valorConversao);

            StringBuilder mensagem = new StringBuilder();
            mensagem.append("O valor de ").append(moedaRecebida).append(" ").append(valueFinal)
                    .append(" é equivalente a ").append(moedaConvertida).append(" ").append(coinValueFinal).append(".");

            return mensagem.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro de I/O ao acessar a API: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("A operação de conexão foi interrompida: " + e.getMessage(), e);
        }
    }

    private static double getCoinValue(String moedaConvertida, double valorConversao, Moedas moedaRecebidaObj) {
        Map<String, Double> conversorMoedas = moedaRecebidaObj.conversionRates();

        if (conversorMoedas == null || conversorMoedas.isEmpty()) {
            throw new RuntimeException("Erro: Dados de conversão não disponíveis.");
        }

        Double taxaConversao = conversorMoedas.get(moedaConvertida);
        if (taxaConversao == null) {
            throw new RuntimeException("Erro: Taxa de conversão para a moeda especificada não disponível.");
        }

        return taxaConversao * valorConversao;
    }

    private String formatValue(double valorConversao) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(valorConversao);
    }
}
