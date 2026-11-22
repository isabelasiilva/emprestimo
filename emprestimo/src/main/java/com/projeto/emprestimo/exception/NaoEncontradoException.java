package com.projeto.emprestimo.exception;

public class NaoEncontradoException extends RuntimeException {
    public NaoEncontradoException(String mensagem) {
        super(mensagem); // manda a mensagem para a mãe dessa classe: RuntimeException
    }
}
