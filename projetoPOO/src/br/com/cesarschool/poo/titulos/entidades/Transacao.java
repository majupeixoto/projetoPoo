package br.com.cesarschool.poo.titulos.entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.gov.cesarschool.poo.daogenerico.Entidade;

public class Transacao extends Entidade{
	private EntidadeOperadora entidadeCredito;
	private EntidadeOperadora entidadeDebito;
	private Acao acao;
	private TituloDivida tituloDivida;
	private double valorOperacao;
	private LocalDateTime dataHoraOperacao;
	
	public Transacao(EntidadeOperadora entidadeCredito, EntidadeOperadora entidadeDebito, Acao acao, TituloDivida tituloDivida,
			double valorOperacao, LocalDateTime dataHoraOperacao) {
		super();
		this.entidadeCredito = entidadeCredito;
		this.entidadeDebito = entidadeDebito;
		this.acao = acao;
		this.tituloDivida = tituloDivida;
		this.valorOperacao = valorOperacao;
		this.dataHoraOperacao = dataHoraOperacao;
	}
	
	public EntidadeOperadora getEntidadeCredito() {
        return entidadeCredito;
    }

    public EntidadeOperadora getEntidadeDebito() {
        return entidadeDebito;
    }

    public Acao getAcao() {
        return acao;
    }

    public TituloDivida getTituloDivida() {
        return tituloDivida;
    }

    public double getValorOperacao() {
        return valorOperacao;
    }


    public LocalDateTime getDataHoraOperacao() {
        return dataHoraOperacao;
    }
    
    @Override
    public String getIdUnico() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return entidadeCredito.getIdUnico() + "_" +
               entidadeDebito.getIdUnico() + "_" +
               acao.getIdUnico() + "_" +
               tituloDivida.getIdUnico() + "_" +
               dataHoraOperacao.format(formatter);
    }
}