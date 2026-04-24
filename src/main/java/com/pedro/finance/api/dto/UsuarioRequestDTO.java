package com.pedro.finance.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @NotNull(message = "Nome não pode ser nulo")
    private String nome;

    @Email(message = "Email inválido")
    @NotNull(message = "Email não pode ser nulo")
    private String email;

    @NotNull(message = "Senha não pode ser nula")
    private String senha;
}
