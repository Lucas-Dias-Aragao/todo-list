package com.estudos.todolistservice.dto;

import com.estudos.todolistservice.entity.Tarefa;
import com.estudos.todolistservice.enums.PrioridadeEnum;
import com.estudos.todolistservice.enums.StatusEnum;
import com.estudos.todolistservice.validation.annotation.EnumValid;
import jakarta.validation.constraints.NotBlank;

public record TarefaDTO(
        Long id,
    @NotBlank(message = "O nome da tarefa deve ser informado")
    String nome,
    String descricao,
    @EnumValid(enumClass = StatusEnum.class, message = "Status inválido")
    String status,
    @EnumValid(enumClass = PrioridadeEnum.class, message = "Prioridade inválida")
    String prioridade
    ) {
    public TarefaDTO(Tarefa tarefa) {
        this(
                tarefa.getId(),
                tarefa.getNome(),
                tarefa.getDescricao(),
                tarefa.getStatus().name(),
                tarefa.getPrioridade().name()
        );
    }
}
