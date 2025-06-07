package com.estudos.todolistservice.exception;

import java.util.Map;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Map<String,String> erros;

    private String mensagem;
    public BusinessException(Map<String,String> erros) {
        this.erros = erros;
    }

    public BusinessException(String mensagem) {
        super(mensagem);
    }

    public Map<String, String> getErros() {
        return erros;
    }
}
