
package com.taskmanager.service;

import com.taskmanager.model.Tarefa;
import com.taskmanager.util.ManipuladorJson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GerenciadorTarefas {
    private List<Tarefa> tarefas;
    private final String jsonFilePath;

    public GerenciadorTarefas(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
        try {
            List<Tarefa> loadedTarefas = ManipuladorJson.loadFromJson(jsonFilePath);
            this.tarefas = new ArrayList<>(loadedTarefas); 
        } catch (IOException e) {
            System.err.println("Erro ao carregar tarefas do JSON: " + e.getMessage());
            this.tarefas = new ArrayList<>();
        }
    }

    public void adicionarTarefa(Tarefa tarefa) {
        this.tarefas.add(tarefa);
        salvarTarefasNoJson();
    }

    public Optional<Tarefa> obterTarefa(String id) {
        return this.tarefas.stream()
                .filter(tarefa -> tarefa.getId().equals(id))
                .findFirst();
    }

    public List<Tarefa> obterTodasAsTarefas() {
        return new ArrayList<>(this.tarefas);
    }

    public void atualizarTarefa(Tarefa tarefaAtualizada) {
        this.tarefas = this.tarefas.stream()
                .map(tarefa -> tarefa.getId().equals(tarefaAtualizada.getId()) ? tarefaAtualizada : tarefa)
                .collect(Collectors.toList());
        salvarTarefasNoJson();
    }

    public void excluirTarefa(String id) {
        this.tarefas.removeIf(tarefa -> tarefa.getId().equals(id));
        salvarTarefasNoJson();
    }

    private void salvarTarefasNoJson() {
        try {
            ManipuladorJson.saveToJson(this.tarefas, jsonFilePath);
        } catch (IOException e) {
            System.err.println("Erro ao salvar tarefas no JSON: " + e.getMessage());
        }
    }
}

