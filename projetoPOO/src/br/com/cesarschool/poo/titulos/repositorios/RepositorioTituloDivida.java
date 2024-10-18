package br.com.cesarschool.poo.titulos.repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

/*
 * Deve gravar em e ler de um arquivo texto chamado TituloDivida.txt os dados dos objetos do tipo
 * TituloDivida. Seguem abaixo exemplos de linhas (identificador, nome, dataValidade, taxaJuros).
 *
    1;BRASIL;2024-12-12;10.5
    2;EUA;2026-01-01;1.5
    3;FRANCA;2027-11-11;2.5 
 * 
 * A inclusão deve adicionar uma nova linha ao arquivo. Não é permitido incluir 
 * identificador repetido. Neste caso, o método deve retornar false. Inclusão com 
 * sucesso, retorno true.
 * 
 * A alteração deve substituir a linha atual por uma nova linha. A linha deve ser 
 * localizada por identificador que, quando não encontrado, enseja retorno false. 
 * Alteração com sucesso, retorno true.  
 *   
 * A exclusão deve apagar a linha atual do arquivo. A linha deve ser 
 * localizada por identificador que, quando não encontrado, enseja retorno false. 
 * Exclusão com sucesso, retorno true.
 * 
 * A busca deve localizar uma linha por identificador, materializar e retornar um 
 * objeto. Caso o identificador não seja encontrado no arquivo, retornar null.   
 */
public class RepositorioTituloDivida {
	public boolean incluir(TituloDivida tituloDivida) {
		if(idDuplicado(tituloDivida.getIdentificador())) {
			 return false;
		 }

		try(BufferedWriter writer = new BufferedWriter(new FileWriter("TituloDivida.txt", true))){
			String linha = tituloDivida.getIdentificador() + ";" + tituloDivida.getNome() + ";" + tituloDivida.getDataDeValidade() + ";" + tituloDivida.getTaxaJuros();
			writer.write(linha);
			writer.newLine();
			return true;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	public boolean alterar(TituloDivida tituloDivida) {
		List<String> linhas = new ArrayList<>();
	    boolean encontrado = false;

	    try (BufferedReader reader = new BufferedReader(new FileReader("TituloDivida.txt"))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] frases = line.split(";");
	            int idPresente = Integer.parseInt(frases[0]);

	            if (idPresente == tituloDivida.getIdentificador()) {
	                linhas.add(tituloDivida.getIdentificador() + ";" + tituloDivida.getNome() + ";" + tituloDivida.getDataDeValidade() + ";" + tituloDivida.getTaxaJuros());
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

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("TituloDivida.txt"))) {
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
	    
	    try (BufferedReader reader = new BufferedReader(new FileReader("TituloDivida.txt"))) {
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

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("TituloDivida.txt"))) {
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
		try(BufferedReader reader = new BufferedReader(new FileReader("TituloDivida.txt"))){
			String line;
            while ((line = reader.readLine()) != null) {
                String[] frases = line.split(";");
                int idPresente = Integer.parseInt(frases[0]);

                if (idPresente == identificador) {
                    String nome = frases[1];
                    LocalDate dataValidade = LocalDate.parse(frases[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    double taxaJuros = Double.parseDouble(frases[3]);
                    return new Acao(identificador, nome, dataValidade, taxaJuros);
                }
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean idDuplicado(int identificador) {
	    try (BufferedReader reader = new BufferedReader(new FileReader("TituloDivida.txt"))) {
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