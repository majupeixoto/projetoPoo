package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTituloDivida extends RepositorioGeral {

    private static final String DIRETORIO_REPOSITORIO = "TituloDivida";
    private DAOSerializadorObjetos dao;

    public RepositorioTituloDivida() {
        this.dao = new DAOSerializadorObjetos(TituloDivida.class);
        File baseDir = new File(DIRETORIO_REPOSITORIO);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
    }

    @Override
    public Class<?> getClasseEntidade() {
        return TituloDivida.class;
    }

    public DAOSerializadorObjetos getDao() {
        return dao;
    }

    public boolean incluir(TituloDivida tituloDivida) {
        return dao.incluir(tituloDivida);
    }

    public boolean alterar(TituloDivida tituloDivida) {
        return dao.alterar(tituloDivida);
    }

    public boolean excluir(int identificador) {
        return dao.excluir(String.valueOf(identificador));
    }

    public TituloDivida buscar(int identificador) {
        return (TituloDivida) dao.buscar(String.valueOf(identificador));
    }

    public List<TituloDivida> listar() {
        List<TituloDivida> listaTitulos = new ArrayList<>();
        for (var entidade : dao.buscarTodos()) {
            listaTitulos.add((TituloDivida) entidade);
        }
        return listaTitulos;
    }
}
