package com.estudos.todolistservice.mapper;

import com.estudos.todolistservice.dto.TarefaDTO;
import com.estudos.todolistservice.entity.Tarefa;
import com.estudos.todolistservice.enums.PrioridadeEnum;
import com.estudos.todolistservice.enums.StatusEnum;

public class TarefaMapper {

    public static Tarefa toEntity(TarefaDTO dto) {
        return Tarefa.builder()
                .id(dto.id())
                .nome(dto.nome())
                .descricao(dto.descricao())
                .status(StatusEnum.valueOf(dto.status()))
                .prioridade(PrioridadeEnum.valueOf(dto.prioridade()))
                .build();
    }

    public static TarefaDTO toDTO(Tarefa tarefa) {
        return new TarefaDTO(
                tarefa.getId(),
                tarefa.getNome(),
                tarefa.getDescricao(),
                tarefa.getStatus().name(),
                tarefa.getPrioridade().name()
        );
    }
}
