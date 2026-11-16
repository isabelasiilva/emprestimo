package com.projeto.atividade2.repository;

import com.projeto.atividade2.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa,Long> {
}
