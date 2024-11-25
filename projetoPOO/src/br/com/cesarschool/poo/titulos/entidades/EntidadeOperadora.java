package br.com.cesarschool.poo.titulos.entidades;

import br.com.cesarschool.poo.titulos.daogenerico.Entidade;

public class EntidadeOperadora extends Entidade {
    private long identificador;
    private String nome;
    private boolean autorizadoAcao;
    private double saldoAcao;
    private double saldoTituloDivida;

    public EntidadeOperadora(long identificador, String nome, boolean autorizadoAcao) {
        super();
        this.identificador = identificador;
        this.nome = nome;
        this.autorizadoAcao = autorizadoAcao;
    }
    
    public EntidadeOperadora(long identificador, String nome, double saldoAcao) {
        super();
        this.identificador = identificador;
        this.nome = nome;
        this.saldoAcao = saldoAcao;
        this.autorizadoAcao = false;
    }

    public long getIdentificador() {
        return identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean getAutorizadoAcao() {
        return autorizadoAcao;
    }

    public void setAutorizadoAcao(boolean autorizadoAcao) {
        this.autorizadoAcao = autorizadoAcao;
    }

    public double getSaldoAcao() {
        return saldoAcao;
    }

    public double getSaldoTituloDivida() {
        return saldoTituloDivida;
    }

    public void creditarSaldoAcao(double valor) {
        if (valor > 0) {
            this.saldoAcao += valor;
        }
    }

    public void debitarSaldoAcao(double valor) {
        if (valor > 0 && this.saldoAcao >= valor) {
            this.saldoAcao -= valor;
        }
    }

    public void creditarSaldoTituloDivida(double valor) {
        if (valor > 0) {
            this.saldoTituloDivida += valor;
        }
    }

    public void debitarSaldoTituloDivida(double valor) {
        if (valor > 0 && this.saldoTituloDivida >= valor) {
            this.saldoTituloDivida -= valor;
        }
    }

    @Override
    public String getIdUnico() {
        return String.valueOf(identificador);
    }
}
