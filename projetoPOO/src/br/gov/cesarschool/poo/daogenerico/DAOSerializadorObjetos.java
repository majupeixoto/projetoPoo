package br.gov.cesarschool.poo.daogenerico;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DAOSerializadorObjetos {
    private static final String PONTO = ".";
    private static final String SEP_ARQUIVO = File.separator;
    private final String nomeDiretorio;

    public DAOSerializadorObjetos(Class<?> tipoEntidade) {
        this.nomeDiretorio = PONTO + SEP_ARQUIVO + tipoEntidade.getSimpleName();
        File diretorio = new File(nomeDiretorio);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
    }

    public boolean incluir(Entidade entidade) {
        if (entidade == null) {
            return false;
        }

        File arquivo = new File(nomeDiretorio + SEP_ARQUIVO + entidade.getIdUnico());

        if (arquivo.exists()) {
            return false;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            entidade.setDataHoraInclusao(LocalDateTime.now());
            oos.writeObject(entidade);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao incluir a entidade: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Entidade entidade) {
        if (entidade == null) {
            return false;
        }

        File arquivo = new File(nomeDiretorio + SEP_ARQUIVO + entidade.getIdUnico());
        if (!arquivo.exists()) {
            return false;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            entidade.setDataHoraUltimaAlteracao(LocalDateTime.now());
            oos.writeObject(entidade);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao alterar a entidade: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(String idUnico) {
        if (idUnico == null || idUnico.isEmpty()) {
            return false;
        }

        File arquivo = new File(nomeDiretorio + SEP_ARQUIVO + idUnico);
        return arquivo.exists() && arquivo.delete();
    }

    public Entidade buscar(String idUnico) {
        if (idUnico == null || idUnico.isEmpty()) {
            return null;
        }

        File arquivo = new File(nomeDiretorio + SEP_ARQUIVO + idUnico);
        if (!arquivo.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Entidade) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao buscar a entidade: " + e.getMessage());
            return null;
        }
    }

    public Entidade[] buscarTodos() {
        File diretorio = new File(nomeDiretorio);
        File[] arquivos = diretorio.listFiles();

        if (arquivos == null || arquivos.length == 0) {
            return new Entidade[0];
        }

        List<Entidade> lista = new ArrayList<>();
        for (File arquivo : arquivos) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                lista.add((Entidade) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao ler arquivo " + arquivo.getName() + ": " + e.getMessage());
            }
        }

        return lista.toArray(new Entidade[0]);
    }
}
