package br.gov.cesarschool.poo.testes;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.relatorios.RelatorioTransacaoBroker;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;
import br.com.cesarschool.poo.titulos.utils.Comparador;
import br.com.cesarschool.poo.titulos.utils.ComparadorPadrao;
import br.com.cesarschool.poo.titulos.utils.ComparadorTransacaoPorNomeCredora;
import br.com.cesarschool.poo.titulos.utils.Comparavel;
import br.com.cesarschool.poo.titulos.utils.Ordenador;
import br.gov.cesarschool.poo.daogenerico.Entidade;

public class TesteRelatorios extends TesteGeral {
	private static final String NOME_DIR_TRANSACAO = PONTO + SEP_ARQUIVO + Transacao.class.getSimpleName();
	@Test
	public void t000() {
		ComparadorTransacaoPorNomeCredora c = new ComparadorTransacaoPorNomeCredora();
		Assertions.assertTrue(c instanceof Comparador);
		Assertions.assertTrue(c instanceof ComparadorPadrao); 
	}
	@Test
	public void t001() {
		LocalDateTime dh1 = LocalDateTime.now();
		LocalDateTime dh2 = LocalDateTime.now().plusDays(2);
		Comparavel c1 = new Transacao(null, null, null, null, 0, dh1);
		Comparavel c2 = new Transacao(null, null, null, null, 0, dh2);
		Assertions.assertTrue(c1.comparar(c2) > 0);
		Assertions.assertTrue(c2.comparar(c1) < 0);
	}
	@Test
	public void t002() {
		EntidadeOperadora e1 = new EntidadeOperadora(1, "ABC", 100);
		EntidadeOperadora e2 = new EntidadeOperadora(2, "CDE", 100);
		Transacao t1 = new Transacao(e1, null, null, null, 0, null);
		Transacao t2 = new Transacao(e2, null, null, null, 0, null);
		Comparador c = new ComparadorTransacaoPorNomeCredora();
		Assertions.assertTrue(c.comparar(t1, t2) < 0);
		Assertions.assertTrue(c.comparar(t2, t1) > 0);
	}
	@Test
	public void t003() {		
		EntidadeModelo[] ents = new EntidadeModelo[3];
				ents[0] = new EntidadeModelo(1, "ZDE");
				ents[1] = new EntidadeModelo(2, "ABC");
				ents[2] = new EntidadeModelo(3, "CDE");
			Ordenador.ordenar(ents);
			Assertions.assertEquals(ents[0].getNome(), "ABC");
			Assertions.assertEquals(ents[1].getNome(), "CDE");
			Assertions.assertEquals(ents[2].getNome(), "ZDE");
	}
	@Test
	public void t004() {		
		EntidadeModelo[] ents = new EntidadeModelo[3];
				ents[0] = new EntidadeModelo(2, "ZDE");
				ents[1] = new EntidadeModelo(1, "ABC");
				ents[2] = new EntidadeModelo(3, "CDE");
			Ordenador.ordenar(ents, new ComparadorEntidadeModeloIdUnico());
			Assertions.assertEquals(ents[0].getIdUnico(), "1");
			Assertions.assertEquals(ents[1].getIdUnico(), "2");
			Assertions.assertEquals(ents[2].getIdUnico(), "3");
	}
	@Test
	public void t005() {		
		EntidadeModelo[] ents = new EntidadeModelo[3];
				ents[0] = new EntidadeModelo(2, "ZDE");
				ents[1] = new EntidadeModelo(1, "ABC");
				ents[2] = new EntidadeModelo(3, "CDE");
			Ordenador.ordenar(ents, new ComparadorEntidadeModeloIdUnico());
			Assertions.assertEquals(ents[0].getIdUnico(), "1");
			Assertions.assertEquals(ents[1].getIdUnico(), "2");
			Assertions.assertEquals(ents[2].getIdUnico(), "3");
	}
	
	@Test
	public void t006() {
		excluirArquivosDiretorio(NOME_DIR_TRANSACAO);
		RepositorioTransacao rep = new RepositorioTransacao();		
		RelatorioTransacaoBroker broker = new RelatorioTransacaoBroker();
		Acao a = new Acao(1, "PB01", LocalDate.now(), 100);
		EntidadeOperadora e1 = new EntidadeOperadora(1, "XXX", 100);
		EntidadeOperadora e2 = new EntidadeOperadora(2, "ZZZ", 100);
		EntidadeOperadora e3 = new EntidadeOperadora(3, "AAA", 100);
		Transacao t1 = new Transacao(e1, e2, a, null, 0, LocalDateTime.now());
		Transacao t2 = new Transacao(e2, e1, a, null, 0, LocalDateTime.now().plusDays(2));
		Transacao t3 = new Transacao(e3, e1, a, null, 0, LocalDateTime.now().plusDays(1));
		rep.incluir(t1);
		rep.incluir(t2);
		rep.incluir(t3);				
		Transacao[] trans = broker.relatorioTransacaoPorNomeEntidadeCredora();
		Assertions.assertEquals(trans[0].getEntidadeCredito().getNome(), "AAA");
		Assertions.assertEquals(trans[1].getEntidadeCredito().getNome(), "XXX");
		Assertions.assertEquals(trans[2].getEntidadeCredito().getNome(), "ZZZ");		
	}
	@Test
	public void t007() {
		excluirArquivosDiretorio(NOME_DIR_TRANSACAO);
		RepositorioTransacao rep = new RepositorioTransacao();		
		RelatorioTransacaoBroker broker = new RelatorioTransacaoBroker();
		Acao a = new Acao(1, "PB02", LocalDate.now(), 200);
		EntidadeOperadora e1 = new EntidadeOperadora(1, "AAA", 100);
		EntidadeOperadora e2 = new EntidadeOperadora(2, "BBB", 100);
		EntidadeOperadora e3 = new EntidadeOperadora(3, "CCC", 100);
		EntidadeOperadora e4 = new EntidadeOperadora(4, "DDD", 100);
		Transacao t1 = new Transacao(e4, e1, a, null, 0, LocalDateTime.now().plusDays(3));
		Transacao t2 = new Transacao(e1, e2, a, null, 0, LocalDateTime.now());
		Transacao t3 = new Transacao(e3, e1, a, null, 0, LocalDateTime.now().plusDays(2));
		Transacao t4 = new Transacao(e2, e1, a, null, 0, LocalDateTime.now().plusDays(1));
		rep.incluir(t1);
		rep.incluir(t2);
		rep.incluir(t3);
		rep.incluir(t4);						
		Transacao[] trans = broker.relatorioTransacaoPorDataHora();
		Assertions.assertEquals(trans[0].getEntidadeCredito().getNome(), "DDD");
		Assertions.assertEquals(trans[1].getEntidadeCredito().getNome(), "CCC");
		Assertions.assertEquals(trans[2].getEntidadeCredito().getNome(), "BBB");		
		Assertions.assertEquals(trans[3].getEntidadeCredito().getNome(), "AAA");
	}
	
	static class ComparadorEntidadeModeloIdUnico implements Comparador {		
		public int comparar(Comparavel c1, Comparavel c2) {
			Entidade e1 = (Entidade)c1;
			Entidade e2 = (Entidade)c2;
			return e1.getIdUnico().compareTo(e2.getIdUnico());
		}		
	}
	
}