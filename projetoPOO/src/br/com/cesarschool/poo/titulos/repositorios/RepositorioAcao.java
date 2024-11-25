package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;

public class RepositorioAcao extends RepositorioGeral {

    @Override
    protected Class<?> getClasseEntidade() {
        return Acao.class;
    }

	public boolean incluir(Acao acao) {
		if (dao.buscar(String.valueOf(acao.getIdentificador())) != null) {
			return false;
		}
		return dao.incluir(acao);
		
	}

    public boolean alterar(Acao acao) {
        if (dao.buscar(String.valueOf(acao.getIdentificador())) == null) {
            return false;
        }
        return dao.alterar(acao);
    }

    public boolean excluir(int identificador) {
        return dao.excluir(String.valueOf(identificador));
    }

    public Acao buscar(int identificador) {
        return (Acao) dao.buscar(String.valueOf(identificador));
    }
}
