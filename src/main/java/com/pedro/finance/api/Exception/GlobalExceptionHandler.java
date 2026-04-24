package com.pedro.finance.api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // EM ANALISE
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, String>> handleRegraNegocio(RegraNegocioException ex) {

        Map<String, String> erro = new HashMap<>();
        erro.put("erro", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> HandleValidatioErros(MethodArgumentNotValidException methodArgumentNotValidException){

        Map<String, String> erros = new HashMap<>();

        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(
        erro -> {erros.put(erro.getField(), erro.getDefaultMessage());
        });

        return erros;
    }
}
