package com.projeto.emprestimo;

import com.projeto.emprestimo.exception.ConflitoException;
import com.projeto.emprestimo.model.Colaborador;
import com.projeto.emprestimo.model.Emprestimo;
import com.projeto.emprestimo.model.Equipamento;
import com.projeto.emprestimo.model.TipoEquipamento;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoTest {

    private Equipamento equipamento() {
        Equipamento e = new Equipamento();
        e.setId(1L);
        e.setPatrimonio("NB-001");
        e.setNome("Notebook Dell");
        e.setTipo(TipoEquipamento.NOTEBOOK);
        e.setDisponivel(true);
        return e;
    }

    private Colaborador colaborador() {
        Colaborador c = new Colaborador();
        c.setId(1L);
        c.setNome("Ana Souza");
        c.setEmail("ana@empresa.com");
        c.setCargo("Analista");
        return c;
    }

    @Test
    void criarEmprestimoEm14De11TemPrazo28De11() {
        LocalDate hoje = LocalDate.of(2026, 11, 14);
        Emprestimo emprestimo = new Emprestimo(equipamento(), colaborador(), hoje);
        assertEquals(LocalDate.of(2026, 11, 14), emprestimo.getDataEmprestimo());
        assertEquals(LocalDate.of(2026, 11, 28), emprestimo.getDataPrevistaDevolucao());
    }

    @Test
    void devolverEmprestimoAbertoEm20De11() {
        LocalDate hoje = LocalDate.of(2026, 11, 14);
        Emprestimo emprestimo = new Emprestimo(equipamento(), colaborador(), hoje);
        emprestimo.devolver(LocalDate.of(2026, 11, 20));
        assertEquals(LocalDate.of(2026, 11, 20), emprestimo.getDataDevolucao());
    }

    @Test
    void devolverEmprestimoDuasVezesLancaExcecao() {
        LocalDate hoje = LocalDate.of(2026, 11, 14);
        Emprestimo emprestimo = new Emprestimo(equipamento(), colaborador(), hoje);
        emprestimo.devolver(LocalDate.of(2026, 11, 20));
        assertThrows(ConflitoException.class,
                () -> emprestimo.devolver(LocalDate.of(2026, 11, 21)));
    }

    @Test
    void emprestimoCriadoEm27De10NaoDevolvidoConsultadoEm14De11EstaAtrasado() {
        LocalDate hoje = LocalDate.of(2026, 10, 27);
        Emprestimo emprestimo = new Emprestimo(equipamento(), colaborador(), hoje);
        assertTrue(emprestimo.estaAtrasadoEm(LocalDate.of(2026, 11, 14)));
    }

    @Test
    void emprestimoCriadoEm06De11NaoDevolvidoConsultadoEm14De11NaoEstaAtrasado() {
        LocalDate hoje = LocalDate.of(2026, 11, 6);
        Emprestimo emprestimo = new Emprestimo(equipamento(), colaborador(), hoje);
        assertFalse(emprestimo.estaAtrasadoEm(LocalDate.of(2026, 11, 14)));
    }

    @Test
    void emprestimoCriadoEm27De10DevolvidoEm12De11ConsultadoEm14De11NaoEstaAtrasado() {
        LocalDate hoje = LocalDate.of(2026, 10, 27);
        Emprestimo emprestimo = new Emprestimo(equipamento(), colaborador(), hoje);
        emprestimo.devolver(LocalDate.of(2026, 11, 12));
        assertFalse(emprestimo.estaAtrasadoEm(LocalDate.of(2026, 11, 14)));
    }

    @Test
    void emprestimoCriadoEm31De10ConsultadoEm14De11NaoEstaAtrasado() {
        LocalDate hoje = LocalDate.of(2026, 10, 31);
        Emprestimo emprestimo = new Emprestimo(equipamento(), colaborador(), hoje);
        assertFalse(emprestimo.estaAtrasadoEm(LocalDate.of(2026, 11, 14)));
    }
}
