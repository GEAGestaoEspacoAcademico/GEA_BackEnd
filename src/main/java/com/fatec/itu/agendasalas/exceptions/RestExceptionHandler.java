package com.fatec.itu.agendasalas.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fatec.itu.agendasalas.exceptions.janelasHorario.FormatoDataInvalidoException;
import com.fatec.itu.agendasalas.exceptions.janelasHorario.ListaDatasInvalidaException;
import com.fatec.itu.agendasalas.exceptions.janelasHorario.SalaIdObrigatorioException;
import com.fatec.itu.agendasalas.exceptions.usuarios.FalhaAoDeletarAgendamentoException;
import com.fatec.itu.agendasalas.exceptions.usuarios.FalhaAoDesvincularCursoException;
import com.fatec.itu.agendasalas.exceptions.usuarios.FalhaAoDesvincularDisciplinaException;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    private ResponseEntity<ApiError> buildResponseEntity(HttpStatus status, String message, HttpServletRequest request) {
    ApiError apiError = new ApiError(
        status.value(),
        status.getReasonPhrase(),
        message,
        request.getRequestURI()
    );
    return ResponseEntity.status(status).body(apiError);
    }



    //Erros de conflito (409)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ApiError> handleEmailJaCadastrado(EmailJaCadastradoException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(MatriculaDuplicadaSecretariaException.class)
    public ResponseEntity<ApiError> handleMatriculaDuplicadaSecretaria(MatriculaDuplicadaSecretariaException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(RecursoJaAdicionadoNaSalaException.class)
    public ResponseEntity<ApiError> handleRecursoJaAdicionadoNaSala(RecursoJaAdicionadoNaSalaException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(FalhaAoDeletarAgendamentoException.class)
    public ResponseEntity<ApiError> handleFalhaAoDeletarAgendamento(FalhaAoDeletarAgendamentoException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(FalhaAoDesvincularCursoException.class)
    public ResponseEntity<ApiError> handleFalhaAoDesvincularCurso(FalhaAoDesvincularCursoException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(FalhaAoDesvincularDisciplinaException.class)
    public ResponseEntity<ApiError> handleFalhaAoDesvincularDisciplina(FalhaAoDesvincularDisciplinaException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(ConflitoAoAgendarException.class)
    public ResponseEntity<ApiError> handleConflitoAoAgendar(ConflitoAoAgendarException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }




    //Erros de bad request (400)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ApiError> handleMessagingError(MessagingException ex, HttpServletRequest request){
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(DataNoPassadoException.class)
    public ResponseEntity<ApiError> handleDataNoPassado(DataNoPassadoException ex, HttpServletRequest request){
         return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(FormatoDataInvalidoException.class)
    public ResponseEntity<ApiError> handleFormatoDataInvalido(FormatoDataInvalidoException ex, HttpServletRequest request){
         return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(ListaDatasInvalidaException.class)
    public ResponseEntity<ApiError> handleListaDatasInvalida(ListaDatasInvalidaException ex, HttpServletRequest request){
         return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(SalaIdObrigatorioException.class)
    public ResponseEntity<ApiError> handleSalaIdObrigatorio(SalaIdObrigatorioException ex, HttpServletRequest request){
         return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(AgendamentoComHorarioIndisponivelException.class)
    public ResponseEntity<ApiError> handleAgendamentoComHorarioIndisponivel(AgendamentoComHorarioIndisponivelException ex, HttpServletRequest request){
         return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(AgendamentoRecorrenteComDataInicialAposADataFinalException.class)
    public ResponseEntity<ApiError> handleAgendamentoRecorrenteComDataInicialAposADataFinal(AgendamentoRecorrenteComDataInicialAposADataFinalException ex, HttpServletRequest request){
         return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(SenhasNaoConferemException.class)
    public ResponseEntity<ApiError> handleSenhasNaoConfere(SenhasNaoConferemException ex, HttpServletRequest request){
         return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(JanelaHorarioPassouException.class)
    public ResponseEntity<ApiError> handleJanelaHorarioPassou(JanelaHorarioPassouException ex, HttpServletRequest request){
         return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, HttpServletRequest request) {
         return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    //Erros de recursos não encontrados (404)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
       return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

}
