package br.com.cesarschool.poo.titulos;

import java.util.Scanner;

import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;
import br.com.cesarschool.poo.titulos.mediators.MediatorEntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorOperacao;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

public class Programa {
	static Scanner ENTRADA = new Scanner(System.in);
	static MediatorAcao mediatorAcao = MediatorAcao.getInstance();
	static MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getInstance();
	static MediatorEntidadeOperadora mediatorEntidadeOperadora = MediatorEntidadeOperadora.getInstance();
	static MediatorOperacao mediatorOperacao = MediatorOperacao.getInstance();

	public static void main(String[] args) {
		
	}
}
