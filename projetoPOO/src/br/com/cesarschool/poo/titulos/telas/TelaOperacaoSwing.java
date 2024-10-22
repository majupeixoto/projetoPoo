package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.mediators.MediatorOperacao;

import javax.swing.*;
import java.awt.*;

public class TelaOperacaoSwing extends JPanel { // Mudou de JFrame para JPanel

    private static final long serialVersionUID = 1L;

    private MediatorOperacao mediatorOperacao;

    private JTextField txtEntidadeCredito;
    private JTextField txtEntidadeDebito;
    private JTextField txtIdAcaoOuTitulo;
    private JTextField txtValor;
    private JCheckBox chkEhAcao;

    private JTextArea txtStatus;

    public TelaOperacaoSwing(MediatorOperacao mediatorOperacao) {
        this.mediatorOperacao = mediatorOperacao;

        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(7, 2));

        panel.add(new JLabel("Entidade Crédito:"));
        txtEntidadeCredito = new JTextField();
        panel.add(txtEntidadeCredito);

        panel.add(new JLabel("Entidade Débito:"));
        txtEntidadeDebito = new JTextField();
        panel.add(txtEntidadeDebito);

        panel.add(new JLabel("ID Ação ou Título:"));
        txtIdAcaoOuTitulo = new JTextField();
        panel.add(txtIdAcaoOuTitulo);

        panel.add(new JLabel("Valor:"));
        txtValor = new JTextField();
        panel.add(txtValor);

        panel.add(new JLabel("É Ação?"));
        chkEhAcao = new JCheckBox();
        panel.add(chkEhAcao);

        JButton btnRealizarOperacao = new JButton("Realizar Operação");
        panel.add(btnRealizarOperacao);

        txtStatus = new JTextArea();
        txtStatus.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtStatus);
        panel.add(new JLabel("Status:"));
        panel.add(scrollPane);

        add(panel, BorderLayout.CENTER);

        btnRealizarOperacao.addActionListener(e -> realizarOperacao());
    }

    // MÉTODOS
    private void realizarOperacao() {
        try {
            int entidadeCredito = Integer.parseInt(txtEntidadeCredito.getText());
            int entidadeDebito = Integer.parseInt(txtEntidadeDebito.getText());
            int idAcaoOuTitulo = Integer.parseInt(txtIdAcaoOuTitulo.getText());
            double valor = Double.parseDouble(txtValor.getText());
            boolean ehAcao = chkEhAcao.isSelected();

            // Chama o método de realização de operação no MediatorOperacao
            String resultado = mediatorOperacao.realizarOperacao(ehAcao, entidadeCredito, entidadeDebito, idAcaoOuTitulo, valor);
            txtStatus.setText(resultado);
        } catch (NumberFormatException ex) {
            txtStatus.setText("Erro: Verifique se todos os campos estão preenchidos corretamente.");
        } catch (Exception ex) {
            txtStatus.setText("Erro ao realizar operação: " + ex.getMessage());
        }
    }
}
