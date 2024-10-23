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

public class RepositorioTransacao {
	
	private static final String FILE_CAMINHO = "src/br/com/cesarschool/poo/titulos/repositorios/transacao.txt"; // Corrigido o caminho do arquivo

	public void incluir(Transacao transacao) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CAMINHO, true))) {
            String linha = formatarTransacaoParaLinha(transacao);
            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public Transacao[] buscarPorEntidadeCredora(long identificadorEntidadeCredito) {
		List<Transacao> transacoesEncontradas = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))) {
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
		if (!"null".equals(dados[10])) {
			int idAcao = Integer.parseInt(dados[10]);
			String nomeAcao = dados[11];
			LocalDate dataValidadeAcao = LocalDate.parse(dados[12]);
			double valorUnitarioAcao = Double.parseDouble(dados[13]);
			acao = new Acao(idAcao, nomeAcao, dataValidadeAcao, valorUnitarioAcao);
		}

		TituloDivida tituloDivida = null;
		if (!"null".equals(dados[14])) {
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
