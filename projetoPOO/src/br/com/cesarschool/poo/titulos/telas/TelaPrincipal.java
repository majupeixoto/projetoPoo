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

    private CardLayout cardLayout;
    private JPanel panelCards;
    private JPanel menuPanel;

    public TelaPrincipal() {
        this.mediatorEntidadeOperadora = MediatorEntidadeOperadora.getInstance();
        this.mediatorAcao = MediatorAcao.getInstance();
        this.mediatorOperacao = MediatorOperacao.getInstance();
        this.mediatorTituloDivida = MediatorTituloDivida.getInstance();

        setTitle("Menu Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuração do CardLayout para alternar entre menu e telas
        cardLayout = new CardLayout();
        panelCards = new JPanel(cardLayout);

        // Painel de Menu Centralizado
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 1, 10, 10));

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

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(menuPanel, BorderLayout.WEST); // Menu na lateral esquerda

        // Adicionando telas ao CardLayout (panelCards)
        panelCards.add(new JPanel(), "Menu");  // Tela inicial
        panelCards.add(new TelaEntidadeOperadoraSwing(mediatorEntidadeOperadora, this), "Entidade");
        panelCards.add(new TelaAcaoSwing(mediatorAcao, this), "Ação");
        panelCards.add(new TelaOperacaoSwing(mediatorOperacao, this), "Operação");
        panelCards.add(new TelaTituloDividaSwing(mediatorTituloDivida, this), "Título de Dívida");
        
        getContentPane().add(panelCards, BorderLayout.CENTER);

        // Ações dos botões para alternar entre as telas
        btnEntidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMenuVisibility(false);
                cardLayout.show(panelCards, "Entidade");
            }
        });

        btnAcao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMenuVisibility(false);
                cardLayout.show(panelCards, "Ação");
            }
        });

        btnOperacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMenuVisibility(false);
                cardLayout.show(panelCards, "Operação");
            }
        });

        btnTituloDivida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMenuVisibility(false);
                cardLayout.show(panelCards, "Título de Dívida");
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(TelaPrincipal.this,
                        "Deseja realmente sair?",
                        "Confirmação de Saída",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();  // Fecha a aplicação se o usuário confirmar
                }
            }
        });
    }

    // Método para mostrar ou esconder o menu
    private void toggleMenuVisibility(boolean visible) {
        menuPanel.setVisible(visible);
    }

    public void mostrarMenuPrincipal() {
        toggleMenuVisibility(true);
        cardLayout.show(panelCards, "Menu"); // Volta para a tela de menu
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.setVisible(true);
        });
    }
}
