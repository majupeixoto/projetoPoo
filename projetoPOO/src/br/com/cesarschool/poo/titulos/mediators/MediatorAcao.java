package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioAcao;

public class MediatorAcao {
    private static MediatorAcao instanciaUnica;
    private final RepositorioAcao repositorioAcao = new RepositorioAcao();
    
    private MediatorAcao() {}
    
    public static MediatorAcao getInstance() {
        if(instanciaUnica == null) {
            instanciaUnica = new MediatorAcao();
        }
        return instanciaUnica;
    }
    
    private String validar(Acao acao) {
        if (acao.getIdentificador() < 1 || acao.getIdentificador() > 99999) {
            return "Identificador deve estar entre 1 e 99999.";
        }

        if (acao.getNome() == null || acao.getNome().trim().isEmpty()) {
            return "Nome deve ser preenchido.";
        }

        if (acao.getNome().length() < 10 || acao.getNome().length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }

        if (acao.getDataDeValidade().isBefore(LocalDate.now().plusDays(30))) {
            return "Data de validade deve ter pelo menos 30 dias na frente da data atual.";
        }

        if (acao.getValorUnitario() <= 0) {
            return "Valor unitário deve ser maior que zero.";
        }

        return null;
    }
    
    public String incluir(Acao acao) {
        String mensagemValidar = validar(acao);
        
        if (mensagemValidar != null) {
            return mensagemValidar;
        }
        
        boolean conferir = repositorioAcao.incluir(acao);
        
        if (conferir) {
            return null;
        } else {
            return "Ação já existente";
        }
    }
    
    public String alterar(Acao acao) {
        String mensagemValidar = validar(acao);
        
        if (mensagemValidar != null) {
            return mensagemValidar;
        }
        
        boolean conferir = repositorioAcao.alterar(acao);
        
        if (conferir) {
            return null;
        } else {
            return "Ação inexistente";
        }
    }
    
    public String excluir(int identificador) {
        if (identificador < 1 || identificador > 99999) {
            return "Identificador deve estar entre 1 e 99999.";
        }
        
        boolean conferir = repositorioAcao.excluir(identificador);

        if (conferir) {
            return null;
        } else {
            return "Ação inexistente";
        }
    }
    
    public Acao buscar(int identificador) {
        if (identificador < 1 || identificador > 99999) {
            return null;
        }
        return repositorioAcao.buscar(identificador);
    }

    public List<String> obterIdsAcoes() {
        List<Acao> acoes = repositorioAcao.listar();
        return acoes.stream()
                .map(acao -> String.valueOf(acao.getIdentificador()))
                .collect(Collectors.toList());
    }
}
