package br.gov.cesarschool.poo.testes;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioGeral;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

public class TesteRepositorioTransacao extends TesteGeral {
	private static final RepositorioTransacao DAO = new RepositorioTransacao();
	private static final String NOME_DIR_TRANSACAO = PONTO + SEP_ARQUIVO + Transacao.class.getSimpleName();
	
	@Test
	public void testDAO00() {
		Assertions.assertTrue(DAO instanceof RepositorioGeral);
		DAOSerializadorObjetos dao = DAO.getDao();
		Assertions.assertNotNull(dao);
	}
	@Test
	public void testDAO01() {
		excluirArquivosDiretorio(NOME_DIR_TRANSACAO);
		Acao acao = new Acao(1, "A1", LocalDate.now(), 100.0);
		EntidadeOperadora eaC = new EntidadeOperadora(2, "EO1", 100.0);
		EntidadeOperadora eaD = new EntidadeOperadora(3, "EO2", 101.0);
		Transacao tr = new Transacao(eaC, eaD, acao, null, 10.0, LocalDateTime.now());
		DAO.incluir(tr);		
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR_TRANSACAO), 1); 
		Assertions.assertTrue(new File(obterNomeArquivo(NOME_DIR_TRANSACAO, tr)).exists());
	}
	@Test
	public void testDAO02() {
		excluirArquivosDiretorio(NOME_DIR_TRANSACAO);
		TituloDivida titulo = new TituloDivida(4, "T4", LocalDate.now(), 102.0);
		EntidadeOperadora eaC = new EntidadeOperadora(5, "EO5", 103.0);
		EntidadeOperadora eaD = new EntidadeOperadora(6, "EO6", 104.0);
		Transacao tr = new Transacao(eaC, eaD, null, titulo, 11.0, LocalDateTime.now());
		DAO.incluir(tr);		
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR_TRANSACAO), 1); 
		Assertions.assertTrue(new File(obterNomeArquivo(NOME_DIR_TRANSACAO, tr)).exists());
	}
}