package br.com.cesarschool.poo.titulos.entidades;

import java.time.LocalDate;

import br.com.cesarschool.poo.titulos.daogenerico.Entidade;

public class Ativo extends Entidade {
    private int identificador; // Read-only fora da classe
    private String nome;
    private LocalDate dataDeValidade;

    public Ativo(int identificador, String nome, LocalDate dataDeValidade) {
        super();
        this.identificador = identificador;
        this.nome = nome;
        this.dataDeValidade = dataDeValidade;
    }

    public int getIdentificador() {
        return identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }

    public void setDataDeValidade(LocalDate dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }

    @Override
    public String getIdUnico() {
        return String.valueOf(identificador);
    }
}
