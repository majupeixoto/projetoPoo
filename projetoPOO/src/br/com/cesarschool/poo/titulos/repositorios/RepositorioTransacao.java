package br.com.cesarschool.poo.titulos.repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
/*
 * Deve gravar em e ler de um arquivo texto chamado Transacao.txt os dados dos objetos do tipo
 * Transacao. Seguem abaixo exemplos de linhas 
 * De entidadeCredito: identificador, nome, autorizadoAcao, saldoAcao, saldoTituloDivida.
 * De entidadeDebito: identificador, nome, autorizadoAcao, saldoAcao, saldoTituloDivida.
 * De acao: identificador, nome, dataValidade, valorUnitario OU null
 * De tituloDivida: identificador, nome, dataValidade, taxaJuros OU null. 
 * valorOperacao, dataHoraOperacao
 * 
 *   002192;BCB;true;0.00;1890220034.0;001112;BOFA;true;12900000210.00;3564234127.0;1;PETROBRAS;2024-12-12;30.33;null;100000.0;2024-01-01 12:22:21 
 *   002192;BCB;false;0.00;1890220034.0;001112;BOFA;true;12900000210.00;3564234127.0;null;3;FRANCA;2027-11-11;2.5;100000.0;2024-01-01 12:22:21
 *
 * A inclusão deve adicionar uma nova linha ao arquivo. 
 * 
 * A busca deve retornar um array de transações cuja entidadeCredito tenha identificador igual ao
 * recebido como parâmetro.  
 */
public class RepositorioTransacao {
	
	private static final String FILE_NAME = "Transacao.txt";
	
	public void incluir(Transacao transacao) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String linha = formatarTransacaoParaLinha(transacao);
            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public Transacao[] buscarPorEntidadeCredora(long identificadorEntidadeCredito) {
		List<Transacao> transacoesEncontradas = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				Transacao transacao = linhaParaTransacao(linha);
				if (transacao.getEntidadeCredito().getIdentificador() == identificadorEntidadeCredito) {
					transacoesEncontradas.add(transacao);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return transacoesEncontradas.toArray(new Transacao[0]);
	}
	
	private Transacao linhaParaTransacao(String linha) {
		String[] dados = linha.split(";");

		long idCredito = Long.parseLong(dados[0]);
		String nomeCredito = dados[1];
		boolean autorizadoCredito = Boolean.parseBoolean(dados[2]);
		double saldoAcaoCredito = Double.parseDouble(dados[3]);
		double saldoTituloDividaCredito = Double.parseDouble(dados[4]);

		EntidadeOperadora entidadeCredito = new EntidadeOperadora(idCredito, nomeCredito, autorizadoCredito, saldoAcaoCredito, saldoTituloDividaCredito);

		long idDebito = Long.parseLong(dados[5]);
		String nomeDebito = dados[6];
		boolean autorizadoDebito = Boolean.parseBoolean(dados[7]);
		double saldoAcaoDebito = Double.parseDouble(dados[8]);
		double saldoTituloDividaDebito = Double.parseDouble(dados[9]);

		EntidadeOperadora entidadeDebito = new EntidadeOperadora(idDebito, nomeDebito, autorizadoDebito, saldoAcaoDebito, saldoTituloDividaDebito);
		
		Acao acao = null;
		if (!dados[10].equals("null")) {
			int idAcao = Integer.parseInt(dados[10]);
			String nomeAcao = dados[11];
			LocalDate dataValidadeAcao = LocalDate.parse(dados[12]);
			double valorUnitarioAcao = Double.parseDouble(dados[13]);
			acao = new Acao(idAcao, nomeAcao, dataValidadeAcao, valorUnitarioAcao);
		}

		TituloDivida tituloDivida = null;
		if (!dados[14].equals("null")) {
			int idTituloDivida = Integer.parseInt(dados[14]);
			String nomeTituloDivida = dados[15];
			LocalDate dataValidadeTituloDivida = LocalDate.parse(dados[16]);
			double taxaJurosTituloDivida = Double.parseDouble(dados[17]);
			tituloDivida = new TituloDivida(idTituloDivida, nomeTituloDivida, dataValidadeTituloDivida, taxaJurosTituloDivida);
		}

		double valorOperacao = Double.parseDouble(dados[18]);
		LocalDateTime dataHoraOperacao = LocalDateTime.parse(dados[19].replace(" ", "T"));

		return new Transacao(entidadeCredito, entidadeDebito, acao, tituloDivida, valorOperacao, dataHoraOperacao);
	}
	
	private String formatarTransacaoParaLinha(Transacao transacao) {
	    // Entidade Credito
	    String linha = transacao.getEntidadeCredito().getIdentificador() + ";" +
	                   transacao.getEntidadeCredito().getNome() + ";" +
	                   transacao.getEntidadeCredito().getAutorizadoAcao() + ";" +
	                   transacao.getEntidadeCredito().getSaldoAcao() + ";" +
	                   transacao.getEntidadeCredito().getSaldoTituloDivida() + ";" ;

	    // Entidade Debito
	    linha += transacao.getEntidadeDebito().getIdentificador() + ";" +
	             transacao.getEntidadeDebito().getNome() + ";" +
	             transacao.getEntidadeDebito().getAutorizadoAcao() + ";" +
	             transacao.getEntidadeDebito().getSaldoAcao() + ";" +
	             transacao.getEntidadeDebito().getSaldoTituloDivida() + ";" ;

	    // Acao
	    if (transacao.getAcao() != null) {
	        linha += transacao.getAcao().getIdentificador() + ";" +
	                 transacao.getAcao().getNome() + ";" +
	                 transacao.getAcao().getDataDeValidade() + ";" +
	                 transacao.getAcao().getValorUnitario() + ";";
	    } else {
	        linha += "null;null;null;null;";
	    }

	    // TituloDivida
	    if (transacao.getTituloDivida() != null) {
	        linha += transacao.getTituloDivida().getIdentificador() + ";" +
	                 transacao.getTituloDivida().getNome() + ";" +
	                 transacao.getTituloDivida().getDataDeValidade() + ";" +
	                 transacao.getTituloDivida().getTaxaJuros() + ";";
	    } else {
	        linha += "null;null;null;null;";
	    }

	    // dataHoraOperacao e valorOperacao
	    linha += transacao.getValorOperacao() + ";" +
	             transacao.getDataHoraOperacao().toString().replace("T", " ");

	    return linha;
	}
}