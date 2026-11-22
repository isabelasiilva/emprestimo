package com.projeto.emprestimo.controller;


import com.projeto.emprestimo.model.Equipamento;
import com.projeto.emprestimo.model.TipoEquipamento;
import com.projeto.emprestimo.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {
    private final EquipamentoService service;
    public EquipamentoController(EquipamentoService service) {
        this.service = service; // fazendo uma injeção de dependencia, declara uma variavel e passa no construtor
    }

    // faz uma requisição para listar todos os equipamentos, mas você pode passar um filtro não obrigatorio (por causa do required false), por ex "tipo",
    @GetMapping
    public List<Equipamento> listar(
            @RequestParam(required = false)
            TipoEquipamento tipo) {
        return service.listar(tipo);
    }

    @GetMapping
    public List<Equipamento> listar(
            @RequestParam(required = false)
            TipoEquipamento tipo,
            @RequestParam(required = false)
            Boolean disponivel) {
        return service.listar(tipo, disponivel);
    }

    @GetMapping("/{id}")
    public Equipamento buscar(@PathVariable Long id){
        return service.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipamento cadastrar(@RequestBody @Valid Equipamento equipamento){
        return service.cadastrar(equipamento);
    }

    @PutMapping("/{id}")
    public Equipamento atualizar(
            @PathVariable Long id,
            @RequestBody @Valid Equipamento dados) {
        return service.atualizar(id, dados);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
