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
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

public class RepositorioAcao extends RepositorioGeral{
	private static final String FILE_CAMINHO = "src/br/com/cesarschool/poo/titulos/repositorios/Acao.txt";

	public boolean incluir(Acao acao) {		
		if(idExiste(acao.getIdentificador())) {
			 return false;
		 }

		try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CAMINHO, true))){
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

	    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))) {
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

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CAMINHO))) {
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

	    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))) {
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

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CAMINHO))) {
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

		try(BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))){
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
	    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))) {
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
	
	public List<Acao> listar() {
	    List<Acao> listaAcoes = new ArrayList<>();
	    
	    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] frases = line.split(";");
	            int identificador = Integer.parseInt(frases[0]);
	            String nome = frases[1];
	            LocalDate dataValidade = LocalDate.parse(frases[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	            double valorUnitario = Double.parseDouble(frases[3]);
	            
	            Acao acao = new Acao(identificador, nome, dataValidade, valorUnitario);
	            listaAcoes.add(acao);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return listaAcoes;
	}

}