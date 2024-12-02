package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEntidadeOperadora extends RepositorioGeral {

    private static final String DIRETORIO_REPOSITORIO = "EntidadeOperadora";
    private DAOSerializadorObjetos dao;

    public RepositorioEntidadeOperadora() {
        this.dao = new DAOSerializadorObjetos(EntidadeOperadora.class);
        File baseDir = new File(DIRETORIO_REPOSITORIO);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
    }

    @Override
    public Class<?> getClasseEntidade() {
        return EntidadeOperadora.class;
    }

    public DAOSerializadorObjetos getDao() {
        return dao;
    }

    public boolean incluir(EntidadeOperadora entidadeOperadora) {
        return dao.incluir(entidadeOperadora);
    }

    public boolean alterar(EntidadeOperadora entidadeOperadora) {
        return dao.alterar(entidadeOperadora);
    }

    public boolean excluir(long identificador) {
        return dao.excluir(String.valueOf(identificador));
    }

    public EntidadeOperadora buscar(long identificador) {
        return (EntidadeOperadora) dao.buscar(String.valueOf(identificador));
    }

    public List<EntidadeOperadora> listar() {
        List<EntidadeOperadora> listaEntidades = new ArrayList<>();
        for (var entidade : dao.buscarTodos()) {
            listaEntidades.add((EntidadeOperadora) entidade);
        }
        return listaEntidades;
    }
}
