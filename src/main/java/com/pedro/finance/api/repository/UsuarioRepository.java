package com.pedro.finance.api.repository;

import com.pedro.finance.api.entity.Transacao;
import com.pedro.finance.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
}
