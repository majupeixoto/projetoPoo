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

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

public class RepositorioTituloDivida {
	private static final String FILE_CAMINHO = "src/br/com/cesarschool/poo/titulos/repositorios/TituloDivida.txt";

	public boolean incluir(TituloDivida tituloDivida) {
	    if (idDuplicado(tituloDivida.getIdentificador())) {
	        return false;
	    }

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CAMINHO, true))) {
	        String linha = tituloDivida.getIdentificador() + ";" + tituloDivida.getNome() + ";" 
	                     + tituloDivida.getDataDeValidade() + ";" + tituloDivida.getTaxaJuros();
	        writer.write(linha);
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	

	public boolean alterar(TituloDivida tituloDivida) {
		List<String> linhas = new ArrayList<>();
	    boolean encontrado = false;

	    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))) {
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
	public TituloDivida buscar(int identificador) {
		try(BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))){
			String line;
            while ((line = reader.readLine()) != null) {
                String[] frases = line.split(";");
                int idPresente = Integer.parseInt(frases[0]);

                if (idPresente == identificador) {
                    String nome = frases[1];
                    LocalDate dataValidade = LocalDate.parse(frases[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    double taxaJuros = Double.parseDouble(frases[3]);
                    return new TituloDivida(identificador, nome, dataValidade, taxaJuros);
                }
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean idDuplicado(int identificador) {
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
	
	public List<TituloDivida> listar() {
	    List<TituloDivida> listaTitulos = new ArrayList<>();
	    
	    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] frases = line.split(";");
	            int identificador = Integer.parseInt(frases[0]);
	            String nome = frases[1];
	            LocalDate dataValidade = LocalDate.parse(frases[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	            double taxaJuros = Double.parseDouble(frases[3]);
	            
	            TituloDivida titulo = new TituloDivida(identificador, nome, dataValidade, taxaJuros);
	            listaTitulos.add(titulo);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return listaTitulos;
	}


}