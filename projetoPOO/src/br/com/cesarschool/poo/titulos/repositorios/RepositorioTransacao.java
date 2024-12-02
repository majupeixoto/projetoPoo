package br.com.cesarschool.poo.titulos.repositorios;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;
import br.gov.cesarschool.poo.daogenerico.Entidade;

public class RepositorioTransacao extends RepositorioGeral {
    private static final String DIRETORIO_REPOSITORIO = "Transacao";
    private DAOSerializadorObjetos dao;

    public RepositorioTransacao() {
        this.dao = new DAOSerializadorObjetos(Transacao.class);
        File baseDir = new File(DIRETORIO_REPOSITORIO);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
    }

    @Override
    public Class<?> getClasseEntidade() {
        return Transacao.class;
    }

    @Override
    public DAOSerializadorObjetos getDao() {
        return dao;
    }

    public boolean incluir(Transacao transacao) {
        return dao.incluir(transacao);
    }

    public Transacao[] buscarPorEntidadeDevedora(long identificadorEntidadeDebito) {
        Entidade[] entidades = dao.buscarTodos();
        
        List<Transacao> transacoesEncontradas = new ArrayList<>();
        for (Entidade entidade : entidades) {
            if (entidade instanceof Transacao) {
                Transacao transacao = (Transacao) entidade;
                if (transacao.getEntidadeDebito().getIdentificador() == identificadorEntidadeDebito) {
                    transacoesEncontradas.add(transacao);
                }
            }
        }
        return transacoesEncontradas.toArray(new Transacao[0]);
    }

    public Transacao[] buscarPorEntidadeCredora(long identificadorEntidadeCredito) {
        Entidade[] entidades = dao.buscarTodos();
        
        List<Transacao> transacoesEncontradas = new ArrayList<>();
        for (Entidade entidade : entidades) {
            if (entidade instanceof Transacao) {
                Transacao transacao = (Transacao) entidade;
                if (transacao.getEntidadeCredito().getIdentificador() == identificadorEntidadeCredito) {
                    transacoesEncontradas.add(transacao);
                }
            }
        }
        return transacoesEncontradas.toArray(new Transacao[0]);
    }
    
    public Transacao[] listar() {
        return (Transacao[]) dao.buscarTodos();
    }
}
