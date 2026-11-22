package com.projeto.emprestimo;

import com.projeto.emprestimo.model.Emprestimo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmprestimoTest {
    @Test
    void emprestimoVencidoENaoDevolvidoEstaAtrasado() {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataPrevistaDevolucao(LocalDate.now().minusDays(1)); // venceu ontem
        assertTrue(emprestimo.isAtrasado());
    }
}
