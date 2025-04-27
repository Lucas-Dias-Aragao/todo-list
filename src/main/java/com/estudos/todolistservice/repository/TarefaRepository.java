package com.estudos.todolistservice.repository;

import com.estudos.todolistservice.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

}
