package com.exemplo.transacoes.exception;

public class TransacaoNotFoundException extends RuntimeException {
    public TransacaoNotFoundException(String message) {
        super(message);
    }
}
