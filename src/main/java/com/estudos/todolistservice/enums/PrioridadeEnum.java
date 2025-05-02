package com.estudos.todolistservice.enums;

import java.util.Arrays;
import java.util.Optional;

public enum PrioridadeEnum {
    BAIXA(1, "Baixa"),
    MEDIA(2, "Média"),
    ALTA(3, "Alta");

    private final int valor;
    private final String descricao;

    PrioridadeEnum(int valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public int getValor() {
        return valor;
    }

    public String getLabel() {
        return descricao;
    }

    public static Optional<PrioridadeEnum> fromValor(int valor) {
        return Arrays.stream(values())
                .filter(prioridade -> prioridade.getValor() == valor)
                .findFirst();
    }
}
