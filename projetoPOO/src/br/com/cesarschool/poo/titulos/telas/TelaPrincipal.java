package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.mediators.MediatorEntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;
import br.com.cesarschool.poo.titulos.mediators.MediatorOperacao;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    private MediatorEntidadeOperadora mediatorEntidadeOperadora;
    private MediatorAcao mediatorAcao;
    private MediatorOperacao mediatorOperacao;
    private MediatorTituloDivida mediatorTituloDivida;

    private JPanel panelCards; // Painel para o CardLayout

    public TelaPrincipal() {
        this.mediatorEntidadeOperadora = MediatorEntidadeOperadora.getInstance();
        this.mediatorAcao = MediatorAcao.getInstance();
        this.mediatorOperacao = MediatorOperacao.getInstance();
        this.mediatorTituloDivida = MediatorTituloDivida.getInstance();

        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelCards = new JPanel();
        panelCards.setLayout(new CardLayout());

        // Painel principal para os botões
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 1));

        JButton btnEntidade = new JButton("Gerenciar Entidades Operadoras");
        JButton btnAcao = new JButton("Gerenciar Ações");
        JButton btnOperacao = new JButton("Gerenciar Operações");
        JButton btnTituloDivida = new JButton("Gerenciar Títulos de Dívida");
        JButton btnSair = new JButton("Sair");

        panelMenu.add(btnEntidade);
        panelMenu.add(btnAcao);
        panelMenu.add(btnOperacao);
        panelMenu.add(btnTituloDivida);
        panelMenu.add(btnSair);

        // Adiciona o painel do menu ao painel de cartões
        panelCards.add(panelMenu, "Menu");

        add(panelCards);

        // BOTÕES DAS TELAS
        btnEntidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaEntidadeOperadoraSwing telaEntidade = new TelaEntidadeOperadoraSwing(mediatorEntidadeOperadora);
                panelCards.add(telaEntidade, "Entidade");
                CardLayout cl = (CardLayout)(panelCards.getLayout());
                cl.show(panelCards, "Entidade");
            }
        });

        btnAcao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaAcaoSwing telaAcao = new TelaAcaoSwing(mediatorAcao);
                panelCards.add(telaAcao, "Ação");
                CardLayout cl = (CardLayout)(panelCards.getLayout());
                cl.show(panelCards, "Ação");
            }
        });

        btnOperacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaOperacaoSwing telaOperacao = new TelaOperacaoSwing(mediatorOperacao);
                panelCards.add(telaOperacao, "Operação");
                CardLayout cl = (CardLayout)(panelCards.getLayout());
                cl.show(panelCards, "Operação");
            }
        });

        btnTituloDivida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaTituloDividaSwing telaTituloDivida = new TelaTituloDividaSwing(mediatorTituloDivida);
                panelCards.add(telaTituloDivida, "Título de Dívida");
                CardLayout cl = (CardLayout)(panelCards.getLayout());
                cl.show(panelCards, "Título de Dívida");
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
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.setVisible(true);
        });
    }
}
