package org.uam.sdm.pixapi.exceptions;

public class TransacaoBloqueadaException extends RuntimeException {

    public TransacaoBloqueadaException(String mensagem) {
        super(mensagem);
    }
}
