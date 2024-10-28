package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTituloDivida;

public class MediatorTituloDivida {
    private static MediatorTituloDivida instanciaUnica;
    private final RepositorioTituloDivida repositorioTituloDivida = new RepositorioTituloDivida();
    
    private MediatorTituloDivida() {}
    
    public static MediatorTituloDivida getInstance() {
        if(instanciaUnica == null) {
            instanciaUnica = new MediatorTituloDivida();
        }
        return instanciaUnica;
    }
    
    private String validar(TituloDivida tituloDivida) {
        if(tituloDivida.getIdentificador() < 1 || tituloDivida.getIdentificador() > 99999) {
            return "Identificador deve estar entre 1 e 99999.";
        }

        if(tituloDivida.getNome() == null || tituloDivida.getNome().trim().isEmpty()) {
            return "Nome deve ser preenchido.";
        }

        if(tituloDivida.getNome().length() < 10 || tituloDivida.getNome().length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }

        if(tituloDivida.getDataDeValidade().isBefore(LocalDate.now().plusDays(180))) {
            return "Data de validade deve ter pelo menos 180 dias na frente da data atual.";
        }

        if(tituloDivida.getTaxaJuros() < 0) {
            return "Taxa de juros deve ser maior ou igual a zero.";
        }

        return null;
    }
    
    public String incluir(TituloDivida tituloDivida) {
        String mensagemValidar = validar(tituloDivida);
        if (mensagemValidar != null) {
            return mensagemValidar;
        }
        
        boolean conferir = repositorioTituloDivida.incluir(tituloDivida);
        if (conferir) {
            return null;
        } else {
            return "Título já existente";
        }
    }
    
    public String alterar(TituloDivida tituloDivida) {
        String mensagemValidar = validar(tituloDivida);
        if (mensagemValidar != null) {
            return mensagemValidar;
        }
        
        boolean conferir = repositorioTituloDivida.alterar(tituloDivida);
        if (conferir) {
            return null;
        } else {
            return "Título inexistente";
        }
    }
    
    public String excluir(int identificador) {
        if (identificador < 1 || identificador > 99999) {
            return "Identificador deve estar entre 1 e 99999.";
        }
        
        boolean conferir = repositorioTituloDivida.excluir(identificador);
        if (conferir) {
            return null;
        } else {
            return "Título inexistente";
        }
    }
    
    public TituloDivida buscar(int identificador) {
        if (identificador < 1 || identificador > 99999) {
            return null;
        }
        return repositorioTituloDivida.buscar(identificador);
    }

    // Método para obter IDs dos títulos de dívida
    public List<String> obterIdsTitulos() {
        // Supondo que o repositório possui um método para listar todos os títulos
        List<TituloDivida> titulos = repositorioTituloDivida.listar(); // Método que você deve implementar no RepositorioTituloDivida
        return titulos.stream()
                .map(titulo -> String.valueOf(titulo.getIdentificador())) // Convertendo o ID para String
                .collect(Collectors.toList());
    }
}
