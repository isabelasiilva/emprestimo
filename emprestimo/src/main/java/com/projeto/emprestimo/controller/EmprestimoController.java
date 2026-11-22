package com.projeto.emprestimo.controller;

import com.projeto.emprestimo.dto.EmprestimoRequest;
import com.projeto.emprestimo.dto.EmprestimoResponse;
import com.projeto.emprestimo.model.Emprestimo;
import com.projeto.emprestimo.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {
    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService service) {
        this.emprestimoService = service;
    }

    @GetMapping
    public List<Emprestimo> listar(@RequestParam(required = false) Boolean atrasados) {
        return emprestimoService.listar(atrasados);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Emprestimo emprestar(@RequestBody @Valid EmprestimoRequest pedido) {
        return emprestimoService.emprestar(pedido);
    }

    @PutMapping("{id}/devolucao")
    public Emprestimo devolver(@PathVariable Long id){
        return emprestimoService.devolver(id);
    }
}
