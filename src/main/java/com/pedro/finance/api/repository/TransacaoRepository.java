package com.pedro.finance.api.repository;

import com.pedro.finance.api.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("SELECT t FROM Transacao t WHERE MONTH(t.data) = :mes AND YEAR(t.data) = :ano")
    List<Transacao> buscarPorMesAno(int mes, int ano);
}
