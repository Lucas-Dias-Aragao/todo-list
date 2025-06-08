package com.estudos.todolistservice.mapper;

import com.estudos.todolistservice.dto.TarefaDTO;
import com.estudos.todolistservice.entity.Tarefa;
import com.estudos.todolistservice.enums.PrioridadeEnum;
import com.estudos.todolistservice.enums.StatusEnum;

public class TarefaMapper {

    public static Tarefa toEntity(TarefaDTO dto) {
        return Tarefa.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .status(StatusEnum.valueOf(dto.getStatus()))
                .prioridade(PrioridadeEnum.valueOf(dto.getPrioridade()))
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
