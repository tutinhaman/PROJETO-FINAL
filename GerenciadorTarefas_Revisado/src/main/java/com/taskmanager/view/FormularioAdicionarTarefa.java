package com.taskmanager.view;

import com.taskmanager.model.StatusTarefa;
import com.taskmanager.model.Tarefa;
import com.taskmanager.service.GerenciadorTarefas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormularioAdicionarTarefa extends JDialog {
    private GerenciadorTarefas gerenciadorTarefas;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField dueDateField;
    private JComboBox<StatusTarefa> statusComboBox;

    public FormularioAdicionarTarefa(Frame parent, GerenciadorTarefas gerenciadorTarefas) {
        super(parent, "Adicionar Tarefa", true);
        this.gerenciadorTarefas = gerenciadorTarefas;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 350);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Título:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        titleField = new JTextField(20);
        mainPanel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        mainPanel.add(scrollPane, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        mainPanel.add(new JLabel("Data de Vencimento (dd/MM/yyyy):"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        dueDateField = new JTextField(20);
        mainPanel.add(dueDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        statusComboBox = new JComboBox<>(StatusTarefa.values());
        mainPanel.add(statusComboBox, gbc);

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(new SaveActionListener());
        
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class SaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (validateFields()) {
                try {
                    String title = titleField.getText().trim();
                    String description = descriptionArea.getText().trim();
                    LocalDate dueDate = LocalDate.parse(dueDateField.getText().trim(), 
                                                       DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    StatusTarefa status = (StatusTarefa) statusComboBox.getSelectedItem();

                    Tarefa tarefa = new Tarefa(title, description, dueDate, status);
                    gerenciadorTarefas.adicionarTarefa(tarefa);

                    JOptionPane.showMessageDialog(FormularioAdicionarTarefa.this, 
                                                "Tarefa adicionada com sucesso!", 
                                                "Sucesso", 
                                                JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(FormularioAdicionarTarefa.this, 
                                                "Formato de data inválido. Use dd/MM/yyyy", 
                                                "Erro", 
                                                JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private boolean validateFields() {
        if (titleField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                        "O título é obrigatório!", 
                                        "Erro de Validação", 
                                        JOptionPane.ERROR_MESSAGE);
            titleField.requestFocus();
            return false;
        }

        if (descriptionArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                        "A descrição é obrigatória!", 
                                        "Erro de Validação", 
                                        JOptionPane.ERROR_MESSAGE);
            descriptionArea.requestFocus();
            return false;
        }

        if (dueDateField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                                        "A data de vencimento é obrigatória!", 
                                        "Erro de Validação", 
                                        JOptionPane.ERROR_MESSAGE);
            dueDateField.requestFocus();
            return false;
        }

        try {
            LocalDate.parse(dueDateField.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, 
                                        "Formato de data inválido. Use dd/MM/yyyy", 
                                        "Erro de Validação", 
                                        JOptionPane.ERROR_MESSAGE);
            dueDateField.requestFocus();
            return false;
        }

        return true;
    }
}

