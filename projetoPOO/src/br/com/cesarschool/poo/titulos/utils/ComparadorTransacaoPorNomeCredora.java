package br.com.cesarschool.poo.titulos.utils;

import br.com.cesarschool.poo.titulos.entidades.Transacao;

public class ComparadorTransacaoPorNomeCredora extends ComparadorPadrao implements Comparador {
    
    @Override
    public int comparar(Comparavel c1, Comparavel c2) {
        if (c1 instanceof Transacao && c2 instanceof Transacao) {
            Transacao t1 = (Transacao) c1;
            Transacao t2 = (Transacao) c2;
            
            String nomeCredora1 = t1.getEntidadeCredito().getNome();
            String nomeCredora2 = t2.getEntidadeCredito().getNome();
            
            return nomeCredora1.compareToIgnoreCase(nomeCredora2);
        }
        throw new IllegalArgumentException("Erro");
    }
}
