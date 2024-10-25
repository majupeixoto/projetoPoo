package br.com.cesarschool.poo.titulos.repositorios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;

public class RepositorioTransacao {

    private static final String FILE_CAMINHO = "src/br/com/cesarschool/poo/titulos/repositorios/transacao.txt";
    private static final String NULL_VALUE = "null";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void incluir(Transacao transacao) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CAMINHO, true))) {
            String linha = formatarTransacaoParaLinha(transacao);
            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Transacao[] buscarPorEntidadeDevedora(long identificadorEntidadeDevedora) {
        List<Transacao> transacoesEncontradas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Transacao transacao = linhaParaTransacao(linha);
                if (transacao != null && transacao.getEntidadeDebito().getIdentificador() == identificadorEntidadeDevedora) {
                    transacoesEncontradas.add(transacao);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transacoesEncontradas.toArray(new Transacao[0]);
    }

    public Transacao[] buscarPorEntidadeCredora(long identificadorEntidadeCredito) {
        List<Transacao> transacoesEncontradas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CAMINHO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Transacao transacao = linhaParaTransacao(linha);
                if (transacao != null && transacao.getEntidadeCredito().getIdentificador() == identificadorEntidadeCredito) {
                    transacoesEncontradas.add(transacao);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transacoesEncontradas.toArray(new Transacao[0]);
    }

    private Transacao linhaParaTransacao(String linha) {
        String[] dados = linha.split(";");
        
        EntidadeOperadora entidadeCredito = parseEntidade(dados, 0);
        EntidadeOperadora entidadeDebito = parseEntidade(dados, 5);

        Acao acao = parseAcao(dados, 10);
        TituloDivida tituloDivida = parseTituloDivida(dados, 14);

        double valorOperacao = Double.parseDouble(dados[18]);
        LocalDateTime dataHoraOperacao = LocalDateTime.parse(dados[19].replace(" ", "T"), DATE_TIME_FORMATTER);

        return new Transacao(entidadeCredito, entidadeDebito, acao, tituloDivida, valorOperacao, dataHoraOperacao);
    }

    private EntidadeOperadora parseEntidade(String[] dados, int startIndex) {
        long id = Long.parseLong(dados[startIndex]);
        String nome = dados[startIndex + 1];
        boolean autorizado = Boolean.parseBoolean(dados[startIndex + 2]);
        
        EntidadeOperadora entidadeOperadora = new EntidadeOperadora(
        		id,
        		nome,
        		autorizado
        );
        entidadeOperadora.creditarSaldoAcao(Double.parseDouble(dados[startIndex + 3]));
        entidadeOperadora.creditarSaldoTituloDivida(Double.parseDouble(dados[startIndex + 4]));
        return entidadeOperadora;
    }

    private Acao parseAcao(String[] dados, int startIndex) {
        if (NULL_VALUE.equals(dados[startIndex])) {
            return null;
        }
        int id = Integer.parseInt(dados[startIndex]);
        String nome = dados[startIndex + 1];
        LocalDate dataValidade = LocalDate.parse(dados[startIndex + 2], DATE_FORMATTER);
        double valorUnitario = Double.parseDouble(dados[startIndex + 3]);
        return new Acao(id, nome, dataValidade, valorUnitario);
    }

    private TituloDivida parseTituloDivida(String[] dados, int startIndex) {
        if (NULL_VALUE.equals(dados[startIndex])) {
            return null;
        }
        int id = Integer.parseInt(dados[startIndex]);
        String nome = dados[startIndex + 1];
        LocalDate dataValidade = LocalDate.parse(dados[startIndex + 2], DATE_FORMATTER);
        double taxaJuros = Double.parseDouble(dados[startIndex + 3]);
        return new TituloDivida(id, nome, dataValidade, taxaJuros);
    }

    private String formatarEntidadeParaLinha(EntidadeOperadora entidade) {
        return entidade.getIdentificador() + ";" +
               entidade.getNome() + ";" +
               entidade.getAutorizadoAcao() + ";" +
               entidade.getSaldoAcao() + ";" +
               entidade.getSaldoTituloDivida() + ";";
    }

    private String formatarAcaoParaLinha(Acao acao) {
        if (acao != null) {
            return acao.getIdentificador() + ";" +
                   acao.getNome() + ";" +
                   DATE_FORMATTER.format(acao.getDataDeValidade()) + ";" +
                   acao.getValorUnitario() + ";";
        }
        return NULL_VALUE + ";";
    }

    private String formatarTituloDividaParaLinha(TituloDivida titulo) {
        if (titulo != null) {
            return titulo.getIdentificador() + ";" +
                   titulo.getNome() + ";" +
                   DATE_FORMATTER.format(titulo.getDataDeValidade()) + ";" +
                   titulo.getTaxaJuros() + ";";
        }
        return NULL_VALUE + ";";
    }

    private String formatarTransacaoParaLinha(Transacao transacao) {
        return formatarEntidadeParaLinha(transacao.getEntidadeCredito()) +
               formatarEntidadeParaLinha(transacao.getEntidadeDebito()) +
               formatarAcaoParaLinha(transacao.getAcao()) +
               formatarTituloDividaParaLinha(transacao.getTituloDivida()) +
               transacao.getValorOperacao() + ";" +
               DATE_TIME_FORMATTER.format(transacao.getDataHoraOperacao()).replace("T", " ");
    }
}
