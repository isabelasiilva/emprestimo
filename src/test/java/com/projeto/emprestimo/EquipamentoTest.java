package com.projeto.emprestimo;

import com.projeto.emprestimo.exception.ConflitoException;
import com.projeto.emprestimo.model.Equipamento;
import com.projeto.emprestimo.model.TipoEquipamento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquipamentoTest {

    private Equipamento equipamentoNotebook() {
        Equipamento e = new Equipamento();
        e.setPatrimonio("NB-001");
        e.setNome("Notebook Dell");
        e.setTipo(TipoEquipamento.NOTEBOOK);
        e.setDisponivel(true);
        return e;
    }

    @Test
    void emprestarEquipamentoDisponivelFicaIndisponivel() {
        Equipamento equipamento = equipamentoNotebook();
        equipamento.emprestar();
        assertFalse(equipamento.isDisponivel());
    }

    @Test
    void emprestarEquipamentoIndisponivelLancaExcecao() {
        Equipamento equipamento = equipamentoNotebook();
        equipamento.emprestar();
        assertThrows(ConflitoException.class, equipamento::emprestar);
    }

    @Test
    void devolverEquipamentoFicaDisponivel() {
        Equipamento equipamento = equipamentoNotebook();
        equipamento.emprestar();
        equipamento.devolver();
        assertTrue(equipamento.isDisponivel());
    }
}
