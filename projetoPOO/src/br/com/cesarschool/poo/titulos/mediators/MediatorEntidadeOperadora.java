package br.com.cesarschool.poo.titulos.mediators;

import java.util.List;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioEntidadeOperadora;

public class MediatorEntidadeOperadora {
    private static MediatorEntidadeOperadora instanciaUnica;
    private final RepositorioEntidadeOperadora repositorioEntidadeOperadora = new RepositorioEntidadeOperadora();

    private MediatorEntidadeOperadora() {}

    public static MediatorEntidadeOperadora getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new MediatorEntidadeOperadora();
        }
        return instanciaUnica;
    }

    private String validar(EntidadeOperadora entidade) {
        if (entidade.getIdentificador() < 100 || entidade.getIdentificador() > 1000000) {
            return "Identificador deve estar entre 100 e 1.000.000.";
        }

        if (entidade.getNome() == null || entidade.getNome().trim().isEmpty()) {
            return "Nome deve ser preenchido.";
        }

        if (entidade.getNome().length() < 10 || entidade.getNome().length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }
        return null;
    }

    public String incluir(EntidadeOperadora entidade) {
        String mensagemValidar = validar(entidade);
        if (mensagemValidar == null) {
            if (repositorioEntidadeOperadora.incluir(entidade)) {
                return null;
            } else {
                return "Entidade já existente.";
            }
        }
        return mensagemValidar;
    }

    public String alterar(EntidadeOperadora entidade) {
        String mensagemValidar = validar(entidade);
        if (mensagemValidar == null) {
            if (repositorioEntidadeOperadora.alterar(entidade)) {
                return null;
            } else {
                return "Entidade inexistente.";
            }
        }
        return mensagemValidar;
    }

    public String excluir(long identificador) {
        if (identificador < 100 || identificador > 1000000) {
            return "Identificador inválido.";
        }

        if (repositorioEntidadeOperadora.excluir(identificador)) {
            return null;
        } else {
            return "Entidade inexistente.";
        }
    }

    public EntidadeOperadora buscar(long identificador) {
        if (identificador < 100 || identificador > 1000000) {
            return null;
        }

        return repositorioEntidadeOperadora.buscar(identificador);
    }
    
    public List<EntidadeOperadora> obterTodasEntidades() {
        return repositorioEntidadeOperadora.buscarTodos();
    }
}