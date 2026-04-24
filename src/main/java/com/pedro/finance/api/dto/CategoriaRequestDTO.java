package com.pedro.finance.api.dto;

import com.pedro.finance.api.Enum.Tipo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoriaRequestDTO {

    @NotNull(message = "Nome não pode ser nulo")
    private String nome;

    @NotNull(message = "Tipo não pode ser nulo")
    private Tipo tipo;
}