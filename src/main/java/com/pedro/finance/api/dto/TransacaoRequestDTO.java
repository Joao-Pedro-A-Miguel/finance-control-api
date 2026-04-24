package com.pedro.finance.api.dto;

import com.pedro.finance.api.Enum.Tipo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransacaoRequestDTO {

    @NotNull(message = "Valor não pode ser nulo")
    private Double valor;

    @NotNull(message = "Descrição não pode ser nula")
    private String descricao;

    @NotNull(message = "Tipo não pode ser nulo")
    private Tipo tipo;

    @NotNull(message = "Data não pode ser nula")
    private LocalDate date;

    @NotNull(message = "Usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "Categoria é obrigatória")
    private Long categoriaId;
}