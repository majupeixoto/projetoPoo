package br.gov.cesarschool.poo.testes;

import java.io.File;
import br.gov.cesarschool.poo.daogenerico.Entidade;
import br.gov.cesarschool.poo.testes.TestesDAOSerializador.EntidadeTeste;

public class TesteGeral {
	protected static final String SEP_ARQUIVO = System.getProperty("file.separator");
	protected static final String PONTO = ".";
	protected static final String NOME_DIR = PONTO + SEP_ARQUIVO + EntidadeTeste.class.getSimpleName();  	
	protected void excluirArquivosDiretorio() {
		excluirArquivosDiretorio(NOME_DIR); 	
	}
	protected void excluirArquivosDiretorio(String diretorio) {
		File dir = new File(diretorio);
		File[] arqs = dir.listFiles();
		if (arqs != null && arqs.length > 0) {
			for (File file : arqs) {
				file.delete();
			}
		}		
	}
	protected int obterQtdArquivosDir(String caminhoDir) {
		File[] files = (new File(caminhoDir)).listFiles();
		if (files == null) {
			return 0;
		} else {
			return files.length;
		}
	}
	protected String obterNomeArquivo(Entidade ent) {
		return obterNomeArquivo(NOME_DIR, ent);
	}
	protected String obterNomeArquivo(String dir, Entidade ent) {
		return dir + SEP_ARQUIVO + ent.getIdUnico();
	}

}