package br.com.cesarschool.poo.titulos.daogenerico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DAOSerializadorObjetos {
    private static final String PONTO = ".";
    private static final String SEP_ARQUIVO = File.separator;
    private final String nomeDiretorio;

    // Construtor que define o diretório baseado na classe da entidade
    public DAOSerializadorObjetos(Class<?> tipoEntidade) {
        this.nomeDiretorio = PONTO + SEP_ARQUIVO + tipoEntidade.getSimpleName();
        File diretorio = new File(nomeDiretorio);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    // Método para incluir uma entidade
    public boolean incluir(Entidade entidade) {
        String caminhoArquivo = obterCaminhoArquivo(entidade.getIdUnico());
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            out.writeObject(entidade);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para alterar uma entidade
    public boolean alterar(Entidade entidade) {
        String caminhoArquivo = obterCaminhoArquivo(entidade.getIdUnico());
        File arquivo = new File(caminhoArquivo);
        if (arquivo.exists()) {
            return incluir(entidade); // Sobrescreve o arquivo existente
        }
        return false;
    }

    // Método para excluir uma entidade pelo ID único
    public boolean excluir(String idUnico) {
        String caminhoArquivo = obterCaminhoArquivo(idUnico);
        File arquivo = new File(caminhoArquivo);
        return arquivo.delete();
    }

    // Método para buscar uma entidade pelo ID único
    public Entidade buscar(String idUnico) {
        String caminhoArquivo = obterCaminhoArquivo(idUnico);
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            return (Entidade) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para buscar todas as entidades
    public Entidade[] buscarTodos() {
        List<Entidade> lista = new ArrayList<>();
        File diretorio = new File(nomeDiretorio);
        File[] arquivos = diretorio.listFiles((dir, name) -> name.endsWith(".ser"));

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
                    lista.add((Entidade) in.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return lista.toArray(new Entidade[0]);
    }

    // Método auxiliar para obter o caminho completo do arquivo
    private String obterCaminhoArquivo(String idUnico) {
        return nomeDiretorio + SEP_ARQUIVO + idUnico + ".ser";
    }
}


