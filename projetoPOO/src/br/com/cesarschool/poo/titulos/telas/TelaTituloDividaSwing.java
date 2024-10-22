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

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

public class TelaTituloDividaSwing extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private MediatorTituloDivida mediatorTituloDivida;

	private JTextField txtIdentificador;
	private JTextField txtNome;
	private JTextField txtDataDeValidade;
	private JTextField txtTaxaJuros;
	
	private JTextArea txtStatus;
	
	public TelaTituloDividaSwing(MediatorTituloDivida mediatorTituloDivida) {
		this.mediatorTituloDivida = mediatorTituloDivida;
		
		setTitle("Gerenciamento de Títulos de Dívida");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));
		
		panel.add(new JLabel("Identificador:"));
		txtIdentificador = new JTextField();
		panel.add(txtIdentificador);

		panel.add(new JLabel("Nome:"));
		txtNome = new JTextField();
		panel.add(txtNome);

		panel.add(new JLabel("Data de Validade (yyyy-MM-dd):"));
		txtDataDeValidade = new JTextField();
		panel.add(txtDataDeValidade);

		panel.add(new JLabel("Taxa de Juros:"));
		txtTaxaJuros = new JTextField();
		panel.add(txtTaxaJuros);
		
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
		
		// Ação dos botões
		btnIncluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				incluirTituloDivida();
			}
		});

		btnAlterar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alterarTituloDivida();
			}
		});

		btnExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				excluirTituloDivida();
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarTituloDivida();
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

			if (resultado == null) {
				txtStatus.setText("Título incluído com sucesso.");
			} else {
				txtStatus.setText(resultado);
			}
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
