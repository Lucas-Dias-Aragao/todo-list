package com.estudos.todolistservice.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getErros());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String campo = error.getField();
            String mensagem = error.getDefaultMessage();
            erros.put(campo, mensagem);
        });
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            String campo = ife.getPath().get(0).getFieldName(); // campo onde ocorreu o erro
            String valor = ife.getValue().toString(); // valor inválido enviado
            String mensagem = "Valor inválido '" + valor + "' para o campo '" + campo + "'. Valores aceitos: " +
                    Arrays.toString(((Class<?>) ife.getTargetType()).getEnumConstants());

            Map<String, String> erro = new HashMap<>();
            erro.put(campo, mensagem);
            return ResponseEntity.badRequest().body(erro);
        }

        // fallback se não for erro de enum
        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", "Erro de leitura do corpo da requisição.");
        return ResponseEntity.badRequest().body(erro);
    }
}
