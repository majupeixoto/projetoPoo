package br.com.cesarschool.poo.titulos.telas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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

    public TelaAcaoSwing(MediatorAcao mediatorAcao) {
        this.mediatorAcao = mediatorAcao;

        setLayout(new GridLayout(8, 2)); // Layout ajustado

        add(new JLabel("Identificador:"));
        txtIdentificador = new JTextField();
        add(txtIdentificador);

        add(new JLabel("Nome:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("Data de Validade (yyyy-MM-dd):"));
        txtDataDeValidade = new JTextField();
        add(txtDataDeValidade);

        add(new JLabel("Valor Unitário:"));
        txtValorUnitario = new JTextField();
        add(txtValorUnitario);

        JButton btnIncluir = new JButton("Incluir");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnBuscar = new JButton("Buscar");

        add(btnIncluir);
        add(btnAlterar);
        add(btnExcluir);
        add(btnBuscar);

        txtStatus = new JTextArea();
        txtStatus.setEditable(false);
        add(new JLabel("Status:"));
        add(txtStatus);

        // Ações dos botões
        btnIncluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incluirAcao();
            }
        });

        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarAcao();
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirAcao();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarAcao();
            }
        });
    }

    // MÉTODOS

    private void incluirAcao() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            String nome = txtNome.getText();
            LocalDate dataDeValidade = LocalDate.parse(txtDataDeValidade.getText());
            double valorUnitario = Double.parseDouble(txtValorUnitario.getText());

            Acao acao = new Acao(identificador, nome, dataDeValidade, valorUnitario);
            String resultado = mediatorAcao.incluir(acao);

            txtStatus.setText(resultado == null ? "Ação incluída com sucesso." : resultado);
        } catch (Exception ex) {
            txtStatus.setText("Erro ao incluir ação: " + ex.getMessage());
        }
    }

    private void alterarAcao() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            String nome = txtNome.getText();
            LocalDate dataDeValidade = LocalDate.parse(txtDataDeValidade.getText());
            double valorUnitario = Double.parseDouble(txtValorUnitario.getText());

            Acao acao = new Acao(identificador, nome, dataDeValidade, valorUnitario);
            String resultado = mediatorAcao.alterar(acao);

            txtStatus.setText(resultado == null ? "Ação alterada com sucesso." : resultado);
        } catch (Exception ex) {
            txtStatus.setText("Erro ao alterar ação: " + ex.getMessage());
        }
    }

    private void excluirAcao() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            String resultado = mediatorAcao.excluir(identificador);

            txtStatus.setText(resultado == null ? "Ação excluída com sucesso." : resultado);
        } catch (Exception ex) {
            txtStatus.setText("Erro ao excluir ação: " + ex.getMessage());
        }
    }

    private void buscarAcao() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            Acao acao = mediatorAcao.buscar(identificador);

            if (acao != null) {
                txtNome.setText(acao.getNome());
                txtDataDeValidade.setText(acao.getDataDeValidade().toString());
                txtValorUnitario.setText(String.valueOf(acao.getValorUnitario()));
                txtStatus.setText("Ação encontrada.");
            } else {
                txtStatus.setText("Ação não encontrada.");
            }
        } catch (Exception ex) {
            txtStatus.setText("Erro ao buscar ação: " + ex.getMessage());
        }
    }
}
