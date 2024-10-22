package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.mediators.MediatorEntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;
import br.com.cesarschool.poo.titulos.mediators.MediatorOperacao;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

import javax.swing.JFrame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TelaPrincipal extends JFrame{
	
	private static final long serialVersionUID = 1L;  // Adiciona serialVersionUID

	private MediatorEntidadeOperadora mediatorEntidadeOperadora;
    private MediatorAcao mediatorAcao;
    private MediatorOperacao mediatorOperacao;
    private MediatorTituloDivida mediatorTituloDivida;
    
    public TelaPrincipal() {
    	this.mediatorEntidadeOperadora = MediatorEntidadeOperadora.getInstance();
        this.mediatorAcao = MediatorAcao.getInstance();
        this.mediatorOperacao = MediatorOperacao.getInstance();
        this.mediatorTituloDivida = MediatorTituloDivida.getInstance();
        
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        
        JButton btnEntidade = new JButton("Gerenciar Entidades Operadoras");
        JButton btnAcao = new JButton("Gerenciar Ações");
        JButton btnOperacao = new JButton("Gerenciar Operações");
        JButton btnTituloDivida = new JButton("Gerenciar Títulos de Dívida");
        JButton btnSair = new JButton("Sair");
        
        panel.add(btnEntidade);
        panel.add(btnAcao);
        panel.add(btnOperacao);
        panel.add(btnTituloDivida);
        panel.add(btnSair);
        
        add(panel);
        
        // BOTÕES DAS TELAS
        btnEntidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaEntidadeOperadoraSwing telaEntidade = new TelaEntidadeOperadoraSwing(mediatorEntidadeOperadora);
                telaEntidade.setVisible(true);
            }
        });

        btnAcao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaAcaoSwing telaAcao = new TelaAcaoSwing(mediatorAcao);
                telaAcao.setVisible(true);
            }
        });

        btnOperacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaOperacaoSwing telaOperacao = new TelaOperacaoSwing(mediatorOperacao);
                telaOperacao.setVisible(true);
            }
        });

        btnTituloDivida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaTituloDividaSwing telaTituloDivida = new TelaTituloDividaSwing(mediatorTituloDivida);
                telaTituloDivida.setVisible(true);
            }
        });

        // Botão Sair para fechar o programa
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela principal
            }
        });
    }
    
    public static void main(String[] args) {
        TelaPrincipal telaPrincipal = new TelaPrincipal();
        telaPrincipal.setVisible(true);
    }
}
