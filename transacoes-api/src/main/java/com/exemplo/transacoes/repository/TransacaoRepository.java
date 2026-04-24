package com.exemplo.transacoes.repository;

import com.exemplo.transacoes.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
    
    List<Transacao> findByDescricaoContainingIgnoreCase(String descricao);
    
    List<Transacao> findByValorBetween(BigDecimal valorMin, BigDecimal valorMax);
    
    List<Transacao> findByDataTransacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    @Query("SELECT t FROM Transacao t ORDER BY t.dataTransacao DESC")
    List<Transacao> findAllOrderByDataTransacaoDesc();
}
