package com.estudos.todolistservice.entity;

import com.estudos.todolistservice.enums.PrioridadeEnum;
import com.estudos.todolistservice.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Entity
@Table(name = "tb_tarefa")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}
