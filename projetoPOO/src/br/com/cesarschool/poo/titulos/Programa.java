package br.com.cesarschool.poo.titulos;

import br.com.cesarschool.poo.titulos.telas.TelaPrincipal;

import javax.swing.SwingUtilities;

public class Programa {
    public static void main(String[] args) {
        // Executa o código da GUI na thread do Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.setVisible(true);
        });
    }
}
