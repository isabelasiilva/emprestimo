package com.projeto.atividade2.service;

import com.projeto.atividade2.model.Tarefa;
import com.projeto.atividade2.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {
    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<Tarefa> listarTarefas(){
        return tarefaRepository.findAll();
    }

    public Tarefa cadastraTarefas(Tarefa tarefa){
        return tarefaRepository.save(tarefa);
    }
}
