package br.com.cesarschool.poo.titulos.telas;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorOperacao;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaOperacaoSwing extends JPanel {

    private static final long serialVersionUID = 1L;

    private MediatorOperacao mediatorOperacao;

    private JComboBox<String> cmbEntidadeCredito;
    private JComboBox<String> cmbEntidadeDebito;
    private JComboBox<String> cmbIdAcaoOuTitulo;
    private JTextField txtValor;
    private JCheckBox chkEhAcao;
    private JTextArea txtDadosOperacao;

    private TelaPrincipal telaPrincipal;
    private JTextArea txtStatus;

    public TelaOperacaoSwing(MediatorOperacao mediatorOperacao, TelaPrincipal telaPrincipal) {
        this.mediatorOperacao = mediatorOperacao;
        this.telaPrincipal = telaPrincipal;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Dados da Operação"));
        
        panel.add(new JLabel("É Ação? (clique uma para atualizar os dados)"));
        chkEhAcao = new JCheckBox();
        panel.add(chkEhAcao);

        // Inicializa as JComboBox
        cmbEntidadeCredito = new JComboBox<>();
        panel.add(new JLabel("Entidade Crédito:"));
        panel.add(cmbEntidadeCredito);

        cmbEntidadeDebito = new JComboBox<>();
        panel.add(new JLabel("Entidade Débito:"));
        panel.add(cmbEntidadeDebito);
        
        cmbIdAcaoOuTitulo = new JComboBox<>();
        panel.add(new JLabel("ID Ação ou Título:"));
        panel.add(cmbIdAcaoOuTitulo);

        panel.add(new JLabel("Valor:"));
        txtValor = new JTextField();
        panel.add(txtValor);

        add(panel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnRealizarOperacao = new JButton("Realizar Operação");
        btnRealizarOperacao.setPreferredSize(new Dimension(150, 30));
        buttonPanel.add(btnRealizarOperacao);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(btnCancelar);

        add(buttonPanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 5, 5));
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

        add(infoPanel, BorderLayout.SOUTH);

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

        chkEhAcao.addActionListener(e -> {
            atualizarCampoId();
            atualizarEntidades();
        });

        cmbEntidadeCredito.addActionListener(e -> atualizarCampoId());
        cmbEntidadeDebito.addActionListener(e -> atualizarCampoId());

        // Atualiza as JComboBox ao iniciar a tela
        atualizarEntidades();
        atualizarCampoId();
    }

    // MÉTODOS
    private void realizarOperacao() {
        try {
            int entidadeCredito = Integer.parseInt(((String) cmbEntidadeCredito.getSelectedItem()).split(" - ")[0]);
            int entidadeDebito = Integer.parseInt(((String) cmbEntidadeDebito.getSelectedItem()).split(" - ")[0]);

            int idAcaoOuTitulo = Integer.parseInt((String) cmbIdAcaoOuTitulo.getSelectedItem());
            double valor = Double.parseDouble(txtValor.getText());
            boolean ehAcao = chkEhAcao.isSelected();

            String resultado = mediatorOperacao.realizarOperacao(ehAcao, entidadeCredito, entidadeDebito, idAcaoOuTitulo, valor);

            txtStatus.setText(resultado);
            mostrarDadosOperacao(entidadeCredito, entidadeDebito, idAcaoOuTitulo, valor, ehAcao);
        } catch (NumberFormatException ex) {
            txtStatus.setText("Erro: Verifique se todos os campos estão preenchidos corretamente.");
        } catch (Exception ex) {
            txtStatus.setText("Erro ao realizar operação: " + ex.getMessage());
        }
    }

    private void atualizarEntidades() {
        List<EntidadeOperadora> entidadesOperadoras = mediatorOperacao.obterTodasEntidades();
        List<String> entidades = entidadesOperadoras.stream()
                .map(entidade -> entidade.getIdentificador() + " - " + entidade.getNome())
                .toList();

        cmbEntidadeCredito.setModel(new DefaultComboBoxModel<>(entidades.toArray(new String[0])));
        cmbEntidadeDebito.setModel(new DefaultComboBoxModel<>(entidades.toArray(new String[0])));
    }

    private void atualizarCampoId() {
        cmbIdAcaoOuTitulo.removeAllItems();

        List<String> ids = chkEhAcao.isSelected() 
                           ? mediatorOperacao.obterIdsAcoes() 
                           : mediatorOperacao.obterIdsTitulos();

        for (String id : ids) {
            cmbIdAcaoOuTitulo.addItem(id);
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
        cmbIdAcaoOuTitulo.setSelectedIndex(-1);
        cmbEntidadeCredito.setSelectedIndex(-1);
        cmbEntidadeDebito.setSelectedIndex(-1);
        txtStatus.setText("");
        txtDadosOperacao.setText("");
        chkEhAcao.setSelected(false);
    }

    private void voltarParaMenuPrincipal() {
        telaPrincipal.mostrarMenuPrincipal();
    }
}
