package br.com.cesarschool.poo.titulos.repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;

/*
 * Deve gravar em e ler de um arquivo texto chamado Acao.txt os dados dos objetos do tipo
 * Acao. Seguem abaixo exemplos de linhas.
 *
    1;PETROBRAS;2024-12-12;30.33
    2;BANCO DO BRASIL;2026-01-01;21.21
    3;CORREIOS;2027-11-11;6.12 
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
public class RepositorioEntidadeOperadora {
	
	private static final String FILE_CAMINHO = "src/br/com/cesarschool/poo/titulos/repositorios/EntidadeOperadora.txt";

	public boolean incluir(EntidadeOperadora entidadeOperadora) {
        if (idDuplicado(entidadeOperadora.getIdentificador())) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CAMINHO, true))) {
            String linha = entidadeOperadora.getIdentificador() + ";" + entidadeOperadora.getNome() + ";" + entidadeOperadora.getAutorizadoAcao();
            writer.write(linha);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(EntidadeOperadora entidadeOperadora) {
        List<String> linhas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");
                long idPresente = Long.parseLong(dados[0]);

                if (idPresente == entidadeOperadora.getIdentificador()) {
                    linhas.add(entidadeOperadora.getIdentificador() + ";" + entidadeOperadora.getNome() + ";" + entidadeOperadora.getAutorizadoAcao());
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

    public boolean excluir(long identificador) {
        List<String> linhas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");
                long idPresente = Long.parseLong(dados[0]);

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

    public EntidadeOperadora buscar(long identificador) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");
                long idPresente = Long.parseLong(dados[0]);

                if (idPresente == identificador) {
                    String nome = dados[1];
                    boolean autorizadoAcao = Boolean.parseBoolean(dados[2]);
                    return new EntidadeOperadora(idPresente, nome, autorizadoAcao, 0.0, 0.0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean idDuplicado(long identificador) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");
                long idPresente = Long.parseLong(dados[0]);

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