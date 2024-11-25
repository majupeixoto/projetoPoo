package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;

public class RepositorioEntidadeOperadora extends RepositorioGeral {

    @Override
    protected Class<?> getClasseEntidade() {
        return EntidadeOperadora.class;
    }

    public boolean incluir(EntidadeOperadora entidadeOperadora) {
        if (dao.buscar(String.valueOf(entidadeOperadora.getIdentificador())) != null) {
            return false;
        }
        return dao.incluir(entidadeOperadora);
    }

    public boolean alterar(EntidadeOperadora entidadeOperadora) {
        if (dao.buscar(String.valueOf(entidadeOperadora.getIdentificador())) == null) {
            return false;
        }
        return dao.alterar(entidadeOperadora);
    }

    public boolean excluir(long identificador) {
        return dao.excluir(String.valueOf(identificador));
    }

    public EntidadeOperadora buscar(long identificador) {
        return (EntidadeOperadora) dao.buscar(String.valueOf(identificador));
    }
}
