package com.taskmanager.view;

import com.taskmanager.model.Tarefa;
import com.taskmanager.service.GerenciadorTarefas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PainelVisualizarTarefas extends JDialog {
    private GerenciadorTarefas gerenciadorTarefas;
    private JTable tarefasTable;
    private DefaultTableModel tableModel;

    public PainelVisualizarTarefas(Frame parent, GerenciadorTarefas gerenciadorTarefas) {
        super(parent, "Visualizar Tarefas", true);
        this.gerenciadorTarefas = gerenciadorTarefas;
        initializeUI();
        carregarTarefas();
    }

    private void initializeUI() {
        setSize(800, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Lista de Tarefas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Título", "Descrição", "Data de Vencimento", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tarefasTable = new JTable(tableModel);
        tarefasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tarefasTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        tarefasTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        tarefasTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        tarefasTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        tarefasTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(tarefasTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshButton = new JButton("Atualizar");
        refreshButton.addActionListener(e -> carregarTarefas());
        
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void carregarTarefas() {
        tableModel.setRowCount(0);
        
        List<Tarefa> tarefas = gerenciadorTarefas.obterTodasAsTarefas();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Tarefa tarefa : tarefas) {
            Object[] rowData = {
                tarefa.getId(),
                tarefa.getTitle(),
                tarefa.getDescription(),
                tarefa.getDueDate().format(formatter),
                tarefa.getStatus()
            };
            tableModel.addRow(rowData);
        }
        
        if (tarefas.isEmpty()) {
            JLabel noTasksLabel = new JLabel("Nenhuma tarefa encontrada.", SwingConstants.CENTER);
            noTasksLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        }
    }
}

