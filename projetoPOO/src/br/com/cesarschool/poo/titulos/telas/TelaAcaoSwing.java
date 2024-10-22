package br.com.cesarschool.poo.titulos.telas;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;

public class TelaAcaoSwing extends JFrame {
	
	private static final long serialVersionUID = 1L;  // Adiciona serialVersionUID

	private MediatorAcao mediatorAcao;
	
	private JTextField txtIdentificador;
    private JTextField txtNome;
    private JTextField txtDataDeValidade;
    private JTextField txtValorUnitario;
    
    private JTextArea txtStatus;
    
    public TelaAcaoSwing(MediatorAcao mediatorAcao) {
    	this.mediatorAcao = mediatorAcao;
    	
    	setTitle("Gerenciamento de Ações");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));
        
        panel.add(new JLabel("Identificador:"));
        txtIdentificador = new JTextField();
        panel.add(txtIdentificador);

        panel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panel.add(txtNome);

        panel.add(new JLabel("Data de Validade (yyyy-MM-dd):"));
        txtDataDeValidade = new JTextField();
        panel.add(txtDataDeValidade);

        panel.add(new JLabel("Valor Unitário:"));
        txtValorUnitario = new JTextField();
        panel.add(txtValorUnitario);
        
        JButton btnIncluir = new JButton("Incluir");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnBuscar = new JButton("Buscar");

        panel.add(btnIncluir);
        panel.add(btnAlterar);
        panel.add(btnExcluir);
        panel.add(btnBuscar);
        
        txtStatus = new JTextArea();
        txtStatus.setEditable(false);
        panel.add(new JLabel("Status:"));
        panel.add(txtStatus);

        add(panel);
        

        
        // BOTÕES!!
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

            if (resultado == null) {
                txtStatus.setText("Ação incluída com sucesso.");
            } else {
                txtStatus.setText(resultado);
            }
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

            if (resultado == null) {
                txtStatus.setText("Ação alterada com sucesso.");
            } else {
                txtStatus.setText(resultado);
            }
        } catch (Exception ex) {
            txtStatus.setText("Erro ao alterar ação: " + ex.getMessage());
        }
    }
    
    private void excluirAcao() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            String resultado = mediatorAcao.excluir(identificador);

            if (resultado == null) {
                txtStatus.setText("Ação excluída com sucesso.");
            } else {
                txtStatus.setText(resultado);
            }
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
