package com.pedro.finance.api.repository;

import com.pedro.finance.api.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("""
    SELECT t FROM Transacao t
    WHERE t.usuario.email = :email
    AND MONTH(t.data) = :mes
    AND YEAR(t.data) = :ano
    """)
    List<Transacao> buscarPorMesAnoEUsuario(String email, int mes, int ano);

    List<Transacao> findByUsuarioEmail(String email);

    Optional<Transacao> findByIdAndUsuarioEmail(Long id, String email);
}
