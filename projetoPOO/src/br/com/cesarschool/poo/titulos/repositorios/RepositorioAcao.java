package br.com.cesarschool.poo.titulos.repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.cesarschool.poo.titulos.entidades.Acao;
/*
 * Deve gravar em e ler de um arquivo texto chamado Acao.txt os dados dos objetos do tipo
 * Acao. Seguem abaixo exemplos de linhas (identificador, nome, dataValidade, valorUnitario) OK
 * 
    1;PETROBRAS;2024-12-12;30.33
    2;BANCO DO BRASIL;2026-01-01;21.21
    3;CORREIOS;2027-11-11;6.12 
 * 
 * A inclusão deve adicionar uma nova linha ao arquivo. Não é permitido incluir 
 * identificador repetido. Neste caso, o método deve retornar false. Inclusão com 
 * sucesso, retorno true. OK
 * 
 * A alteração deve substituir a linha atual por uma nova linha. A linha deve ser 
 * localizada por identificador que, quando não encontrado, enseja retorno false. 
 * Alteração com sucesso, retorno true.   OK
 *   
 * A exclusão deve apagar a linha atual do arquivo. A linha deve ser 
 * localizada por identificador que, quando não encontrado, enseja retorno false. 
 * Exclusão com sucesso, retorno true. OK
 * 
 * A busca deve localizar uma linha por identificador, materializar e retornar um 
 * objeto. Caso o identificador não seja encontrado no arquivo, retornar null.   
 */
public class RepositorioAcao {
	public boolean incluir(Acao acao) {		
		if(idExiste(acao.getIdentificador())) {
			 return false;
		 }
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("Acao.txt", true))){
			String linha = acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataDeValidade() + ";" + acao.getValorUnitario();
			writer.write(linha);
			writer.newLine();
			return true;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public boolean alterar(Acao acao) {
	    List<String> linhas = new ArrayList<>();
	    boolean encontrado = false;

	    try (BufferedReader reader = new BufferedReader(new FileReader("Acao.txt"))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] frases = line.split(";");
	            int idPresente = Integer.parseInt(frases[0]);

	            if (idPresente == acao.getIdentificador()) {
	                linhas.add(acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataDeValidade() + ";" + acao.getValorUnitario());
	                encontrado = true;
	            } else {
	                linhas.add(line);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }

	    if (!encontrado) {
	        return false;
	    }

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Acao.txt"))) {
	        for (String linha : linhas) {
	            writer.write(linha);
	            writer.newLine();
	        }
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}	
	
	public boolean excluir(int identificador) {
		List<String> linhas = new ArrayList<>();
	    boolean encontrado = false;
	    
	    try (BufferedReader reader = new BufferedReader(new FileReader("Acao.txt"))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] frases = line.split(";");
	            int idPresente = Integer.parseInt(frases[0]);

	            if (idPresente != identificador) {
	                linhas.add(line);
	            } else {
	                encontrado = true;
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	    
	    if (!encontrado) {
            return false;
        }

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Acao.txt"))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	    
	}
	
	
	public Acao buscar(int identificador) {
		
		try(BufferedReader reader = new BufferedReader(new FileReader("Acao.txt"))){
			String line;
            while ((line = reader.readLine()) != null) {
                String[] frases = line.split(";");
                int idPresente = Integer.parseInt(frases[0]);

                if (idPresente == identificador) {
                    String nome = frases[1];
                    LocalDate dataValidade = LocalDate.parse(frases[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    double valorUnitario = Double.parseDouble(frases[3]);
                    return new Acao(identificador, nome, dataValidade, valorUnitario);
                }
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean idExiste(int identificador) {
	    try (BufferedReader reader = new BufferedReader(new FileReader("Acao.txt"))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] frases = line.split(";");
	            int idPresente = Integer.parseInt(frases[0]);

	            if (idPresente == identificador) {
	                return true;
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

}