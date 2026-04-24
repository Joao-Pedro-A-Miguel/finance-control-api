package com.pedro.finance.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pedro.finance.api.Enum.Tipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Usuario usuario;

    private Double valor;

    @ManyToOne
    private Categoria categoria;

    @NotBlank
    private String descricao;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    private LocalDate data;



}
