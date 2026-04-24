package com.exemplo.transacoes.controller;

import com.exemplo.transacoes.dto.TransacaoRequestDTO;
import com.exemplo.transacoes.dto.TransacaoResponseDTO;
import com.exemplo.transacoes.service.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TransacaoController {
    
    private final TransacaoService transacaoService;
    
    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> criarTransacao(@Valid @RequestBody TransacaoRequestDTO requestDTO) {
        TransacaoResponseDTO responseDTO = transacaoService.criarTransacao(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    
    @GetMapping
    public ResponseEntity<List<TransacaoResponseDTO>> listarTodasTransacoes() {
        List<TransacaoResponseDTO> transacoes = transacaoService.listarTodasTransacoes();
        return ResponseEntity.ok(transacoes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TransacaoResponseDTO> buscarTransacaoPorId(@PathVariable Integer id) {
        TransacaoResponseDTO responseDTO = transacaoService.buscarTransacaoPorId(id);
        return ResponseEntity.ok(responseDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TransacaoResponseDTO> atualizarTransacao(
            @PathVariable Integer id, 
            @Valid @RequestBody TransacaoRequestDTO requestDTO) {
        TransacaoResponseDTO responseDTO = transacaoService.atualizarTransacao(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTransacao(@PathVariable Integer id) {
        transacaoService.deletarTransacao(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<TransacaoResponseDTO>> buscarPorDescricao(@RequestParam String descricao) {
        List<TransacaoResponseDTO> transacoes = transacaoService.buscarPorDescricao(descricao);
        return ResponseEntity.ok(transacoes);
    }
    
    @GetMapping("/buscar-por-valor")
    public ResponseEntity<List<TransacaoResponseDTO>> buscarPorFaixaValor(
            @RequestParam BigDecimal valorMin, 
            @RequestParam BigDecimal valorMax) {
        List<TransacaoResponseDTO> transacoes = transacaoService.buscarPorFaixaValor(valorMin, valorMax);
        return ResponseEntity.ok(transacoes);
    }
}
