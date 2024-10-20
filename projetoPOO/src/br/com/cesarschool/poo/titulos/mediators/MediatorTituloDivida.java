package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDate;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTituloDivida;

/*
 * Deve ser um singleton.
 * 
 * Deve ter um atributo repositorioTituloDivida, do tipo RepositorioTituloDivida, que deve 
 * ser inicializado na sua declaração. Este atributo será usado exclusivamente
 * pela classe, não tendo, portanto, métodos set e get.
 * 
 * Métodos: 
 * 
 * pivate String validar(TituloDivida titulo): deve validar os dados do objeto recebido nos seguintes termos: 
 * identificador: deve ser maior que zero e menor que 100000 (1)
 * nome: deve ser preenchido, diferente de branco e de null (2). deve ter entre 10 e 100 caracteres (3).
 * data de validade: deve ser maior do que a data atual mais 180 dias (4). 
 * valorUnitario: deve ser maior que zero (5). 
 * O método validar deve retornar null se o objeto estiver válido, e uma mensagem pertinente (ver abaixo)
 * se algum valor de atributo estiver inválido.
 * 
 * (1) - Identificador deve estar entre 1 e 99999.
 * (2) - Nome deve ser preenchido.
 * (3) - Nome deve ter entre 10 e 100 caracteres.
 * (4) - Data de validade deve ter pelo menos 180 dias na frente da data atual.
 * (5) - Taxa de juros deve ser maior ou igual a zero.
 *
 * public String incluir(TituloDivida titulo): deve chamar o método validar. Se ele 
 * retornar null, deve incluir titulo no repositório. Retornos possíveis:
 * (1) null, se o retorno do validar for null e o retorno do incluir do 
 * repositório for true.
 * (2) a mensagem retornada pelo validar, se o retorno deste for diferente
 * de null.
 * (3) A mensagem "Título já existente", se o retorno do validar for null
 * e o retorno do repositório for false.
 *
 * public String alterar(TituloDivida titulo): deve chamar o método validar. Se ele 
 * retornar null, deve alterar titulo no repositório. Retornos possíveis:
 * (1) null, se o retorno do validar for null e o retorno do alterar do 
 * repositório for true.
 * (2) a mensagem retornada pelo validar, se o retorno deste for diferente
 * de null.
 * (3) A mensagem "Título inexistente", se o retorno do validar for null
 * e o retorno do repositório for false.
 * 
 * public String excluir(int identificador): deve validar o identificador. 
 * Se este for válido, deve chamar o excluir do repositório. Retornos possíveis:
 * (1) null, se o retorno do excluir do repositório for true.
 * (2) A mensagem "Título inexistente", se o retorno do repositório for false
 * ou se o identificador for inválido.
 * 
 * public TituloDivida buscar(int identificador): deve validar o identificador.
 * Se este for válido, deve chamar o buscar do repositório, retornando o 
 * que ele retornar. Se o identificador for inválido, retornar null. 
 */
public class MediatorTituloDivida {
	private static MediatorTituloDivida instanciaUnica;
	private final RepositorioTituloDivida repositorioTituloDivida = new RepositorioTituloDivida();
	
	private MediatorTituloDivida() {}
	
		public static MediatorTituloDivida getInstance() {
			if(instanciaUnica == null) {
				instanciaUnica = new MediatorTituloDivida();
			}
			
			return instanciaUnica;
		}
		
		private String validar(TituloDivida tituloDivida) {
			if(tituloDivida.getIdentificador() < 1 || tituloDivida.getIdentificador() > 99999) {
				return "Identificador deve estar entre 1 e 99999.";
			}
			
			if(tituloDivida.getNome() == null || tituloDivida.getNome().trim().isEmpty()) {
				return "Nome deve ser preenchido.";
			}
			
			if(tituloDivida.getNome().length() < 10 || tituloDivida.getNome().length() > 100) {
				return "Nome deve ter entre 10 e 100 caracteres.";
			}
			
			if(tituloDivida.getDataDeValidade().isBefore(LocalDate.now().plusDays(180))) {
				return "Data de validade deve ter pelo menos 180 dias na frente da data atual.";
			}
			
			if(tituloDivida.getTaxaJuros() < 0) {
				return "Taxa de juros deve ser maior ou igual a zero.";
			}
			
			return null;
		}
		
		public String incluir(TituloDivida tituloDivida) {
			String mensagemValidar = validar(tituloDivida);
			if (mensagemValidar != null) {
				return mensagemValidar;
			}
			
			boolean conferir = repositorioTituloDivida.incluir(tituloDivida);
			if (conferir) {
				return null;
			} else {
				return "Título já existente";
			}
		}
		
		public String alterar(TituloDivida tituloDivida) {
			String mensagemValidar = validar(tituloDivida);
			if (mensagemValidar != null) {
				return mensagemValidar;
			}
			
			boolean conferir = repositorioTituloDivida.alterar(tituloDivida);
			if (conferir) {
				return null;
			} else {
				return "Título inexistente";
			}
		}
		
		public String excluir(int identificador) {
			if (identificador < 1 || identificador > 99999) {
				return "Identificador deve estar entre 1 e 99999.";
			}
			
			boolean conferir = repositorioTituloDivida.excluir(identificador);
			if (conferir) {
				return null;
			} else {
				return "Título inexistente";
			}
		}
		
		public TituloDivida buscar(int identificador) {
			if (identificador < 1 || identificador > 99999) {
				return null;
			}
			return repositorioTituloDivida.buscar(identificador);
		}
}