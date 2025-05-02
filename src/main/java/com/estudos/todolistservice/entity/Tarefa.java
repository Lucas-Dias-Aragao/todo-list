package com.estudos.todolistservice.entity;

import com.estudos.todolistservice.enums.PrioridadeEnum;
import com.estudos.todolistservice.enums.StatusEnum;
import com.estudos.todolistservice.validation.annotation.EnumValid;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_tarefa")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O nome da tarefa deve ser informado")
    private String nome;
    private String descricao;

    @Enumerated(EnumType.STRING)
    @EnumValid(enumClass = StatusEnum.class, message = "Status inválido")
    private StatusEnum status;
    @Enumerated(EnumType.STRING)
    @EnumValid(enumClass = PrioridadeEnum.class, message = "Prioridade inválida")
    private PrioridadeEnum prioridade;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public PrioridadeEnum getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(PrioridadeEnum prioridade) {
        this.prioridade = prioridade;
    }
}
