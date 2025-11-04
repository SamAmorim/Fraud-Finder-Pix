package org.uam.sdm.pixapi.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.uam.sdm.pixapi.exceptions.RecursoNaoEncontradoException;

@ControllerAdvice
public class ExcecoesGlobalHandler {
    
    public record ErroResponse(
        String mensagem
    ) {
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErroResponse handleGenericException(Exception ex) {
        return new ErroResponse("Ocorreu um erro inesperado.");
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErroResponse handleRecursoNaoEncontradoException(RecursoNaoEncontradoException ex) {
        return new ErroResponse(ex.getMessage());
    }
}
