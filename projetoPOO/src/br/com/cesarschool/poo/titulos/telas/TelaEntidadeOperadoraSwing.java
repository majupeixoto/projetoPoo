package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorEntidadeOperadora;

import javax.swing.*;
import java.awt.*;

public class TelaEntidadeOperadoraSwing extends JPanel { // Mudou de JFrame para JPanel
    private static final long serialVersionUID = 1L;

    private MediatorEntidadeOperadora mediatorEntidadeOperadora;

    private JTextField txtIdentificador;
    private JTextField txtNome;
    private JTextField txtAutorizadoAcao;

    private JTextArea txtStatus;

    public TelaEntidadeOperadoraSwing(MediatorEntidadeOperadora mediatorEntidadeOperadora) {
        this.mediatorEntidadeOperadora = mediatorEntidadeOperadora;

        setLayout(new BorderLayout()); // Usar BorderLayout diretamente

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Identificador:"));
        txtIdentificador = new JTextField();
        panel.add(txtIdentificador);

        panel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panel.add(txtNome);

        panel.add(new JLabel("Autorizado Ação (true/false):"));
        txtAutorizadoAcao = new JTextField();
        panel.add(txtAutorizadoAcao);

        JPanel buttonPanel = new JPanel();
        JButton btnIncluir = new JButton("Incluir");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnBuscar = new JButton("Buscar");

        buttonPanel.add(btnIncluir);
        buttonPanel.add(btnAlterar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnBuscar);

        txtStatus = new JTextArea();
        txtStatus.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtStatus);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(new JLabel("Status:"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.EAST); // Área de status à direita

        // Ação dos botões
        btnIncluir.addActionListener(e -> incluirEntidadeOperadora());
        btnAlterar.addActionListener(e -> alterarEntidadeOperadora());
        btnExcluir.addActionListener(e -> excluirEntidadeOperadora());
        btnBuscar.addActionListener(e -> buscarEntidadeOperadora());
    }

    // MÉTODOS
    private void incluirEntidadeOperadora() {
        try {
            long identificador = Long.parseLong(txtIdentificador.getText());
            String nome = txtNome.getText();
            boolean autorizadoAcao = Boolean.parseBoolean(txtAutorizadoAcao.getText());

            EntidadeOperadora entidade = new EntidadeOperadora(identificador, nome, autorizadoAcao, 0.0, 0.0);
            String resultado = mediatorEntidadeOperadora.incluir(entidade);

            txtStatus.setText(resultado != null ? resultado : "Entidade operadora incluída com sucesso.");
        } catch (Exception ex) {
            txtStatus.setText("Erro ao incluir entidade: " + ex.getMessage());
        }
    }

    private void alterarEntidadeOperadora() {
        try {
            long identificador = Long.parseLong(txtIdentificador.getText());
            String nome = txtNome.getText();
            boolean autorizadoAcao = Boolean.parseBoolean(txtAutorizadoAcao.getText());

            EntidadeOperadora entidade = new EntidadeOperadora(identificador, nome, autorizadoAcao, 0.0, 0.0);
            String resultado = mediatorEntidadeOperadora.alterar(entidade);

            txtStatus.setText(resultado != null ? resultado : "Entidade operadora alterada com sucesso.");
        } catch (Exception ex) {
            txtStatus.setText("Erro ao alterar entidade: " + ex.getMessage());
        }
    }

    private void excluirEntidadeOperadora() {
        try {
            long identificador = Long.parseLong(txtIdentificador.getText());
            String resultado = mediatorEntidadeOperadora.excluir((int) identificador);

            txtStatus.setText(resultado != null ? resultado : "Entidade operadora excluída com sucesso.");
        } catch (Exception ex) {
            txtStatus.setText("Erro ao excluir entidade: " + ex.getMessage());
        }
    }

    private void buscarEntidadeOperadora() {
        try {
            long identificador = Long.parseLong(txtIdentificador.getText());
            EntidadeOperadora entidade = mediatorEntidadeOperadora.buscar((int) identificador);

            if (entidade != null) {
                txtNome.setText(entidade.getNome());
                txtAutorizadoAcao.setText(String.valueOf(entidade.getAutorizadoAcao()));
                txtStatus.setText("Entidade operadora encontrada.");
            } else {
                txtStatus.setText("Entidade operadora não encontrada.");
            }
        } catch (Exception ex) {
            txtStatus.setText("Erro ao buscar entidade: " + ex.getMessage());
        }
    }
}
