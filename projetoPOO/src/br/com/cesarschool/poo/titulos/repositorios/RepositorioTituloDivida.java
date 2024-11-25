package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

public class RepositorioTituloDivida extends RepositorioGeral {

    @Override
    protected Class<?> getClasseEntidade() {
        return TituloDivida.class;
    }

    public boolean incluir(TituloDivida tituloDivida) {
        if (dao.buscar(String.valueOf(tituloDivida.getIdentificador())) != null) {
            return false;
        }
        return dao.incluir(tituloDivida);
    }

    public boolean alterar(TituloDivida tituloDivida) {
        if (dao.buscar(String.valueOf(tituloDivida.getIdentificador())) == null) {
            return false;
        }
        return dao.alterar(tituloDivida);
    }

    public boolean excluir(int identificador) {
        return dao.excluir(String.valueOf(identificador));
    }

    public TituloDivida buscar(int identificador) {
        return (TituloDivida) dao.buscar(String.valueOf(identificador));
    }
}
