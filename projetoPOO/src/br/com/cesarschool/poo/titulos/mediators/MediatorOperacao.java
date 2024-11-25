package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class MediatorOperacao {
    private static MediatorOperacao instanciaUnica;
    private final MediatorAcao mediatorAcao = MediatorAcao.getInstance();
    private final MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getInstance();
    private final MediatorEntidadeOperadora mediatorEntidadeOperadora = MediatorEntidadeOperadora.getInstance();
    private final RepositorioTransacao repositorioTransacao = new RepositorioTransacao();

    private MediatorOperacao() {}

    public static synchronized MediatorOperacao getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new MediatorOperacao();
        }
        return instanciaUnica;
    }

    public String realizarOperacao(boolean ehAcao, int entidadeCredito, int idEntidadeDebito, int idAcaoOuTitulo, double valor) {
        if (valor <= 0) {
            return "Valor inválido";
        }

        EntidadeOperadora credora = mediatorEntidadeOperadora.buscar(entidadeCredito);
        if (credora == null) {
            return "Entidade crédito inexistente";
        }

        EntidadeOperadora devedora = mediatorEntidadeOperadora.buscar(idEntidadeDebito);
        if (devedora == null) {
            return "Entidade débito inexistente";
        }

        if (ehAcao && !credora.getAutorizadoAcao()) {
            return "Entidade de crédito não autorizada para ação";
        }

        if (ehAcao && !devedora.getAutorizadoAcao()) {
            return "Entidade de débito não autorizada para ação";
        }

        return processarOperacao(ehAcao, credora, devedora, idAcaoOuTitulo, valor);
    }

    private String processarOperacao(boolean ehAcao, EntidadeOperadora credora, EntidadeOperadora devedora, int idAcaoOuTitulo, double valor) {
        if (ehAcao) {
            Acao acao = mediatorAcao.buscar(idAcaoOuTitulo);
            if (acao == null) {
                return "Ação inexistente";
            }
            if (devedora.getSaldoAcao() < valor) {
                return "Saldo da entidade débito insuficiente";
            }
            if (acao.getValorUnitario() > valor) {
                return "Valor da operação é menor do que o valor unitário da ação";
            }
            return registrarTransacao(credora, devedora, acao, null, valor);
        } else {
            TituloDivida titulo = mediatorTituloDivida.buscar(idAcaoOuTitulo);
            if (titulo == null) {
                return "Título inexistente";
            }
            if (devedora.getSaldoTituloDivida() < valor) {
                return "Saldo da entidade débito insuficiente";
            }
            double valorOperacao = titulo.calcularPrecoTransacao(valor);
            return registrarTransacao(credora, devedora, null, titulo, valorOperacao);
        }
    }

    private String registrarTransacao(EntidadeOperadora credora, EntidadeOperadora devedora, Acao acao, TituloDivida titulo, double valorOperacao) {
        // Creditar e debitar saldos
        if (acao != null) {
            credora.creditarSaldoAcao(valorOperacao);
            devedora.debitarSaldoAcao(valorOperacao);
        } else {
            credora.creditarSaldoTituloDivida(valorOperacao);
            devedora.debitarSaldoTituloDivida(valorOperacao);
        }

        // Alterar entidades no mediator e verificar erros
        String erroCredora = mediatorEntidadeOperadora.alterar(credora);
        if (erroCredora != null) {
            return erroCredora;
        }

        String erroDevedora = mediatorEntidadeOperadora.alterar(devedora);
        if (erroDevedora != null) {
            return erroDevedora;
        }

        // Criar e registrar a transação
        Transacao transacao = new Transacao(credora, devedora, acao, titulo, valorOperacao, LocalDateTime.now());
        repositorioTransacao.incluir(transacao);

        return "Operação realizada com sucesso";
    }

    public Transacao[] gerarExtrato(int entidade) {
        Transacao[] transacoesCredoras = repositorioTransacao.buscarPorEntidadeCredora(entidade);
        Transacao[] transacoesDevedoras = repositorioTransacao.buscarPorEntidadeDevedora(entidade); // Ajustado para buscar devedoras

        return combineAndSort(transacoesCredoras, transacoesDevedoras);
    }

    private Transacao[] combineAndSort(Transacao[] credoras, Transacao[] devedoras) {
        Transacao[] result = new Transacao[credoras.length + devedoras.length];
        System.arraycopy(credoras, 0, result, 0, credoras.length);
        System.arraycopy(devedoras, 0, result, credoras.length, devedoras.length);

        Arrays.sort(result, (a, b) -> b.getDataHoraOperacao().compareTo(a.getDataHoraOperacao()));
        return result;
    }

    public List<String> obterIdsTitulos() {
        return mediatorTituloDivida.obterIdsTitulos();
    }

}
