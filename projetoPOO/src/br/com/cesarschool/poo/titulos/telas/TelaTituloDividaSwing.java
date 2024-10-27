package br.com.cesarschool.poo.titulos.telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

public class TelaTituloDividaSwing extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private MediatorTituloDivida mediatorTituloDivida;

	private JTextField txtIdentificador;
	private JTextField txtNome;
	private JTextField txtDataDeValidade;
	private JTextField txtTaxaJuros;
	private JTextArea txtStatus;
	private JTextArea txtDadosTituloDivida;
	
    private TelaPrincipal telaPrincipal;
	
	public TelaTituloDividaSwing(MediatorTituloDivida mediatorTituloDivida, TelaPrincipal telaPrincipal) {
		this.mediatorTituloDivida = mediatorTituloDivida;
        this.telaPrincipal = telaPrincipal;
		
        setLayout(new BorderLayout(10, 10));
        
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Dados de Título Dívida"));

        inputPanel.add(new JLabel("Identificador:"));
        txtIdentificador = new JTextField();
        inputPanel.add(txtIdentificador);

        inputPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        inputPanel.add(txtNome);

        inputPanel.add(new JLabel("Data de Validade (yyyy-MM-dd):"));
        txtDataDeValidade = new JTextField();
        inputPanel.add(txtDataDeValidade);
        
        inputPanel.add(new JLabel("Taxa de Juros:"));
        txtTaxaJuros = new JTextField();
        inputPanel.add(txtTaxaJuros);
        
        add(inputPanel, BorderLayout.NORTH);
		
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

        add(buttonPanel, BorderLayout.CENTER);
		
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 5, 5)); // Espaçamento interno
        txtStatus = new JTextArea();
        txtStatus.setEditable(false);
        txtStatus.setLineWrap(true);
        txtStatus.setWrapStyleWord(true);
        txtStatus.setBorder(BorderFactory.createTitledBorder("Status"));
        infoPanel.add(txtStatus);

        txtDadosTituloDivida = new JTextArea();
        txtDadosTituloDivida.setEditable(false);
        txtDadosTituloDivida.setLineWrap(true);
        txtDadosTituloDivida.setWrapStyleWord(true);
        txtDadosTituloDivida.setBorder(BorderFactory.createTitledBorder("Dados da Ação"));
        infoPanel.add(txtDadosTituloDivida);

        add(infoPanel, BorderLayout.SOUTH);

		// Ação dos botões
		btnIncluir.addActionListener(e -> incluirTituloDivida());
		btnAlterar.addActionListener(e -> alterarTituloDivida());
		btnExcluir.addActionListener(e -> excluirTituloDivida());
		btnBuscar.addActionListener(e -> buscarTituloDivida());
		btnCancelar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(TelaTituloDividaSwing.this,
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
	
	private void incluirTituloDivida() {
		try {
			int identificador = Integer.parseInt(txtIdentificador.getText());
			String nome = txtNome.getText();
			LocalDate dataDeValidade = LocalDate.parse(txtDataDeValidade.getText());
			double taxaJuros = Double.parseDouble(txtTaxaJuros.getText());

			TituloDivida tituloDivida = new TituloDivida(identificador, nome, dataDeValidade, taxaJuros);
			String resultado = mediatorTituloDivida.incluir(tituloDivida);
			
			txtStatus.setText(resultado == null ? "Título incluído com sucesso." : resultado);
			mostrarDadosTituloDivida(tituloDivida);
		} catch (Exception ex) {
			txtStatus.setText("Erro ao incluir título: " + ex.getMessage());
		}
	}
	
	private void alterarTituloDivida() {
		try {
			int identificador = Integer.parseInt(txtIdentificador.getText());
			String nome = txtNome.getText();
			LocalDate dataDeValidade = LocalDate.parse(txtDataDeValidade.getText());
			double taxaJuros = Double.parseDouble(txtTaxaJuros.getText());

			TituloDivida tituloDivida = new TituloDivida(identificador, nome, dataDeValidade, taxaJuros);
			String resultado = mediatorTituloDivida.alterar(tituloDivida);

			if (resultado == null) {
				txtStatus.setText("Título alterado com sucesso.");
				mostrarDadosTituloDivida(tituloDivida);
			} else {
				txtStatus.setText(resultado);
			}
		} catch (Exception ex) {
			txtStatus.setText("Erro ao alterar título: " + ex.getMessage());
		}
	}
	
	private void excluirTituloDivida() {
		try {
			int identificador = Integer.parseInt(txtIdentificador.getText());
			String resultado = mediatorTituloDivida.excluir(identificador);

			if (resultado == null) {
				txtStatus.setText("Título excluído com sucesso.");
				txtDadosTituloDivida.setText("");
			} else {
				txtStatus.setText(resultado);
			}
		} catch (Exception ex) {
			txtStatus.setText("Erro ao excluir título: " + ex.getMessage());
		}
	}
	
	private void buscarTituloDivida() {
		try {
			int identificador = Integer.parseInt(txtIdentificador.getText());
			TituloDivida tituloDivida = mediatorTituloDivida.buscar(identificador);

			if (tituloDivida != null) {
				txtNome.setText(tituloDivida.getNome());
				txtDataDeValidade.setText(tituloDivida.getDataDeValidade().toString());
				txtTaxaJuros.setText(String.valueOf(tituloDivida.getTaxaJuros()));
				txtStatus.setText("Título encontrado.");
				mostrarDadosTituloDivida(tituloDivida);
			} else {
				txtStatus.setText("Título não encontrado.");
				txtDadosTituloDivida.setText("");
			}
		} catch (Exception ex) {
			txtStatus.setText("Erro ao buscar título: " + ex.getMessage());
		}
	}
	
	private void mostrarDadosTituloDivida(TituloDivida tituloDivida) {
		txtDadosTituloDivida.setText("Identificador: " + tituloDivida.getIdentificador() + "\n" + 
									 "Nome: " + tituloDivida.getNome() + "\n" + 
									 "Data de Validade: " + tituloDivida.getDataDeValidade() + "\n" + 
									 "Taxa de Juros: " + tituloDivida.getTaxaJuros());
    }
	
	private void limparCampos() {
        txtIdentificador.setText("");
        txtNome.setText("");
        txtDataDeValidade.setText("");
        txtTaxaJuros.setText("");
        txtStatus.setText("");
        txtDadosTituloDivida.setText("");
    }

    private void voltarParaMenuPrincipal() {
        telaPrincipal.mostrarMenuPrincipal();
    }
}
