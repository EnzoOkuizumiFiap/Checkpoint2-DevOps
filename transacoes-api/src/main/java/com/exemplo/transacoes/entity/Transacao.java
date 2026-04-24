package com.exemplo.transacoes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String descricao;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal valor;
    
    @Column(name = "data_transacao")
    private LocalDateTime dataTransacao;
    
    @PrePersist
    protected void onCreate() {
        if (dataTransacao == null) {
            dataTransacao = LocalDateTime.now();
        }
    }
}
