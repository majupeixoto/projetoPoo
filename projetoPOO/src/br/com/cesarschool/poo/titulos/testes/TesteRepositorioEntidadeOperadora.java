package br.com.cesarschool.poo.titulos.testes;

import java.io.File;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import br.com.cesarschool.poo.titulos.daogenerico.DAOSerializadorObjetos;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioEntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioGeral;

public class TesteRepositorioEntidadeOperadora extends TesteGeral {
	private static final RepositorioEntidadeOperadora DAO = new RepositorioEntidadeOperadora();
	private static final String NOME_DIR_EO = PONTO + SEP_ARQUIVO + EntidadeOperadora.class.getSimpleName();
	
	@Test
	public void testDAO00() {
		Assertions.assertTrue(DAO instanceof RepositorioGeral);
		DAOSerializadorObjetos dao = DAO.getDao();
		Assertions.assertNotNull(dao);
	}
	@Test
	public void testDAO01() {
		excluirArquivosDiretorio(NOME_DIR_EO);
		EntidadeOperadora ea = new EntidadeOperadora(1, "EO1", 100.0);
		Assertions.assertTrue(DAO.incluir(ea));		
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR_EO), 1); 
		Assertions.assertTrue(new File(obterNomeArquivo(NOME_DIR_EO, ea)).exists());
		EntidadeOperadora ea1 = DAO.buscar(ea.getIdentificador());
		Assertions.assertNotNull(ea1);
		Assertions.assertNotNull(ea1.getDataHoraInclusao()); 
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ea, ea1));								
	}
	@Test
	public void testDAO02() {
		excluirArquivosDiretorio(NOME_DIR_EO);
		EntidadeOperadora ea = new EntidadeOperadora(2, "EO2", 101.0);
		Assertions.assertTrue(DAO.incluir(ea));
		Assertions.assertFalse(DAO.incluir(ea));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR_EO), 1); 
	}
	@Test
	public void testDAO03() {
		excluirArquivosDiretorio(NOME_DIR_EO);
		long id = 3;
		EntidadeOperadora ea = new EntidadeOperadora(id, "EO3", 102.0);
		EntidadeOperadora eaAlt = new EntidadeOperadora(id, "EO4", 103.0);
		Assertions.assertTrue(DAO.incluir(ea));		
		Assertions.assertTrue(DAO.alterar(eaAlt));
		EntidadeOperadora ea1 = DAO.buscar(ea.getIdentificador());
		Assertions.assertNotNull(ea1);
		Assertions.assertNotNull(ea1.getDataHoraUltimaAlteracao()); 
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(eaAlt, ea1));								
	}
	@Test
	public void testDAO04() {
		excluirArquivosDiretorio(NOME_DIR_EO);
		EntidadeOperadora ea = new EntidadeOperadora(4, "EO5", 104.0);
		EntidadeOperadora eaAlt = new EntidadeOperadora(5, "EO6", 105.0);
		Assertions.assertTrue(DAO.incluir(ea));
		Assertions.assertFalse(DAO.alterar(eaAlt));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR_EO), 1); 
	}
	@Test
	public void testDAO05() {
		excluirArquivosDiretorio(NOME_DIR_EO);
		int id = 6;
		EntidadeOperadora ea = new EntidadeOperadora(id, "EO7", 106.0);		
		Assertions.assertTrue(DAO.incluir(ea));
		Assertions.assertTrue(DAO.excluir(id));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR_EO), 0);
		EntidadeOperadora eaBusc = DAO.buscar(id);
		Assertions.assertNull(eaBusc);
	}
	@Test
	public void testDAO06() {
		excluirArquivosDiretorio(NOME_DIR_EO);		
		EntidadeOperadora ea = new EntidadeOperadora(7, "EO8", 107.0);		
		Assertions.assertTrue(DAO.incluir(ea));
		Assertions.assertFalse(DAO.excluir(8));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR_EO), 1);
	}
}