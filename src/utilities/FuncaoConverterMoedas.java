package utilities;

import javax.swing.JOptionPane;
import java.net.ConnectException;

public class FuncaoConverterMoedas {

    private ApiRequest apiRequest;

    public FuncaoConverterMoedas() {
        this.apiRequest = new ApiRequest();
    }

    public void converterMoeda(double valorRecebido) {
        try {
            String opcao = obterOpcaoConversao();
            String resultado = realizarConversao(opcao, valorRecebido);
            System.out.println(resultado);
        } catch (ConnectException e) {
            System.err.println("Erro ao conectar à API: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }

    private String obterOpcaoConversao() {
        return (String) JOptionPane.showInputDialog(
                null, "Escolha a moeda para a qual você deseja converter seu dinheiro ",
                "Moedas", JOptionPane.PLAIN_MESSAGE, null,
                new Object[] { "De Reais a Dólares", "De Reais a Euros", "De Reais a Libras",
                        "De Reais a Peso Argentino", "De Reais a Peso Chileno", "De Reais a Iene Japonês",
                        "De Dólares a Reais", "De Euros a Reais", "De Libras a Reais",
                        "De Peso Argentino a Reais", "De Peso Chileno a Reais", "De Iene Japonês a Reais" },
                "Escolha");
    }

    private String realizarConversao(String opcao, double valorRecebido) throws ConnectException {
        switch (opcao) {
            case "De Reais a Dólares":
                return apiRequest.paraConverter("BRL", "USD", valorRecebido);
            case "De Reais a Euros":
                return apiRequest.paraConverter("BRL", "EUR", valorRecebido);
            case "De Reais a Libras":
                return apiRequest.paraConverter("BRL", "GBP", valorRecebido);
            case "De Reais a Peso Argentino":
                return apiRequest.paraConverter("BRL", "ARS", valorRecebido);
            case "De Reais a Peso Chileno":
                return apiRequest.paraConverter("BRL", "CLP", valorRecebido);
            case "De Reais a Iene Japonês":
                return apiRequest.paraConverter("BRL", "JPY", valorRecebido);
            case "De Dólares a Reais":
                return apiRequest.paraConverter("USD", "BRL", valorRecebido);
            case "De Euros a Reais":
                return apiRequest.paraConverter("EUR", "BRL", valorRecebido);
            case "De Libras a Reais":
                return apiRequest.paraConverter("GBP", "BRL", valorRecebido);
            case "De Peso Argentino a Reais":
                return apiRequest.paraConverter("ARS", "BRL", valorRecebido);
            case "De Peso Chileno a Reais":
                return apiRequest.paraConverter("CLP", "BRL", valorRecebido);
            case "De Iene Japonês a Reais":
                return apiRequest.paraConverter("JPY", "BRL", valorRecebido);
            default:
                throw new IllegalArgumentException("Opção de conversão inválida: " + opcao);
        }
    }
}