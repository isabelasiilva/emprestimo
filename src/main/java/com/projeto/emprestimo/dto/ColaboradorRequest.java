package com.projeto.emprestimo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// request de colaborador para nao expor o campo de ID no POST
public record ColaboradorRequest(
        @NotBlank(message = "O nome é obrigatório") String nome,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Email inválido") String email,
        String cargo) {
}
