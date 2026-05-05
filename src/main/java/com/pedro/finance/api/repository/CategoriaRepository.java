package com.pedro.finance.api.repository;

import com.pedro.finance.api.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByIdAndUsuarioEmail(Long id, String email);

    List<Categoria> findByUsuarioEmail(String email);
}
