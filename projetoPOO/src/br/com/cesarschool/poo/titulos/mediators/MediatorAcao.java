package br.com.cesarschool.poo.titulos.mediators;


import java.time.LocalDate;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioAcao;

/*
 * Deve ser um singleton.
 * 
 * Deve ter um atributo repositorioAcao, do tipo RepositorioAcao, que deve 
 * ser inicializado na sua declaração. Este atributo será usado exclusivamente
 * pela classe, não tendo, portanto, métodos set e get. OK
 * 
 * Métodos: 
 * 
 * pivate String validar(Acao acao): deve validar os dados do objeto recebido nos seguintes termos: 
 * identificador: deve ser maior que zero e menor que 100000 (1)
 * nome: deve ser preenchido, diferente de branco e de null (2). deve ter entre 10 e 100 caracteres (3).
 * data de validade: deve ser maior do que a data atual mais 30 dias (4). 
 * valorUnitario: deve ser maior que zero (5). 
 * O método validar deve retornar null se o objeto estiver válido, e uma mensagem pertinente (ver abaixo)
 * se algum valor de atributo estiver inválido.
 * 
 * (1) - Identificador deve estar entre 1 e 99999.
 * (2) - Nome deve ser preenchido.
 * (3) - Nome deve ter entre 10 e 100 caracteres.
 * (4) - Data de validade deve ter pelo menos 30 dias na frente da data atual.
 * (5) - Valor unitário deve ser maior que zero.
 *
 * public String incluir(Acao acao): deve chamar o método validar. Se ele 
 * retornar null, deve incluir acao no repositório. Retornos possíveis:
 * (1) null, se o retorno do validar for null e o retorno do incluir do 
 * repositório for true.
 * (2) a mensagem retornada pelo validar, se o retorno deste for diferente
 * de null.
 * (3) A mensagem "Ação já existente", se o retorno do validar for null
 * e o retorno do repositório for false.
 *
 * public String alterar(Acao acao): deve chamar o método validar. Se ele 
 * retornar null, deve alterar acao no repositório. Retornos possíveis:
 * (1) null, se o retorno do validar for null e o retorno do alterar do 
 * repositório for true.
 * (2) a mensagem retornada pelo validar, se o retorno deste for diferente
 * de null.
 * (3) A mensagem "Ação inexistente", se o retorno do validar for null
 * e o retorno do repositório for false.
 * 
 * public String excluir(int identificador): deve validar o identificador. 
 * Se este for válido, deve chamar o excluir do repositório. Retornos possíveis:
 * (1) null, se o retorno do excluir do repositório for true.
 * (2) A mensagem "Ação inexistente", se o retorno do repositório for false
 * ou se o identificador for inválido.
 * 
 * public Acao buscar(int identificador): deve validar o identificador.
 * Se este for válido, deve chamar o buscar do repositório, retornando o 
 * que ele retornar. Se o identificador for inválido, retornar null. 
 */
public class MediatorAcao {
	private static MediatorAcao instanciaUnica;
	private final RepositorioAcao repositorioAcao = new RepositorioAcao();
	
	private MediatorAcao() {}
	
	public static MediatorAcao getInstance() {
		if(instanciaUnica == null) {
			instanciaUnica = new MediatorAcao();
		}
		return instanciaUnica;
	}
	
	private String validar(Acao acao) {
		if (acao.getIdentificador() < 1 || acao.getIdentificador() > 99999) {
            return "Identificador deve estar entre 1 e 99999.";
        }

        if (acao.getNome() == null || acao.getNome().trim().isEmpty()) {
            return "Nome deve ser preenchido.";
        }

        if (acao.getNome().length() < 10 || acao.getNome().length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }

        if (acao.getDataDeValidade().isBefore(LocalDate.now().plusDays(30))) {
            return "Data de validade deve ter pelo menos 30 dias na frente da data atual.";
        }

        if (acao.getValorUnitario() <= 0) {
            return "Valor unitário deve ser maior que zero.";
        }

        return null;
	}
	
	public String incluir(Acao acao) {
		String mensagemValidar = validar(acao);
        
        if (mensagemValidar != null) {
            return mensagemValidar;
        }
        
        boolean conferir = repositorioAcao.incluir(acao);
        
        if (conferir) {
            return null;
        } else {
            return "Ação já existente";
        }
	}
	
	public String alterar(Acao acao) {
		String mensagemValidar = validar(acao);
		
		if(mensagemValidar != null) {
			return mensagemValidar;
		}
		
		boolean conferir = repositorioAcao.alterar(acao);
		
		if(conferir) {
			return null;
		} else {
			return "Ação inexistente";
		}
	}
	
	public String excluir(int identificador) {
		if (identificador < 1 || identificador > 99999) {
            return "Identificador deve estar entre 1 e 99999.";
        }
		
		boolean conferir = repositorioAcao.excluir(identificador);

        if (conferir) {
            return null;
        } else {
            return "Ação inexistente";
        }
	}
	
	public Acao buscar(int identificador) {
		if (identificador < 1 || identificador > 99999) {
	        return null;
	    }
	    return repositorioAcao.buscar(identificador);
	}
}