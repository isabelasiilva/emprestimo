package com.projeto.emprestimo.dto;

import java.time.LocalDate;

// é a resposta, o resultado do banco de dados formatado para aparecer no seu sistema
public record EmprestimoResponse(
        Long id,
        String equipamentoNome,
        String colaboradorNome,
        LocalDate dataPrevistaDevolucao){
}
