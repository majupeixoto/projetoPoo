package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEntidadeOperadora extends RepositorioGeral {

    private static final String DIRETORIO_REPOSITORIO = "EntidadeOperadora"; // Caminho do diretório
    private DAOSerializadorObjetos dao;

    // Construtor da classe RepositorioEntidadeOperadora
    public RepositorioEntidadeOperadora() {
        this.dao = new DAOSerializadorObjetos(EntidadeOperadora.class); // Instancia o DAO para a classe EntidadeOperadora
        File baseDir = new File(DIRETORIO_REPOSITORIO);
        if (!baseDir.exists()) {
            baseDir.mkdirs(); // Cria o diretório se ele não existir
        }
    }

    @Override
    public Class<?> getClasseEntidade() {
        return EntidadeOperadora.class; // Retorna a classe EntidadeOperadora como entidade gerenciada
    }

    public DAOSerializadorObjetos getDao() {
        return dao; // Retorna o DAO
    }

    // Método para incluir uma EntidadeOperadora
    public boolean incluir(EntidadeOperadora entidadeOperadora) {
        return dao.incluir(entidadeOperadora); // Chama o método incluir do DAOSerializadorObjetos
    }

    // Método para alterar uma EntidadeOperadora
    public boolean alterar(EntidadeOperadora entidadeOperadora) {
        return dao.alterar(entidadeOperadora); // Chama o método alterar do DAOSerializadorObjetos
    }

    // Método para excluir uma EntidadeOperadora pelo identificador
    public boolean excluir(long identificador) {
        return dao.excluir(String.valueOf(identificador)); // Converte o identificador para String e chama o método excluir
    }

    // Método para buscar uma EntidadeOperadora pelo identificador
    public EntidadeOperadora buscar(long identificador) {
        return (EntidadeOperadora) dao.buscar(String.valueOf(identificador)); // Converte identificador e chama o método buscar
    }

    // Método para listar todas as EntidadeOperadora
    public List<EntidadeOperadora> listar() {
        // Obtém todas as entidades do DAO e as converte para uma lista de EntidadeOperadora
        List<EntidadeOperadora> listaEntidades = new ArrayList<>();
        for (var entidade : dao.buscarTodos()) {
            listaEntidades.add((EntidadeOperadora) entidade); // Adiciona a entidade na lista
        }
        return listaEntidades;
    }
}
