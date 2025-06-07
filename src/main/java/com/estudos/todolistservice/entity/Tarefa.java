package com.estudos.todolistservice.entity;

import com.estudos.todolistservice.dto.TarefaDTO;
import com.estudos.todolistservice.enums.PrioridadeEnum;
import com.estudos.todolistservice.enums.StatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_tarefa")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Enumerated(EnumType.STRING)
    private PrioridadeEnum prioridade;

    public Tarefa() {}
    public Tarefa(TarefaDTO dados) {
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.status = StatusEnum.valueOf(dados.status().toString());
        this.prioridade = PrioridadeEnum.valueOf(dados.prioridade());
    }

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
