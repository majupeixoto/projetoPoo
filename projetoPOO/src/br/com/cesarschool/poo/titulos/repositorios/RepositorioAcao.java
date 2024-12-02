package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepositorioAcao extends RepositorioGeral {
	
    private static final String DIRETORIO_REPOSITORIO = "Acao";
    private DAOSerializadorObjetos dao;

    public RepositorioAcao() {
        this.dao = new DAOSerializadorObjetos(Acao.class);
        File baseDir = new File(DIRETORIO_REPOSITORIO);
		if (!baseDir.exists()) {
			baseDir.mkdirs();
		}
    }

    @Override
    public Class<?> getClasseEntidade() {
        return Acao.class;
    }

    public DAOSerializadorObjetos getDao() {
        return dao;
    }

    public boolean incluir(Acao acao) {
        return dao.incluir(acao);
    }

    public boolean alterar(Acao acao) {
        return dao.alterar(acao);
    }

    public boolean excluir(int identificador) {
        return dao.excluir(String.valueOf(identificador));
    }

    public Acao buscar(int identificador) {
        return (Acao) dao.buscar(String.valueOf(identificador));
    }

    public List<Acao> listar() {
        List<Acao> listaAcoes = new ArrayList<>();
        for (var entidade : dao.buscarTodos()) {
            listaAcoes.add((Acao) entidade);
        }
        return listaAcoes;
    }
}
