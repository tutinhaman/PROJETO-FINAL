package com.taskmanager.view;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import com.taskmanager.service.GerenciadorTarefas;

public class TelaPrincipal extends JFrame {
    private GerenciadorTarefas gerenciadorTarefas;

    public TelaPrincipal() {
        this.gerenciadorTarefas = new GerenciadorTarefas("resources/tasks.json");   initializeUI();
    }

    private void initializeUI() {
        setTitle("Gerenciador de Tarefas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        
        JMenu taskMenu = new JMenu("Tarefas");
        JMenuItem addTaskItem = new JMenuItem("Adicionar Tarefa");
        
        addTaskItem.addActionListener(e -> abrirFormularioAdicionarTarefa());
        
        JMenuItem viewTasksItem = new JMenuItem("Visualizar Tarefas");
        viewTasksItem.addActionListener(e -> abrirPainelVisualizarTarefas());
        
        JMenuItem editTaskItem = new JMenuItem("Editar Tarefa");
        editTaskItem.addActionListener(e -> abrirFormularioEditarTarefa());
        
        JMenuItem deleteTaskItem = new JMenuItem("Excluir Tarefa");
        deleteTaskItem.addActionListener(e -> abrirFormularioExcluirTarefa());
        
        taskMenu.add(addTaskItem);
        taskMenu.add(viewTasksItem);
        taskMenu.add(editTaskItem);
        taskMenu.add(deleteTaskItem);
        
        menuBar.add(taskMenu);
        setJMenuBar(menuBar);

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Gerenciador de Tarefas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));
        
        JButton addButton = new JButton("Adicionar Tarefa");
        addButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addButton.addActionListener(e -> abrirFormularioAdicionarTarefa());
        
        JButton viewButton = new JButton("Visualizar Tarefas");
        viewButton.setFont(new Font("Arial", Font.PLAIN, 16));
        viewButton.addActionListener(e -> abrirPainelVisualizarTarefas());
        
        JButton editButton = new JButton("Editar Tarefa");
        editButton.setFont(new Font("Arial", Font.PLAIN, 16));
        editButton.addActionListener(e -> abrirFormularioEditarTarefa());
        
        JButton deleteButton = new JButton("Excluir Tarefa");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 16));
        deleteButton.addActionListener(e -> abrirFormularioExcluirTarefa());
        
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }

    private void abrirFormularioAdicionarTarefa() {
        new FormularioAdicionarTarefa(this, gerenciadorTarefas).setVisible(true);
    }

    private void abrirPainelVisualizarTarefas() {
        new PainelVisualizarTarefas(this, gerenciadorTarefas).setVisible(true);
    }

    private void abrirFormularioEditarTarefa() {
        new FormularioEditarTarefa(this, gerenciadorTarefas).setVisible(true);
    }

    private void abrirFormularioExcluirTarefa() {
        new FormularioExcluirTarefa(this, gerenciadorTarefas).setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}

