package main;

import utilities.FuncaoConverterMoedas;

import javax.swing.JOptionPane;
import java.net.ConnectException;

public class Principal {

    public static void main(String[] args) throws ConnectException {
        FuncaoConverterMoedas funcao = new FuncaoConverterMoedas();

        while (true) {

            String opcao = JOptionPane
                    .showInputDialog(null, "Escolha uma conversão: ", "Menu", JOptionPane.PLAIN_MESSAGE, null,
                            new Object[] { "Conversor de Moeda" }, "Escolha")
                    .toString();

            switch (opcao) {
                case "Conversor de Moeda":
                    String input = JOptionPane.showInputDialog("Insira um valor");
                    if (conferirValor(input)) {
                        double valorRecebido = Double.parseDouble(input);
                        funcao.converterMoeda(valorRecebido);

                        int resposta = JOptionPane.showConfirmDialog(null, "Deseja fazer outra conversão?");
                        if (JOptionPane.OK_OPTION == resposta) {
                            System.out.println("Escolha opção Afirmativa");
                        } else {
                            JOptionPane.showMessageDialog(null, "Programa finalizado");
                            System.exit(0);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Valor inválido");
                    }
                    break;
            }
        }
    }

    public static boolean conferirValor(String input) {
        try {
            double valor = Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Erro:" + e.getMessage());
            return false;
        }
    }
}