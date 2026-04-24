package com.pedro.finance.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransacaoResponseDTO {

        private Long id;
        private String descricao;
        private Double valor;
        private LocalDate data;
        private CategoriaResponseDTO categoria;
}