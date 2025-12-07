package com.fatec.itu.agendasalas.exceptions;

import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
import jakarta.validation.ConstraintViolationException;

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

    @ExceptionHandler(RegistroCoordenacaoDuplicadoException.class)
    public ResponseEntity<ApiError> handleRegistroCoordenacaoDuplicado(RegistroCoordenacaoDuplicadoException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(RegistroProfessorDuplicadoException.class)
    public ResponseEntity<ApiError> handleRegistroProfessorDuplicado(RegistroProfessorDuplicadoException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(RecursoJaExisteException.class)
    public ResponseEntity<ApiError> handleRecursoDuplicado(RecursoJaExisteException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<ApiError> handleConflict(ConflitoException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }


    //Erros de Não autorizado (401)
    @ExceptionHandler(JWTNaoEValidoException.class)
    public ResponseEntity<ApiError> handleJWTNaoEValido(JWTNaoEValidoException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }


    //Erros de bad request (400)

    @ExceptionHandler(RequisicaoInvalidaException.class)
    public ResponseEntity<ApiError> handleBadRequest(RequisicaoInvalidaException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request){
        String errorMessage = ex.getBindingResult()
                                .getAllErrors()
                                .stream()
                                .map(error -> error.getDefaultMessage())
                                .collect(Collectors.joining("\n"));
                                
        return buildResponseEntity(HttpStatus.BAD_REQUEST, errorMessage, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request){
        String errorMessage = ex.getConstraintViolations()
                                .stream()
                                .map(violation -> violation.getMessage())
                                .collect(Collectors.joining("\n"));
        return buildResponseEntity(HttpStatus.BAD_REQUEST, errorMessage, request);
    }

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

    @ExceptionHandler(DataInicialMaiorQueDataFinalException.class)
    public ResponseEntity<ApiError> handleAgendamentoRecorrenteComDataInicialAposADataFinal(DataInicialMaiorQueDataFinalException ex, HttpServletRequest request){
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

   
    //Erros de recursos não encontrados (404)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
       return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(RecursoNaoEncontradoException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    //Erros de servidor (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);  
    }

   
}
