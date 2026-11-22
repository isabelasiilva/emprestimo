package com.projeto.emprestimo.dto;

import com.projeto.emprestimo.model.TipoEquipamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipamentoRequest(
        @NotBlank(message = "O patrimônio é obrigatório") String patrimonio,
        @NotBlank(message = "O nome é obrigatório") String nome,
        @NotNull(message = "O tipo é obrigatório") TipoEquipamento tipo) {
}
