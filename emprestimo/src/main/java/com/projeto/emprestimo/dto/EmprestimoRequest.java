package com.projeto.emprestimo.dto;

import jakarta.validation.constraints.NotNull;

// para entrada de dados de equipamento
public record EmprestimoRequest(
    @NotNull(message = "O equipamento é obrigatório") Long equipamentoId,
    @NotNull(message = "O colaborador é obrigatório") Long colaboradorId){
    // passamos 2 parametros mas por ser record nao vemos tudo o que tem por tras, os metodos que pode usar
    // todos os outros parametros tem configuraçoes de como os valores serao setados, num request o DTO só vai receber os campos que o usuario vai preencher, que sao só o tipo de equipamento e quem está usando
}
