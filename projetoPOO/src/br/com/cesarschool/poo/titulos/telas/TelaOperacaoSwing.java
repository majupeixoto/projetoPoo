package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorOperacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class TelaOperacaoSwing extends JPanel {

    private static final long serialVersionUID = 1L;

    private MediatorOperacao mediatorOperacao;

    private JComboBox<String> cmbEntidadeCredito;
    private JComboBox<String> cmbEntidadeDebito;
    private JTextField txtIdAcaoOuTitulo;
    private JTextField txtValor;
    private JCheckBox chkEhAcao;
    private JTextArea txtDadosOperacao;
    
    private TelaPrincipal telaPrincipal; // Referência para a TelaPrincipal
    private JTextArea txtStatus;

    public TelaOperacaoSwing(MediatorOperacao mediatorOperacao, TelaPrincipal telaPrincipal) {
        this.mediatorOperacao = mediatorOperacao;
        this.telaPrincipal = telaPrincipal;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Dados da Operação"));

        // Obtém a lista de entidades e converte para uma lista de Strings com os nomes das entidades
        List<EntidadeOperadora> entidadesOperadoras = mediatorOperacao.obterTodasEntidades();
        List<String> entidades = entidadesOperadoras.stream()
                .map(entidade -> entidade.getIdentificador() + " - " + entidade.getNome()) // Formato "ID - Nome"
                .toList();

        // Diminuindo o tamanho das JComboBox
        cmbEntidadeCredito = new JComboBox<>(entidades.toArray(new String[0]));
        cmbEntidadeDebito = new JComboBox<>(entidades.toArray(new String[0]));
        cmbEntidadeCredito.setPreferredSize(new Dimension(200, 25));
        cmbEntidadeDebito.setPreferredSize(new Dimension(200, 25));

        panel.add(new JLabel("Entidade Crédito:"));
        panel.add(cmbEntidadeCredito);

        panel.add(new JLabel("Entidade Débito:"));
        panel.add(cmbEntidadeDebito);

        panel.add(new JLabel("ID Ação ou Título:"));
        txtIdAcaoOuTitulo = new JTextField();
        panel.add(txtIdAcaoOuTitulo);

        panel.add(new JLabel("Valor:"));
        txtValor = new JTextField();
        panel.add(txtValor);

        panel.add(new JLabel("É Ação?"));
        chkEhAcao = new JCheckBox();
        panel.add(chkEhAcao);
        
        add(panel, BorderLayout.NORTH);

        // Criando um painel para os botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Usando FlowLayout para alinhar os botões
        JButton btnRealizarOperacao = new JButton("Realizar Operação");
        btnRealizarOperacao.setPreferredSize(new Dimension(150, 30)); // Diminuindo o tamanho do botão
        buttonPanel.add(btnRealizarOperacao);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 30)); // Diminuindo o tamanho do botão
        buttonPanel.add(btnCancelar);

        add(buttonPanel, BorderLayout.CENTER); // Adiciona o painel de botões ao centro

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 5, 5)); // Espaçamento interno
        txtStatus = new JTextArea();
        txtStatus.setEditable(false);
        txtStatus.setLineWrap(true);
        txtStatus.setWrapStyleWord(true);
        txtStatus.setBorder(BorderFactory.createTitledBorder("Status"));
        infoPanel.add(txtStatus);

        txtDadosOperacao = new JTextArea();
        txtDadosOperacao.setEditable(false);
        txtDadosOperacao.setLineWrap(true);
        txtDadosOperacao.setWrapStyleWord(true);
        txtDadosOperacao.setBorder(BorderFactory.createTitledBorder("Dados da Operação:"));
        infoPanel.add(txtDadosOperacao);

        add(infoPanel, BorderLayout.SOUTH); // Adiciona o painel de informações ao fundo

        btnRealizarOperacao.addActionListener(e -> realizarOperacao());

        btnCancelar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(TelaOperacaoSwing.this,
                    "Deseja realmente cancelar essa operação?",
                    "Confirmação de Cancelamento",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                limparCampos();
                voltarParaMenuPrincipal();
            }
        });
    }

    // MÉTODOS
    private void realizarOperacao() {
        try {
            // Extrai o ID da entidade selecionada antes do primeiro hífen
            int entidadeCredito = Integer.parseInt(((String) cmbEntidadeCredito.getSelectedItem()).split(" - ")[0]);
            int entidadeDebito = Integer.parseInt(((String) cmbEntidadeDebito.getSelectedItem()).split(" - ")[0]);

            int idAcaoOuTitulo = Integer.parseInt(txtIdAcaoOuTitulo.getText());
            double valor = Double.parseDouble(txtValor.getText());
            boolean ehAcao = chkEhAcao.isSelected();

            // Chama o método de realização de operação no MediatorOperacao
            String resultado = mediatorOperacao.realizarOperacao(ehAcao, entidadeCredito, entidadeDebito, idAcaoOuTitulo, valor);

            txtStatus.setText(resultado);
            mostrarDadosOperacao(entidadeCredito, entidadeDebito, idAcaoOuTitulo, valor, ehAcao);
        } catch (NumberFormatException ex) {
            txtStatus.setText("Erro: Verifique se todos os campos estão preenchidos corretamente.");
        } catch (Exception ex) {
            txtStatus.setText("Erro ao realizar operação: " + ex.getMessage());
        }
    }
    
    private void mostrarDadosOperacao(int entidadeCredito, int entidadeDebito, int idAcaoOuTitulo, double valor, boolean ehAcao) {
        txtDadosOperacao.setText("Operação: " + (ehAcao ? "Ação" : "Título") +
                                  "\nEntidade Crédito: " + entidadeCredito +
                                  "\nEntidade Débito: " + entidadeDebito +
                                  "\nID: " + idAcaoOuTitulo +
                                  "\nValor: " + valor);
    }

    private void limparCampos() {
        txtValor.setText("");
        txtIdAcaoOuTitulo.setText("");
        txtStatus.setText("");
        txtDadosOperacao.setText(""); // Limpa os dados da operação
        chkEhAcao.setSelected(false); // Limpa a checkbox
    }

    private void voltarParaMenuPrincipal() {
        telaPrincipal.mostrarMenuPrincipal();
    }
}
