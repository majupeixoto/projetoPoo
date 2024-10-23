package br.com.cesarschool.poo.titulos.telas;

import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

public class TelaTituloDividaSwing extends JPanel { // Mudou de JFrame para JPanel
	
	private static final long serialVersionUID = 1L;

	private MediatorTituloDivida mediatorTituloDivida;

	private JTextField txtIdentificador;
	private JTextField txtNome;
	private JTextField txtDataDeValidade;
	private JTextField txtTaxaJuros;
	
	private JTextArea txtStatus;
	
	public TelaTituloDividaSwing(MediatorTituloDivida mediatorTituloDivida) {
		this.mediatorTituloDivida = mediatorTituloDivida;
		
		setLayout(new GridLayout(7, 2));
		
		add(new JLabel("Identificador:"));
		txtIdentificador = new JTextField();
		add(txtIdentificador);

		add(new JLabel("Nome:"));
		txtNome = new JTextField();
		add(txtNome);

		add(new JLabel("Data de Validade (yyyy-MM-dd):"));
		txtDataDeValidade = new JTextField();
		add(txtDataDeValidade);

		add(new JLabel("Taxa de Juros:"));
		txtTaxaJuros = new JTextField();
		add(txtTaxaJuros);
		
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

		// Ação dos botões
		btnIncluir.addActionListener(e -> incluirTituloDivida());
		btnAlterar.addActionListener(e -> alterarTituloDivida());
		btnExcluir.addActionListener(e -> excluirTituloDivida());
		btnBuscar.addActionListener(e -> buscarTituloDivida());
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
			} else {
				txtStatus.setText("Título não encontrado.");
			}
		} catch (Exception ex) {
			txtStatus.setText("Erro ao buscar título: " + ex.getMessage());
		}
	}
}
