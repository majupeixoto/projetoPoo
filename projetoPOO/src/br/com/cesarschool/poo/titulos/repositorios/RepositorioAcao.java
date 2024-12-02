package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepositorioAcao extends RepositorioGeral {
	
    private static final String DIRETORIO_REPOSITORIO = "Acao";  // Caminho do diretório
    private DAOSerializadorObjetos dao;

    // Construtor da classe RepositorioAcao
    public RepositorioAcao() {
        this.dao = new DAOSerializadorObjetos(Acao.class); // Instancia o DAO para a classe Acao
        File baseDir = new File(DIRETORIO_REPOSITORIO);
		if (!baseDir.exists()) {
			baseDir.mkdirs(); // Cria o diretório se ele não existir
		}
    }

    @Override
    public Class<?> getClasseEntidade() {
        return Acao.class;
    }

    public DAOSerializadorObjetos getDao() {
        return dao;
    }

    // Método para incluir uma ação
    public boolean incluir(Acao acao) {
        return dao.incluir(acao);  // Chama o método incluir do DAOSerializadorObjetos
    }

    // Método para alterar uma ação
    public boolean alterar(Acao acao) {
        return dao.alterar(acao);  // Chama o método alterar do DAOSerializadorObjetos
    }

    // Método para excluir uma ação pelo identificador
    public boolean excluir(int identificador) {
        return dao.excluir(String.valueOf(identificador));  // Converte identificador para String e chama o método excluir do DAOSerializadorObjetos
    }

    // Método para buscar uma ação pelo identificador
    public Acao buscar(int identificador) {
        return (Acao) dao.buscar(String.valueOf(identificador));  // Converte identificador para String e chama o método buscar do DAOSerializadorObjetos
    }

    // Método para listar todas as ações
    public List<Acao> listar() {
        // Obtém todas as ações do DAO e as converte para uma lista de Acao
        List<Acao> listaAcoes = new ArrayList<>();
        for (var entidade : dao.buscarTodos()) {
            listaAcoes.add((Acao) entidade);  // Adiciona a ação na lista
        }
        return listaAcoes;
    }
}
