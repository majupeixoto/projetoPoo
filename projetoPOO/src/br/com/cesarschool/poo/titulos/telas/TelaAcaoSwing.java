package br.com.cesarschool.poo.titulos.telas;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.*;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;

public class TelaAcaoSwing extends JPanel {
    private static final long serialVersionUID = 1L;

    private MediatorAcao mediatorAcao;
    private JTextField txtIdentificador;
    private JTextField txtNome;
    private JTextField txtDataDeValidade;
    private JTextField txtValorUnitario;
    private JTextArea txtStatus;
    private JTextArea txtDadosAcao;

    private TelaPrincipal telaPrincipal;

    public TelaAcaoSwing(MediatorAcao mediatorAcao, TelaPrincipal telaPrincipal) {
        this.mediatorAcao = mediatorAcao;
        this.telaPrincipal = telaPrincipal;
        setLayout(new BorderLayout(10, 10)); // Espaçamento entre componentes

        // Painel para os campos de entrada
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // Espaçamento interno
        inputPanel.setBorder(BorderFactory.createTitledBorder("Dados da Ação"));

        inputPanel.add(new JLabel("Identificador:"));
        txtIdentificador = new JTextField();
        inputPanel.add(txtIdentificador);

        inputPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        inputPanel.add(txtNome);

        inputPanel.add(new JLabel("Data de Validade (yyyy-MM-dd):"));
        txtDataDeValidade = new JTextField();
        inputPanel.add(txtDataDeValidade);

        inputPanel.add(new JLabel("Valor Unitário:"));
        txtValorUnitario = new JTextField();
        inputPanel.add(txtValorUnitario);

        add(inputPanel, BorderLayout.NORTH); // Adiciona o painel ao topo

        JPanel buttonPanel = new JPanel(new FlowLayout()); // Espaçamento harmônico
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

        add(buttonPanel, BorderLayout.CENTER); // Adiciona o painel de botões no centro

        // Painel para status e dados
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 5, 5)); // Espaçamento interno
        txtStatus = new JTextArea();
        txtStatus.setEditable(false);
        txtStatus.setLineWrap(true);
        txtStatus.setWrapStyleWord(true);
        txtStatus.setBorder(BorderFactory.createTitledBorder("Status"));
        infoPanel.add(txtStatus);

        txtDadosAcao = new JTextArea();
        txtDadosAcao.setEditable(false);
        txtDadosAcao.setLineWrap(true);
        txtDadosAcao.setWrapStyleWord(true);
        txtDadosAcao.setBorder(BorderFactory.createTitledBorder("Dados da Ação"));
        infoPanel.add(txtDadosAcao);

        add(infoPanel, BorderLayout.SOUTH); // Adiciona o painel de informações na parte inferior

        // Ações dos botões
        btnIncluir.addActionListener(e -> incluirAcao());
        btnAlterar.addActionListener(e -> alterarAcao());
        btnExcluir.addActionListener(e -> excluirAcao());
        btnBuscar.addActionListener(e -> buscarAcao());
        btnCancelar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(TelaAcaoSwing.this,
                    "Deseja realmente cancelar essa operação?",
                    "Confirmação de Cancelamento",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                limparCampos();
                voltarParaMenuPrincipal();
            }
        });
    }

    private void incluirAcao() {
        try {
            String identificadorTexto = txtIdentificador.getText().trim();
            String nome = txtNome.getText().trim();
            String dataTexto = txtDataDeValidade.getText().trim();
            String valorTexto = txtValorUnitario.getText().trim();

            if (identificadorTexto.isEmpty() || nome.isEmpty() || dataTexto.isEmpty() || valorTexto.isEmpty()) {
                txtStatus.setText("Erro: Todos os campos devem ser preenchidos.");
                return;
            }

            int identificador = Integer.parseInt(identificadorTexto);
            LocalDate dataDeValidade = LocalDate.parse(dataTexto);
            double valorUnitario = Double.parseDouble(valorTexto);

            Acao acao = new Acao(identificador, nome, dataDeValidade, valorUnitario);
            String resultado = mediatorAcao.incluir(acao);

            if (resultado == null) {
                txtStatus.setText("Ação incluída com sucesso.");
                mostrarDadosAcao(acao);
            } else {
                txtStatus.setText("Erro ao incluir ação: " + resultado);
            }
        } catch (NumberFormatException ex) {
            txtStatus.setText("Erro: Identificador e valor unitário devem ser numéricos.");
        } catch (DateTimeParseException ex) {
            txtStatus.setText("Erro: Data de validade deve estar no formato correto (yyyy-MM-dd).");
        } catch (Exception ex) {
            txtStatus.setText("Erro inesperado ao incluir ação: " + ex.getMessage());
        }
    }


    private void alterarAcao() {
        try {
            if (txtIdentificador.getText().trim().isEmpty() || 
                txtNome.getText().trim().isEmpty() || 
                txtDataDeValidade.getText().trim().isEmpty() || 
                txtValorUnitario.getText().trim().isEmpty()) {
                
                txtStatus.setText("Erro: Preencha todos os campos obrigatórios.");
                return;
            }

            int identificador = Integer.parseInt(txtIdentificador.getText().trim());
            String nome = txtNome.getText().trim();
            LocalDate dataDeValidade = LocalDate.parse(txtDataDeValidade.getText().trim());
            double valorUnitario = Double.parseDouble(txtValorUnitario.getText().trim());

            Acao acao = new Acao(identificador, nome, dataDeValidade, valorUnitario);
            String resultado = mediatorAcao.alterar(acao);

            if (resultado == null) {
                txtStatus.setText("Ação alterada com sucesso.");
                mostrarDadosAcao(acao);
            } else {
                txtStatus.setText("Erro ao alterar ação: " + resultado);
            }
        } catch (NumberFormatException ex) {
            txtStatus.setText("Erro ao alterar ação: Identificador ou valor numérico inválido.");
        } catch (DateTimeParseException ex) {
            txtStatus.setText("Erro ao alterar ação: Data de validade inválida.");
        } catch (Exception ex) {
            txtStatus.setText("Erro inesperado ao alterar ação: " + ex.getMessage());
        }
    }


    private void excluirAcao() {
        try {
            if (txtIdentificador.getText().trim().isEmpty()) {
                txtStatus.setText("Erro: Informe o identificador da ação para excluí-la.");
                return;
            }

            int identificador = Integer.parseInt(txtIdentificador.getText().trim());
            String resultado = mediatorAcao.excluir(identificador);

            if (resultado == null) {
                txtStatus.setText("Ação excluída com sucesso.");
                txtDadosAcao.setText("");
            } else {
                txtStatus.setText("Erro ao excluir ação: " + resultado);
            }
        } catch (NumberFormatException ex) {
            txtStatus.setText("Erro ao excluir ação: Identificador inválido.");
        } catch (Exception ex) {
            txtStatus.setText("Erro inesperado ao excluir ação: " + ex.getMessage());
        }
    }


    private void buscarAcao() {
        limparCamposDados();

        try {
            int identificador = Integer.parseInt(txtIdentificador.getText().trim());
            Acao acao = mediatorAcao.buscar(identificador);

            if (acao != null) {
                txtNome.setText(acao.getNome());
                txtDataDeValidade.setText(acao.getDataDeValidade().toString());
                txtValorUnitario.setText(String.valueOf(acao.getValorUnitario()));
                txtStatus.setText("Ação encontrada.");
                mostrarDadosAcao(acao);
            } else {
                txtStatus.setText("Erro ao buscar ação: Ação inexistente.");
            }
        } catch (NumberFormatException ex) {
            txtStatus.setText("Erro ao buscar ação: Identificador inválido. Insira um número válido.");
        } catch (Exception ex) {
            txtStatus.setText("Erro inesperado ao buscar ação: " + ex.getMessage());
        }
    }

    
    private void mostrarDadosAcao(Acao acao) {
        txtDadosAcao.setText("Identificador: " + acao.getIdentificador() + "\n" +
                             "Nome: " + acao.getNome() + "\n" +
                             "Data de Validade: " + acao.getDataDeValidade() + "\n" +
                             "Valor Unitário: " + acao.getValorUnitario());
    }
    
    private void limparCampos() {
        txtIdentificador.setText("");
        txtNome.setText("");
        txtDataDeValidade.setText("");
        txtValorUnitario.setText("");
        txtStatus.setText("");
        txtDadosAcao.setText("");
    }

    private void limparCamposDados() {
        txtNome.setText("");
        txtDataDeValidade.setText("");
        txtValorUnitario.setText("");
    }

    private void voltarParaMenuPrincipal() {
        telaPrincipal.mostrarMenuPrincipal();
    }
}
