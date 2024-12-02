package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTituloDivida extends RepositorioGeral {

    private static final String DIRETORIO_REPOSITORIO = "TituloDivida"; // Caminho do diretório
    private DAOSerializadorObjetos dao;

    // Construtor da classe RepositorioTituloDivida
    public RepositorioTituloDivida() {
        this.dao = new DAOSerializadorObjetos(TituloDivida.class); // Instancia o DAO para a classe TituloDivida
        File baseDir = new File(DIRETORIO_REPOSITORIO);
        if (!baseDir.exists()) {
            baseDir.mkdirs(); // Cria o diretório se ele não existir
        }
    }

    @Override
    public Class<?> getClasseEntidade() {
        return TituloDivida.class;
    }

    public DAOSerializadorObjetos getDao() {
        return dao;
    }

    // Método para incluir um TituloDivida
    public boolean incluir(TituloDivida tituloDivida) {
        return dao.incluir(tituloDivida); // Chama o método incluir do DAOSerializadorObjetos
    }

    // Método para alterar um TituloDivida
    public boolean alterar(TituloDivida tituloDivida) {
        return dao.alterar(tituloDivida); // Chama o método alterar do DAOSerializadorObjetos
    }

    // Método para excluir um TituloDivida pelo identificador
    public boolean excluir(int identificador) {
        return dao.excluir(String.valueOf(identificador)); // Converte o identificador para String e chama o método excluir
    }

    // Método para buscar um TituloDivida pelo identificador
    public TituloDivida buscar(int identificador) {
        return (TituloDivida) dao.buscar(String.valueOf(identificador)); // Converte identificador e chama o método buscar
    }

    // Método para listar todos os TituloDivida
    public List<TituloDivida> listar() {
        // Obtém todas as entidades do DAO e as converte para uma lista de TituloDivida
        List<TituloDivida> listaTitulos = new ArrayList<>();
        for (var entidade : dao.buscarTodos()) {
            listaTitulos.add((TituloDivida) entidade); // Adiciona o título na lista
        }
        return listaTitulos;
    }
}
