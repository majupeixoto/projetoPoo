package br.com.cesarschool.poo.titulos.daogenerico;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Entidade implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private LocalDateTime dataHoraInclusao;
    private LocalDateTime dataHoraUltimaAlteracao;
    private String usuarioInclusao;
    private String usuarioUltimaAlteracao;
    
    // Construtor sem parâmetros
    public Entidade() {
        this.dataHoraInclusao = LocalDateTime.now();
        this.dataHoraUltimaAlteracao = LocalDateTime.now();
    }
    
    // Métodos Getters e Setters
    public LocalDateTime getDataHoraInclusao() {
        return dataHoraInclusao;
    }

    public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
        this.dataHoraInclusao = dataHoraInclusao;
    }

    public LocalDateTime getDataHoraUltimaAlteracao() {
        return dataHoraUltimaAlteracao;
    }

    public void setDataHoraUltimaAlteracao(LocalDateTime dataHoraUltimaAlteracao) {
        this.dataHoraUltimaAlteracao = dataHoraUltimaAlteracao;
    }

    public String getUsuarioInclusao() {
        return usuarioInclusao;
    }

    public void setUsuarioInclusao(String usuarioInclusao) {
        this.usuarioInclusao = usuarioInclusao;
    }

    public String getUsuarioUltimaAlteracao() {
        return usuarioUltimaAlteracao;
    }

    public void setUsuarioUltimaAlteracao(String usuarioUltimaAlteracao) {
        this.usuarioUltimaAlteracao = usuarioUltimaAlteracao;
    }
    
    // Método abstrato para obter o ID único
    public abstract String getIdUnico();
}
