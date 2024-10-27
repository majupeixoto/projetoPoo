package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorEntidadeOperadora;

import javax.swing.*;
import java.awt.*;


public class TelaEntidadeOperadoraSwing extends JPanel {
    private static final long serialVersionUID = 1L;

    private MediatorEntidadeOperadora mediatorEntidadeOperadora;
    private JTextField txtIdentificador;
    private JTextField txtNome;
    private JTextField txtAutorizadoAcao;
    private JTextArea txtStatus;
    private JTextArea txtDadosEntidadeOperadora;

    private TelaPrincipal telaPrincipal;

    public TelaEntidadeOperadoraSwing(MediatorEntidadeOperadora mediatorEntidadeOperadora, TelaPrincipal telaPrincipal) {
        this.mediatorEntidadeOperadora = mediatorEntidadeOperadora;
        this.telaPrincipal = telaPrincipal;

        setLayout(new BorderLayout(10, 10)); // Espaçamento interno
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Bordas ao redor da tela

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // Espaçamento interno
        inputPanel.setBorder(BorderFactory.createTitledBorder("Dados da Entidade Operadora"));

        inputPanel.add(new JLabel("Identificador:"));
        txtIdentificador = new JTextField();
        inputPanel.add(txtIdentificador);

        inputPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        inputPanel.add(txtNome);
        
        inputPanel.add(new JLabel("Autorizado Ação (true/false):"));
        txtAutorizadoAcao = new JTextField();
        inputPanel.add(txtAutorizadoAcao);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnIncluir = new JButton("Incluir");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnCancelar = new JButton("Cancelar");

        buttonPanel.add(btnIncluir);
        buttonPanel.add(btnAlterar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnBuscar);
        buttonPanel.add(btnCancelar);

        add(buttonPanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 5, 5)); // Espaçamento interno
        txtStatus = new JTextArea();
        txtStatus.setEditable(false);
        txtStatus.setLineWrap(true);
        txtStatus.setWrapStyleWord(true);
        txtStatus.setBorder(BorderFactory.createTitledBorder("Status"));
        infoPanel.add(txtStatus);

        txtDadosEntidadeOperadora = new JTextArea();
        txtDadosEntidadeOperadora.setEditable(false);
        txtDadosEntidadeOperadora.setLineWrap(true);
        txtDadosEntidadeOperadora.setWrapStyleWord(true);
        txtDadosEntidadeOperadora.setBorder(BorderFactory.createTitledBorder("Dados da Entidade: "));
        infoPanel.add(txtDadosEntidadeOperadora);

        add(infoPanel, BorderLayout.SOUTH);

        // Ação dos botões
        btnIncluir.addActionListener(e -> incluirEntidadeOperadora());
        btnAlterar.addActionListener(e -> alterarEntidadeOperadora());
        btnExcluir.addActionListener(e -> excluirEntidadeOperadora());
        btnBuscar.addActionListener(e -> buscarEntidadeOperadora());
        btnCancelar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(TelaEntidadeOperadoraSwing.this,
                    "Deseja realmente cancelar essa operação?",
                    "Confirmação de Cancelamento",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                limparCampos();
                voltarParaMenuPrincipal();
            }
        });
    }

    // MÉTODOS
    private void incluirEntidadeOperadora() {
        try {
            long identificador = Long.parseLong(txtIdentificador.getText());
            String nome = txtNome.getText();
            boolean autorizadoAcao = Boolean.parseBoolean(txtAutorizadoAcao.getText());

            EntidadeOperadora entidade = new EntidadeOperadora(identificador, nome, autorizadoAcao);
            String resultado = mediatorEntidadeOperadora.incluir(entidade);

            txtStatus.setText(resultado != null ? resultado : "Entidade operadora incluída com sucesso.");
            mostrarDadosEntidadeOperadora(entidade);
        } catch (NumberFormatException ex) {
            txtStatus.setText("Erro: Identificador deve ser um número válido.");
        } catch (Exception ex) {
            txtStatus.setText("Erro ao incluir entidade: " + ex.getMessage());
        }
    }

    private void alterarEntidadeOperadora() {
        try {
            long identificador = Long.parseLong(txtIdentificador.getText());
            String nome = txtNome.getText();
            boolean autorizadoAcao = Boolean.parseBoolean(txtAutorizadoAcao.getText());

            EntidadeOperadora entidade = new EntidadeOperadora(identificador, nome, autorizadoAcao);
            String resultado = mediatorEntidadeOperadora.alterar(entidade);

            txtStatus.setText(resultado != null ? resultado : "Entidade operadora alterada com sucesso.");
            mostrarDadosEntidadeOperadora(entidade);
        } catch (NumberFormatException ex) {
            txtStatus.setText("Erro: Identificador deve ser um número válido.");
        } catch (Exception ex) {
            txtStatus.setText("Erro ao alterar entidade: " + ex.getMessage());
        }
    }

    private void excluirEntidadeOperadora() {
        try {
            long identificador = Long.parseLong(txtIdentificador.getText());
            String resultado = mediatorEntidadeOperadora.excluir(identificador); // Passando long diretamente

            txtStatus.setText(resultado != null ? resultado : "Entidade operadora excluída com sucesso.");
            txtDadosEntidadeOperadora.setText("");
        } catch (NumberFormatException ex) {
            txtStatus.setText("Erro: Identificador deve ser um número válido.");
        } catch (Exception ex) {
            txtStatus.setText("Erro ao excluir entidade: " + ex.getMessage());
        }
    }

    private void buscarEntidadeOperadora() {
        try {
            long identificador = Long.parseLong(txtIdentificador.getText());
            EntidadeOperadora entidade = mediatorEntidadeOperadora.buscar(identificador); // Passando long diretamente

            if (entidade != null) {
                txtNome.setText(entidade.getNome());
                txtAutorizadoAcao.setText(String.valueOf(entidade.getAutorizadoAcao()));
                txtStatus.setText("Entidade operadora encontrada.");
                mostrarDadosEntidadeOperadora(entidade);
            } else {
                txtStatus.setText("Entidade operadora não encontrada.");
            }
        } catch (NumberFormatException ex) {
            txtStatus.setText("Erro: Identificador deve ser um número válido.");
        } catch (Exception ex) {
            txtStatus.setText("Erro ao buscar entidade: " + ex.getMessage());
        }
    }

    private void mostrarDadosEntidadeOperadora(EntidadeOperadora entidadeOperadora) {
        txtDadosEntidadeOperadora.setText("Identificador: " + entidadeOperadora.getIdentificador() + "\n" +
        		"Nome: " + entidadeOperadora.getNome() + "\n" +
        		"Ação: " +entidadeOperadora.getAutorizadoAcao() + "\n" +
        		"Saldo da ação: " +entidadeOperadora.getSaldoAcao() + "\n" +
        		"Saldo título dívida: " +entidadeOperadora.getSaldoTituloDivida());
    }

    private void limparCampos() {
        txtIdentificador.setText("");
        txtNome.setText("");
        txtAutorizadoAcao.setText("");
        txtStatus.setText("");
        txtDadosEntidadeOperadora.setText("");
    }

    private void voltarParaMenuPrincipal() {
        telaPrincipal.mostrarMenuPrincipal();
    }
}
