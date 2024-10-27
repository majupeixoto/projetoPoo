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
    private JTextField txtIdAcaoOuTitulo;
    private JTextField txtValor;
    private JCheckBox chkEhAcao;

    private JTextArea txtStatus;

    public TelaOperacaoSwing(MediatorOperacao mediatorOperacao) {
        this.mediatorOperacao = mediatorOperacao;

        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(7, 2));

     // Obtém a lista de entidades e converte para uma lista de Strings com os nomes das entidades
        List<EntidadeOperadora> entidadesOperadoras = mediatorOperacao.obterTodasEntidades();
        List<String> entidades = entidadesOperadoras.stream()
                .map(entidade -> entidade.getIdentificador() + " - " + entidade.getNome()) // Formato "ID - Nome"
                .toList();

        cmbEntidadeCredito = new JComboBox<>(entidades.toArray(new String[0]));
        cmbEntidadeDebito = new JComboBox<>(entidades.toArray(new String[0]));


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

        JButton btnRealizarOperacao = new JButton("Realizar Operação");
        panel.add(btnRealizarOperacao);

        txtStatus = new JTextArea();
        txtStatus.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtStatus);
        panel.add(new JLabel("Status:"));
        panel.add(scrollPane);

        add(panel, BorderLayout.CENTER);

        btnRealizarOperacao.addActionListener(e -> realizarOperacao());
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
        } catch (NumberFormatException ex) {
            txtStatus.setText("Erro: Verifique se todos os campos estão preenchidos corretamente.");
        } catch (Exception ex) {
            txtStatus.setText("Erro ao realizar operação: " + ex.getMessage());
        }
    }
}
