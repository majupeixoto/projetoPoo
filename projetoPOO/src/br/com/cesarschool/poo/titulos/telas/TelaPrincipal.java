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
    private CardLayout cardLayout;

    public TelaPrincipal() {
        this.mediatorEntidadeOperadora = MediatorEntidadeOperadora.getInstance();
        this.mediatorAcao = MediatorAcao.getInstance();
        this.mediatorOperacao = MediatorOperacao.getInstance();
        this.mediatorTituloDivida = MediatorTituloDivida.getInstance();

        setTitle("Menu Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel para o CardLayout
        cardLayout = new CardLayout();
        panelCards = new JPanel(cardLayout);

        // Adicionando o menu à esquerda com JSplitPane
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 1));

        JButton btnEntidade = new JButton("Gerenciar Entidades Operadoras");
        JButton btnAcao = new JButton("Gerenciar Ações");
        JButton btnOperacao = new JButton("Gerenciar Operações");
        JButton btnTituloDivida = new JButton("Gerenciar Títulos de Dívida");
        JButton btnSair = new JButton("Sair");

        menuPanel.add(btnEntidade);
        menuPanel.add(btnAcao);
        menuPanel.add(btnOperacao);
        menuPanel.add(btnTituloDivida);
        menuPanel.add(btnSair);

        // Adicionando as telas ao CardLayout
        panelCards.add(new JPanel(), "Menu");  // Tela inicial
        panelCards.add(new TelaEntidadeOperadoraSwing(mediatorEntidadeOperadora), "Entidade");
        panelCards.add(new TelaAcaoSwing(mediatorAcao), "Ação");
        panelCards.add(new TelaOperacaoSwing(mediatorOperacao), "Operação");
        panelCards.add(new TelaTituloDividaSwing(mediatorTituloDivida), "Título de Dívida");

        // Dividindo a janela em menu à esquerda e conteúdo à direita
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuPanel, panelCards);
        splitPane.setDividerLocation(200);  // Define a largura do painel de menu

        // Adicionando o JSplitPane à janela principal
        getContentPane().add(splitPane);

        // Ações dos botões para alternar entre as telas
        btnEntidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelCards, "Entidade");
            }
        });

        btnAcao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelCards, "Ação");
            }
        });

        btnOperacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelCards, "Operação");
            }
        });

        btnTituloDivida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelCards, "Título de Dívida");
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Fecha a aplicação
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
