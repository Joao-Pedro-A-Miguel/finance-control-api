package com.pedro.finance.api.dto;

import com.pedro.finance.api.Enum.Tipo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoriaResponseDTO {

    private Long id;
    private String nome;
    private Tipo tipo;
}