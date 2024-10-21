package br.com.cesarschool.poo.titulos.entidades;
/*
 * Esta classe deve ter os seguintes atributos: OK
 * identificador, do tipo long
 * nome, do tipo String
 * autorizadoAcao, do tipo double
 * saldoAcao, do tipo double
 * saldoTituloDivida, do tipo double
 * 
 * Deve ter um construtor público que inicializa os atributos identificador, nome OK 
 * e autorizadoAcao. Deve ter métodos set/get públicos para os atributos identificador, nome
 * e autorizadoAcao. O atributo identificador é read-only fora da classe.
 * 
 * Os atributos saldoAcao e saldoTituloDivida devem ter apenas métodos get públicos. OK
 * 
 * Outros métodos públicos: OK
 * 
 *  void creditarSaldoAcao(double valor): deve adicionar valor ao saldoAcao OK
 *  void debitarSaldoAcao(double valor): deve diminuir valor de saldoAcao OK
 *  void creditarSaldoTituloDivida(double valor): deve adicionar valor ao saldoTituloDivida OK
 *  void debitarSaldoTituloDivida(double valor): deve diminuir valor de saldoTituloDivida  OK
 */
public class EntidadeOperadora {
	private long identificador;
	private String nome;
	private boolean autorizadoAcao;
	private double saldoAcao;
	private double saldoTituloDivida;
	
	public EntidadeOperadora(long identificador, String nome, boolean autorizadoAcao, double saldoAcao, double saldoTituloDivida) {
		this.identificador = identificador;
		this.nome = nome;
		this.autorizadoAcao = autorizadoAcao;
		this.saldoAcao = 0.0;
		this.saldoTituloDivida = 0.0;
	}
	
	public long getIdentificador() {
		return identificador;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean getAutorizadoAcao() {
		return autorizadoAcao;
	}
	
	public void setAutorizadoAcao(boolean autorizadoAcao) {
		this.autorizadoAcao = autorizadoAcao;
	}
	
	public double getSaldoAcao() {
		return saldoAcao;
	}
	
	public double getSaldoTituloDivida() {
		return saldoTituloDivida;
	}
	
	public void creditarSaldoAcao(double valor) {
		if(valor > 0) {
			this.saldoAcao += valor;
		}
	}
	
	public void debitarSaldoAcao(double valor) {
		if(valor > 0 && this.saldoAcao >= valor) {
			this.saldoAcao -= valor;
		}
	}
	
	public void creditarSaldoTituloDivida(double valor) {
		if(valor > 0) {
			this.saldoTituloDivida += valor;
		}
	}
	
	public void debitarSaldoTituloDivida(double valor) {
		if(valor > 0 && this.saldoTituloDivida >= valor) {
			this.saldoTituloDivida -= valor;
		}
	}
}