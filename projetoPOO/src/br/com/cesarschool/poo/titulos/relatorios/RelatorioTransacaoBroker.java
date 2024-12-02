package br.com.cesarschool.poo.titulos.relatorios;

import java.util.ArrayList;

import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.utils.ComparadorTransacaoPorNomeCredora;
import br.com.cesarschool.poo.titulos.utils.Ordenador;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;
import br.gov.cesarschool.poo.daogenerico.Entidade;

public class RelatorioTransacaoBroker {

    public Transacao[] relatorioTransacaoPorNomeEntidadeCredora() {
        ComparadorTransacaoPorNomeCredora comparador = new ComparadorTransacaoPorNomeCredora();
        DAOSerializadorObjetos daoSerializador = new DAOSerializadorObjetos(Transacao.class);
        Entidade[] entidadesListadas = daoSerializador.buscarTodos();
        
        ArrayList<Transacao> transacoesListadas = new ArrayList<>();

        for (Entidade entidadeItem : entidadesListadas) {
            if (entidadeItem instanceof Transacao) {
                transacoesListadas.add((Transacao) entidadeItem);
            }
        }

        Transacao[] transacoesOrdenadas = transacoesListadas.toArray(new Transacao[0]);
        
        Ordenador.ordenar(transacoesOrdenadas, comparador);
        
        return transacoesOrdenadas;
    }
    
    public Transacao[] relatorioTransacaoPorDataHora() {
        DAOSerializadorObjetos daoSerializador = new DAOSerializadorObjetos(Transacao.class);
        Entidade[] entidadesListadas = daoSerializador.buscarTodos();
        
        ArrayList<Transacao> transacoesListadas = new ArrayList<>();

        for (Entidade entidadeItem : entidadesListadas) {
            if (entidadeItem instanceof Transacao) {
                transacoesListadas.add((Transacao) entidadeItem);
            }
        }

        Transacao[] transacoesOrdenadas = transacoesListadas.toArray(new Transacao[0]);
        
        Ordenador.ordenar(transacoesOrdenadas);
        
        return transacoesOrdenadas;
    }
}
