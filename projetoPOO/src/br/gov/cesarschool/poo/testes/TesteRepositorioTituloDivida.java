package br.gov.cesarschool.poo.testes;

import java.io.File;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioGeral;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTituloDivida;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

public class TesteRepositorioTituloDivida extends TesteGeral {
	private static final RepositorioTituloDivida DAO = new RepositorioTituloDivida();
	private static final String NOME_DIR_TITULO = PONTO + SEP_ARQUIVO + TituloDivida.class.getSimpleName();
	
	@Test
	public void testDAO00() {
		Assertions.assertTrue(DAO instanceof RepositorioGeral);
		DAOSerializadorObjetos dao = DAO.getDao();
		Assertions.assertNotNull(dao);
	}
	@Test
	public void testDAO01() {
		excluirArquivosDiretorio(NOME_DIR_TITULO);
		TituloDivida acao = new TituloDivida(1, "A1", LocalDate.now(), 100.0);
		Assertions.assertTrue(DAO.incluir(acao));		
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR_TITULO), 1); 
		Assertions.assertTrue(new File(obterNomeArquivo(NOME_DIR_TITULO, acao)).exists());
		TituloDivida acao1 = DAO.buscar(acao.getIdentificador());
		Assertions.assertNotNull(acao1);
		Assertions.assertNotNull(acao1.getDataHoraInclusao()); 
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(acao, acao1));								
	}
	@Test
	public void testDAO02() {
		excluirArquivosDiretorio(NOME_DIR_TITULO);
		TituloDivida acao = new TituloDivida(2, "A2", LocalDate.now(), 101.0);
		Assertions.assertTrue(DAO.incluir(acao));
		Assertions.assertFalse(DAO.incluir(acao));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR_TITULO), 1); 
	}
	@Test
	public void testDAO03() {
		excluirArquivosDiretorio(NOME_DIR_TITULO);
		int id = 3;
		TituloDivida acao = new TituloDivida(id, "A3", LocalDate.now(), 102.0);
		TituloDivida acaoAlt = new TituloDivida(id, "A3Alt", LocalDate.now().minusDays(10), 103.0);
		Assertions.assertTrue(DAO.incluir(acao));		
		Assertions.assertTrue(DAO.alterar(acaoAlt));
		TituloDivida acao1 = DAO.buscar(acao.getIdentificador());
		Assertions.assertNotNull(acao1);
		Assertions.assertNotNull(acao1.getDataHoraUltimaAlteracao()); 
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(acaoAlt, acao1));								
	}
	@Test
	public void testDAO04() {
		excluirArquivosDiretorio(NOME_DIR_TITULO);
		TituloDivida acao = new TituloDivida(4, "A4", LocalDate.now(), 104.0);
		TituloDivida acaoAlt = new TituloDivida(5, "A5", LocalDate.now().minusDays(11), 105.0);
		Assertions.assertTrue(DAO.incluir(acao));
		Assertions.assertFalse(DAO.alterar(acaoAlt));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR_TITULO), 1); 
	}
	@Test
	public void testDAO05() {
		excluirArquivosDiretorio(NOME_DIR_TITULO);
		int id = 6;
		TituloDivida acao = new TituloDivida(id, "A6", LocalDate.now(), 106.0);		
		Assertions.assertTrue(DAO.incluir(acao));
		Assertions.assertTrue(DAO.excluir(id));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR_TITULO), 0);
		TituloDivida acaoBusc = DAO.buscar(id);
		Assertions.assertNull(acaoBusc);
	}
	@Test
	public void testDAO06() {
		excluirArquivosDiretorio(NOME_DIR_TITULO);		
		TituloDivida acao = new TituloDivida(7, "A7", LocalDate.now(), 107.0);		
		Assertions.assertTrue(DAO.incluir(acao));
		Assertions.assertFalse(DAO.excluir(8));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR_TITULO), 1);
	}
}