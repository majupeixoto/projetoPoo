package br.com.cesarschool.poo.titulos.testes;

import br.com.cesarschool.poo.titulos.daogenerico.Entidade;
import br.com.cesarschool.poo.titulos.utils.Comparavel;

class EntidadeModelo extends Entidade implements Comparavel {
	private int id; 
	private String nome;
	EntidadeModelo(int id, String nome) {
		this.id = id;
		this.nome = nome; 
	}		
	int getId() {
		return id;
	}
	String getNome() {
		return nome;
	}
	public int comparar(Comparavel c1) {
		EntidadeModelo em = (EntidadeModelo)c1;			
		return nome.compareTo(em.nome);
	}
	public String getIdUnico() {
		return "" + id;
	}		
	public String toString() {
		return nome;
	}
}