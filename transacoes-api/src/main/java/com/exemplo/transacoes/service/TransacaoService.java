package com.exemplo.transacoes.service;

import com.exemplo.transacoes.dto.TransacaoRequestDTO;
import com.exemplo.transacoes.dto.TransacaoResponseDTO;
import com.exemplo.transacoes.entity.Transacao;
import com.exemplo.transacoes.exception.TransacaoNotFoundException;
import com.exemplo.transacoes.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransacaoService {
    
    private final TransacaoRepository transacaoRepository;
    
    @Transactional
    public TransacaoResponseDTO criarTransacao(TransacaoRequestDTO requestDTO) {
        Transacao transacao = new Transacao();
        transacao.setDescricao(requestDTO.getDescricao());
        transacao.setValor(requestDTO.getValor());
        transacao.setDataTransacao(LocalDateTime.now());
        
        Transacao transacaoSalva = transacaoRepository.save(transacao);
        return convertToResponseDTO(transacaoSalva);
    }
    
    @Transactional(readOnly = true)
    public List<TransacaoResponseDTO> listarTodasTransacoes() {
        return transacaoRepository.findAllOrderByDataTransacaoDesc()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public TransacaoResponseDTO buscarTransacaoPorId(Integer id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new TransacaoNotFoundException("Transação não encontrada com ID: " + id));
        return convertToResponseDTO(transacao);
    }
    
    @Transactional
    public TransacaoResponseDTO atualizarTransacao(Integer id, TransacaoRequestDTO requestDTO) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new TransacaoNotFoundException("Transação não encontrada com ID: " + id));
        
        transacao.setDescricao(requestDTO.getDescricao());
        transacao.setValor(requestDTO.getValor());
        transacao.setDataTransacao(requestDTO.getDataTransacao());
        
        Transacao transacaoAtualizada = transacaoRepository.save(transacao);
        return convertToResponseDTO(transacaoAtualizada);
    }
    
    @Transactional
    public void deletarTransacao(Integer id) {
        if (!transacaoRepository.existsById(id)) {
            throw new TransacaoNotFoundException("Transação não encontrada com ID: " + id);
        }
        transacaoRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<TransacaoResponseDTO> buscarPorDescricao(String descricao) {
        return transacaoRepository.findByDescricaoContainingIgnoreCase(descricao)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<TransacaoResponseDTO> buscarPorFaixaValor(BigDecimal valorMin, BigDecimal valorMax) {
        return transacaoRepository.findByValorBetween(valorMin, valorMax)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    private TransacaoResponseDTO convertToResponseDTO(Transacao transacao) {
        TransacaoResponseDTO responseDTO = new TransacaoResponseDTO();
        responseDTO.setId(transacao.getId());
        responseDTO.setDescricao(transacao.getDescricao());
        responseDTO.setValor(transacao.getValor());
        responseDTO.setDataTransacao(transacao.getDataTransacao());
        return responseDTO;
    }
}
