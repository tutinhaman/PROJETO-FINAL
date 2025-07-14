package com.taskmanager.view;

import com.taskmanager.model.Tarefa;
import com.taskmanager.service.GerenciadorTarefas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FormularioExcluirTarefa extends JDialog {
    private GerenciadorTarefas gerenciadorTarefas;
    private JComboBox<TarefaItem> tarefaComboBox;
    private JTextArea tarefaDetailsArea;
    private Tarefa selectedTarefa;

    public FormularioExcluirTarefa(Frame parent, GerenciadorTarefas gerenciadorTarefas) {
        super(parent, "Excluir Tarefa", true);
        this.gerenciadorTarefas = gerenciadorTarefas;
        initializeUI();
        carregarTarefas();
    }

    private void initializeUI() {
        setSize(450, 350);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("Excluir Tarefa");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.RED);
        mainPanel.add(titleLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Selecionar Tarefa:"), gbc); gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        tarefaComboBox = new JComboBox<>();
        tarefaComboBox.addActionListener(e -> carregarDetalhesTarefaSelecionada());
        mainPanel.add(tarefaComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Detalhes da Tarefa:"), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        tarefaDetailsArea = new JTextArea(8, 30);
        tarefaDetailsArea.setEditable(false);
        tarefaDetailsArea.setBackground(getBackground());
        tarefaDetailsArea.setBorder(BorderFactory.createLoweredBevelBorder());
        tarefaDetailsArea.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(tarefaDetailsArea);
        mainPanel.add(scrollPane, gbc);

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton deleteButton = new JButton("Excluir Tarefa");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(new DeleteActionListener());
        
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void carregarTarefas() {
        tarefaComboBox.removeAllItems();
        List<Tarefa> tarefas = gerenciadorTarefas.obterTodasAsTarefas();
        
        if (tarefas.isEmpty()) {
            tarefaComboBox.addItem(new TarefaItem(null, "Nenhuma tarefa disponível"));
            tarefaDetailsArea.setText("Nenhuma tarefa disponível para exclusão.");
        } else {
            tarefaComboBox.addItem(new TarefaItem(null, "Selecione uma tarefa..."));
            for (Tarefa tarefa : tarefas) {
                tarefaComboBox.addItem(new TarefaItem(tarefa, tarefa.getTitle()));
            }
            tarefaDetailsArea.setText("Selecione uma tarefa para ver os detalhes.");
        }
    }

    private void carregarDetalhesTarefaSelecionada() {
        TarefaItem selectedItem = (TarefaItem) tarefaComboBox.getSelectedItem();
        if (selectedItem != null && selectedItem.getTarefa() != null) {
            selectedTarefa = selectedItem.getTarefa();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            StringBuilder details = new StringBuilder();
            details.append("ID: ").append(selectedTarefa.getId()).append("\n\n");
            details.append("Título: ").append(selectedTarefa.getTitle()).append("\n\n");
            details.append("Descrição: ").append(selectedTarefa.getDescription()).append("\n\n");
            details.append("Data de Vencimento: ").append(selectedTarefa.getDueDate().format(formatter)).append("\n\n");
            details.append("Status: ").append(selectedTarefa.getStatus()).append("\n\n");
            details.append("ATENÇÃO: Esta ação não pode ser desfeita!");
            
            tarefaDetailsArea.setText(details.toString());
        } else {
            selectedTarefa = null;
            tarefaDetailsArea.setText("Selecione uma tarefa para ver os detalhes.");
        }
    }

    private class DeleteActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedTarefa == null) {
                JOptionPane.showMessageDialog(FormularioExcluirTarefa.this, 
                                            "Selecione uma tarefa para excluir!", 
                                            "Erro", 
                                            JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmation = JOptionPane.showConfirmDialog(
                FormularioExcluirTarefa.this,
                "Tem certeza que deseja excluir a tarefa \'" + selectedTarefa.getTitle() + "\'\n" +
                "Esta ação não pode ser desfeita!",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                gerenciadorTarefas.excluirTarefa(selectedTarefa.getId());
                
                JOptionPane.showMessageDialog(FormularioExcluirTarefa.this, 
                                            "Tarefa excluída com sucesso!", 
                                            "Sucesso", 
                                            JOptionPane.INFORMATION_MESSAGE);
                
                carregarTarefas();
            }
        }
    }

    private static class TarefaItem {
        private Tarefa tarefa;
        private String displayText;

        public TarefaItem(Tarefa tarefa, String displayText) {
            this.tarefa = tarefa;
            this.displayText = displayText;
        }

        public Tarefa getTarefa() {
            return tarefa;
        }

        @Override
        public String toString() {
            return displayText;
        }
    }
}

