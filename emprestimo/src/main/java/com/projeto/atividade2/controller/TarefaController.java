package com.projeto.atividade2.controller;

import com.projeto.atividade2.model.Tarefa;
import com.projeto.atividade2.service.TarefaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {
    private TarefaService tarefaService;

    public TarefaController(TarefaService tarefa) {
        this.tarefaService = tarefa;
    }

    @GetMapping
    public List<Tarefa> listar() {
        return tarefaService.listarTarefas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tarefa salvar(@RequestBody Tarefa tarefa) {
        return tarefaService.cadastraTarefas(tarefa);
    }
}
