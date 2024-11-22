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
    private JCheckBox chkAcao;
    private JCheckBox chkTitulo;
    private JTextField txtValor;
    private JTextArea txtDadosOperacao;

    private TelaPrincipal telaPrincipal;
    private JTextArea txtStatus;

    public TelaOperacaoSwing(MediatorOperacao mediatorOperacao, TelaPrincipal telaPrincipal) {
        this.mediatorOperacao = mediatorOperacao;
        this.telaPrincipal = telaPrincipal;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Dados da Operação"));

        chkAcao = new JCheckBox("Ação");
        chkTitulo = new JCheckBox("Título");

        panel.add(new JLabel("Tipo de Operação:"));
        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel.add(chkAcao);
        checkboxPanel.add(chkTitulo);
        panel.add(checkboxPanel);

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

        chkAcao.addActionListener(e -> {
            if (chkAcao.isSelected()) {
                chkTitulo.setSelected(false);
                atualizarEntidades(true);
                atualizarCampoId(true);
            } else {
                limparCampos();
            }
        });

        chkTitulo.addActionListener(e -> {
            if (chkTitulo.isSelected()) {
                chkAcao.setSelected(false);
                atualizarEntidades(false);
                atualizarCampoId(false);
            } else {
                limparCampos(); // Limpa os campos se nenhuma opção for selecionada
            }
        });

        limparCampos(); // Garante que os campos estejam vazios até que uma opção seja selecionada
    }

    private void realizarOperacao() {
        try {
            int entidadeCredito = Integer.parseInt(((String) cmbEntidadeCredito.getSelectedItem()).split(" - ")[0]);
            int entidadeDebito = Integer.parseInt(((String) cmbEntidadeDebito.getSelectedItem()).split(" - ")[0]);

            int idAcaoOuTitulo = Integer.parseInt((String) cmbIdAcaoOuTitulo.getSelectedItem());
            double valor = Double.parseDouble(txtValor.getText());
            boolean ehAcao = chkAcao.isSelected();

            String resultado = mediatorOperacao.realizarOperacao(ehAcao, entidadeCredito, entidadeDebito, idAcaoOuTitulo, valor);

            txtStatus.setText(resultado);
            mostrarDadosOperacao(entidadeCredito, entidadeDebito, idAcaoOuTitulo, valor, ehAcao);
        } catch (NumberFormatException ex) {
            txtStatus.setText("Erro: Verifique se todos os campos estão preenchidos corretamente.");
        } catch (Exception ex) {
            txtStatus.setText("Erro ao realizar operação: " + ex.getMessage());
        }
    }

    private void atualizarEntidades(boolean ehAcao) {
        List<EntidadeOperadora> todasEntidades = mediatorOperacao.obterTodasEntidades();
        List<String> entidadesFiltradas = todasEntidades.stream()
                .filter(entidade -> ehAcao ? entidade.getAutorizadoAcao() : true)
                .map(entidade -> entidade.getIdentificador() + " - " + entidade.getNome())
                .toList();

        cmbEntidadeCredito.setModel(new DefaultComboBoxModel<>(entidadesFiltradas.toArray(new String[0])));
        cmbEntidadeDebito.setModel(new DefaultComboBoxModel<>(entidadesFiltradas.toArray(new String[0])));
    }

    private void atualizarCampoId(boolean ehAcao) {
        cmbIdAcaoOuTitulo.removeAllItems();

        List<String> ids = ehAcao ? mediatorOperacao.obterIdsAcoes() : mediatorOperacao.obterIdsTitulos();
        ids.forEach(cmbIdAcaoOuTitulo::addItem);
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
        cmbIdAcaoOuTitulo.removeAllItems();
        cmbEntidadeCredito.removeAllItems();
        cmbEntidadeDebito.removeAllItems();
        txtStatus.setText("");
        txtDadosOperacao.setText("");
        chkAcao.setSelected(false);
        chkTitulo.setSelected(false);
    }

    private void voltarParaMenuPrincipal() {
        telaPrincipal.mostrarMenuPrincipal();
    }
}
