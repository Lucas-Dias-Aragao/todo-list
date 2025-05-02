package com.estudos.todolistservice.enums;

import java.util.Arrays;
import java.util.Optional;

public enum StatusEnum {
    PENDENTE(1, "Pendente"),
    EM_ANDAMENTO(2, "Em andamento"),
    CONCLUIDO(3, "Concluído");

    private final int valor;
    private final String descricao;

    StatusEnum(int valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public int getValor() {
        return valor;
    }

    public String getLabel() {
        return descricao;
    }

    public static Optional<StatusEnum> fromValor(int valor) {
        return Arrays.stream(values())
                .filter(status -> status.getValor() == valor)
                .findFirst();
    }
}
