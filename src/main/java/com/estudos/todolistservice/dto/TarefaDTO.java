package com.estudos.todolistservice.dto;

import com.estudos.todolistservice.entity.Tarefa;
import com.estudos.todolistservice.enums.PrioridadeEnum;
import com.estudos.todolistservice.enums.StatusEnum;
import com.estudos.todolistservice.validation.annotation.EnumValid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TarefaDTO {
    private Long id;

    @NotBlank(message = "O nome da tarefa deve ser informado")
    private String nome;

    private String descricao;

    @EnumValid(enumClass = StatusEnum.class, message = "Status inválido")
    private String status;

    @EnumValid(enumClass = PrioridadeEnum.class, message = "Prioridade inválida")
    private String prioridade;


    public TarefaDTO(String nome, String descricao, StatusEnum status, PrioridadeEnum prioridade) {
        this(null, nome, descricao, status.name(), prioridade.name());
    }

    public TarefaDTO(Tarefa tarefa) {
        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.descricao = tarefa.getDescricao();
        this.status = String.valueOf(tarefa.getStatus());
        this.prioridade = String.valueOf(tarefa.getPrioridade());
    }
}