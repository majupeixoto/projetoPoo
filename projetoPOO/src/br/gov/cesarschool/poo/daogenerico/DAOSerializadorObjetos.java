package br.gov.cesarschool.poo.daogenerico;

import java.io.*;
import java.time.LocalDateTime;
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
        File arquivo = new File(nomeDiretorio + SEP_ARQUIVO + entidade.getIdUnico());
        if (arquivo.exists()) {
            return false; // Arquivo já existe
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            entidade.setDataHoraInclusao(LocalDateTime.now());
            oos.writeObject(entidade);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para alterar uma entidade existente
    public boolean alterar(Entidade entidade) {
        File arquivo = new File(nomeDiretorio + SEP_ARQUIVO + entidade.getIdUnico());
        if (!arquivo.exists()) {
            return false; // Arquivo não existe
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            entidade.setDataHoraUltimaAlteracao(LocalDateTime.now());
            oos.writeObject(entidade);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para excluir uma entidade pelo ID único
    public boolean excluir(String idUnico) {
        File arquivo = new File(nomeDiretorio + SEP_ARQUIVO + idUnico);
        return arquivo.exists() && arquivo.delete();
    }

    // Método para buscar uma entidade pelo ID único
    public Entidade buscar(String idUnico) {
        File arquivo = new File(nomeDiretorio + SEP_ARQUIVO + idUnico);
        if (!arquivo.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Entidade) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para buscar todas as entidades no diretório
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
                e.printStackTrace();
            }
        }

        return lista.toArray(new Entidade[0]);
    }
}
