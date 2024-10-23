package br.com.cesarschool.poo.titulos.repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;

public class RepositorioEntidadeOperadora {
    
    private static final String FILE_CAMINHO = "src/br/com/cesarschool/poo/titulos/repositorios/EntidadeOperadora.txt";

    public boolean incluir(EntidadeOperadora entidadeOperadora) {
        if (idDuplicado(entidadeOperadora.getIdentificador())) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CAMINHO, true))) {
            String linha = entidadeOperadora.getIdentificador() + ";" +
                           entidadeOperadora.getNome() + ";" +
                           entidadeOperadora.getAutorizadoAcao() + ";" +
                           entidadeOperadora.getSaldoAcao() + ";" +
                           entidadeOperadora.getSaldoTituloDivida();
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
                    linhas.add(entidadeOperadora.getIdentificador() + ";" +
                               entidadeOperadora.getNome() + ";" +
                               entidadeOperadora.getAutorizadoAcao() + ";" +
                               entidadeOperadora.getSaldoAcao() + ";" +
                               entidadeOperadora.getSaldoTituloDivida());
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
                    double saldoAcao = Double.parseDouble(dados[3]);
                    double saldoTituloDivida = Double.parseDouble(dados[4]);
                    return new EntidadeOperadora(idPresente, nome, autorizadoAcao, saldoAcao, saldoTituloDivida);
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
