package br.gov.cesarschool.poo.testes;

import java.io.File;
import java.io.Serializable;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;
import br.gov.cesarschool.poo.daogenerico.Entidade;

public class TestesDAOSerializador extends TesteGeral {
	private static final String NOME_2 = "ENT NEW";
	private static final String ID_2 = "2";
	private static final String NOME_1 = "ENT 1";
	private static final String ID_1 = "1";
	static class EntidadeTeste extends Entidade {
		private static final long serialVersionUID = 1L;
		private String id;
		private String nome;
		EntidadeTeste(String id, String nome) {
			this.id = id;
			this.nome = nome;
		}
		public String getIdUnico() {			
			return id;
		}		
		String getId() {
			return id;
		}
		String getNome() {
			return nome; 
		}
	}
	private static final DAOSerializadorObjetos DAO = new DAOSerializadorObjetos(EntidadeTeste.class);
	@Test
	public void testEntidade() {
		Class<Entidade> classe = Entidade.class;
		Class<?>[] interfaces = classe.getInterfaces();
		for (Class<?> class1 : interfaces) {
			if (class1.equals(Serializable.class)) {
				return;
			}
		}
		Assertions.fail();
	}
	@Test
	public void testDAO01() {
		excluirArquivosDiretorio();
		String id = ID_1;
		String nome = NOME_1;
		EntidadeTeste e1 = new EntidadeTeste(id, nome);
		Assertions.assertTrue(DAO.incluir(e1));		
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR), 1); 
		Assertions.assertTrue(new File(obterNomeArquivo(e1)).exists());
		EntidadeTeste eb = (EntidadeTeste)DAO.buscar(id);
		Assertions.assertNotNull(eb);
		Assertions.assertNotNull(eb.getDataHoraInclusao()); 
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(e1, eb));								
	}
	@Test
	public void testDAO02() {
		excluirArquivosDiretorio();
		String id = ID_1;
		String nome = NOME_1;
		EntidadeTeste e1 = new EntidadeTeste(id, nome);
		Assertions.assertTrue(DAO.incluir(e1));
		Assertions.assertFalse(DAO.incluir(e1));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR), 1); 
	}
	@Test
	public void testDAO03() {
		excluirArquivosDiretorio();
		String id = ID_1;
		String nome = NOME_1;
		String nomeNew = NOME_2;
		EntidadeTeste e1 = new EntidadeTeste(id, nome);
		Assertions.assertTrue(DAO.incluir(e1));
		EntidadeTeste ealt = new EntidadeTeste(id, nomeNew);
		Assertions.assertTrue(DAO.alterar(ealt));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR), 1); 
		Assertions.assertTrue(new File(obterNomeArquivo(e1)).exists());
		EntidadeTeste eb = (EntidadeTeste)DAO.buscar(id);
		Assertions.assertNotNull(eb);
		Assertions.assertNotNull(eb.getDataHoraUltimaAlteracao());
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ealt, eb));		
	}		
	@Test
	public void testDAO04() {
		excluirArquivosDiretorio();
		String id = ID_1;
		String idNew = ID_2;
		String nome = NOME_1;
		String nomeNew = NOME_2;
		EntidadeTeste e1 = new EntidadeTeste(id, nome);
		Assertions.assertTrue(DAO.incluir(e1));
		EntidadeTeste ealt = new EntidadeTeste(idNew, nomeNew);
		Assertions.assertFalse(DAO.alterar(ealt));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR), 1); 
		Assertions.assertTrue(new File(obterNomeArquivo(e1)).exists());
		EntidadeTeste eb = (EntidadeTeste)DAO.buscar(id);
		Assertions.assertNotNull(eb);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(e1, eb));		
	}		
	@Test
	public void testDAO05() {
		excluirArquivosDiretorio();
		String id = ID_1;
		String nome = NOME_1;
		EntidadeTeste e1 = new EntidadeTeste(id, nome);
		Assertions.assertTrue(DAO.incluir(e1));
		EntidadeTeste eb = (EntidadeTeste)DAO.buscar(id);
		Assertions.assertNotNull(eb);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(e1, eb));		
	}
	@Test
	public void testDAO06() {
		excluirArquivosDiretorio();
		String id = ID_1;
		String nome = NOME_1;
		String idNew = ID_2;
		EntidadeTeste e1 = new EntidadeTeste(id, nome);
		Assertions.assertTrue(DAO.incluir(e1));
		EntidadeTeste eb = (EntidadeTeste)DAO.buscar(idNew);
		Assertions.assertNull(eb);				
	}
	@Test
	public void testDAO07() {
		excluirArquivosDiretorio();
		String id = ID_1;
		String nome = NOME_1;
		EntidadeTeste e1 = new EntidadeTeste(id, nome);
		Assertions.assertTrue(DAO.incluir(e1));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR), 1);
		Assertions.assertTrue(DAO.excluir(id));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR), 0);
		EntidadeTeste eb = (EntidadeTeste)DAO.buscar(id);
		Assertions.assertNull(eb);						
	}
	@Test
	public void testDAO08() {
		excluirArquivosDiretorio();
		String id = ID_1;
		String nome = NOME_1;
		String idNew = ID_2;
		EntidadeTeste e1 = new EntidadeTeste(id, nome);
		Assertions.assertTrue(DAO.incluir(e1));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR), 1);
		Assertions.assertFalse(DAO.excluir(idNew));
		Assertions.assertEquals(obterQtdArquivosDir(NOME_DIR), 1);
		EntidadeTeste eb = (EntidadeTeste)DAO.buscar(id);
		Assertions.assertNotNull(eb);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(e1, eb));		
	}
	@Test
	public void testDAO09() {
		excluirArquivosDiretorio();
		String id = ID_1;
		String nome = NOME_1;
		String idNew = ID_2;
		String nomeNew = NOME_2;
		EntidadeTeste e1 = new EntidadeTeste(id, nome);
		EntidadeTeste e2 =new EntidadeTeste(idNew, nomeNew);
		Assertions.assertTrue(DAO.incluir(e1));
		Assertions.assertTrue(DAO.incluir(e2));
		Entidade[] res = DAO.buscarTodos();
		Assertions.assertNotNull(res);
		Assertions.assertEquals(res.length, 2);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(e1, res[0]));
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(e2, res[1]));
	}
	@Test
	public void testDAO10() {
		excluirArquivosDiretorio();
		Entidade[] res = DAO.buscarTodos();
		Assertions.assertNotNull(res);
		Assertions.assertEquals(res.length, 0);
	}
}