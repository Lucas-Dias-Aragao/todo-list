package com.estudos.todolistservice.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final Map<String,String> erros;
    public BusinessException(Map<String,String> erros) {
        this.erros = erros;
    }
    public Map<String, String> getErros() {
        return erros;
    }
}
