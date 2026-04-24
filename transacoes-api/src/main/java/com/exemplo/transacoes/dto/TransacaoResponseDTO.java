package com.exemplo.transacoes.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransacaoResponseDTO {
    private Integer id;
    private String descricao;
    private BigDecimal valor;
    private LocalDateTime dataTransacao;
}
