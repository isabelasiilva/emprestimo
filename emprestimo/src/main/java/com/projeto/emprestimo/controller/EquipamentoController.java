package com.projeto.emprestimo.controller;


import com.projeto.emprestimo.model.Equipamento;
import com.projeto.emprestimo.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {
    private final EquipamentoService service;
    public EquipamentoController(EquipamentoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Equipamento buscar(@PathVariable Long id){
        return service.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  Equipamento cadastrar(@RequestBody @Valid Equipamento equipamento){
        return service.cadastrar(equipamento);
    }
}
