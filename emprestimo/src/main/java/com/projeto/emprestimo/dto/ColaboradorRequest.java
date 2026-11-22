package com.projeto.emprestimo.dto;

import jakarta.validation.constraints.NotNull;

// request de colaborador para nao expor o campo de ID no POST
public record ColaboradorRequest(
        String nome,
        String email,
        String cep,
        String numero,
        String logradouro,
        String bairro,
        String cidade,
        String uf){
}
