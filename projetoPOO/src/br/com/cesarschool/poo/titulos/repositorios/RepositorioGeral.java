package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.daogenerico.DAOSerializadorObjetos;

public abstract class RepositorioGeral {
    protected DAOSerializadorObjetos dao;

    public RepositorioGeral() {
        this.dao = new DAOSerializadorObjetos(getClasseEntidade());
    }

    protected abstract Class<?> getClasseEntidade();

    public DAOSerializadorObjetos getDao() {
        return this.dao;
    }
}
