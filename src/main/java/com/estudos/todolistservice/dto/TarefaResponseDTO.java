package com.estudos.todolistservice.dto;

import com.estudos.todolistservice.entity.Tarefa;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class TarefaResponseDTO {

    private Tarefa tarefa;
    private Map<String, String> mensagens;

    public TarefaResponseDTO() {}

    private TarefaResponseDTO(Tarefa tarefa, Map<String, String> mensagens) {
        this.tarefa = tarefa;
        this.mensagens = mensagens;
    }

    public TarefaResponseDTO(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public TarefaResponseDTO(Map<String, String> mensagens) {
        this.mensagens = mensagens;
    }

    public static TarefaResponseDTO success(Tarefa tarefa) {
        return new TarefaResponseDTO(tarefa);
    }

    public static TarefaResponseDTO exception(Map<String, String> mensagens, HttpStatus badRequest) {
        return new TarefaResponseDTO(mensagens);
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public Map<String, String> getMensagens() {
        return mensagens;
    }

}
