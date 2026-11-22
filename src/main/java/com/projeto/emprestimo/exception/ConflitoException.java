package com.projeto.emprestimo.exception;

public class ConflitoException extends RuntimeException {
    public ConflitoException(String message) {
        super(message); // manda a mensagem para a mãe dessa classe: RuntimeException
    }
}
