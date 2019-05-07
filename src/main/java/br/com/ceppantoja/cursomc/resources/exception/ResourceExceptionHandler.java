package br.com.ceppantoja.cursomc.resources.exception;

import br.com.ceppantoja.cursomc.service.exception.AuthorizationException;
import br.com.ceppantoja.cursomc.service.exception.DataIntegrityException;
import br.com.ceppantoja.cursomc.service.exception.FileException;
import br.com.ceppantoja.cursomc.service.exception.ObjectNotFoundException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, HttpStatus.NOT_FOUND, "Não encontrado", request.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, HttpStatus.BAD_REQUEST, "Integridade de dados", request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        final HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationError validationError = new ValidationError(System.currentTimeMillis(), status.value(), "Erro de Validação", e.getMessage(), request.getRequestURI());

        e.getBindingResult().getFieldErrors().forEach(error -> validationError.addError(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(status).body(validationError);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> forbidden(AuthorizationException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, HttpStatus.FORBIDDEN, "Acesso negado", request.getRequestURI());
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<StandardError> file(FileException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, HttpStatus.BAD_REQUEST, "Erro de arquivo", request.getRequestURI());
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, HttpStatus.valueOf(e.getStatusCode()), "Erro Amazon Service", request.getRequestURI());
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, HttpStatus.BAD_REQUEST, "Erro Amazon Client", request.getRequestURI());
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, HttpStatus.BAD_REQUEST, "Erro Amazon S3", request.getRequestURI());
    }

    private ResponseEntity<StandardError> getStandardErrorResponseEntity(RuntimeException e, HttpStatus status, String error, String uri) {
        StandardError standardError = new StandardError(System.currentTimeMillis(), status.value(), error, e.getMessage(), uri);

        return ResponseEntity.status(status).body(standardError);
    }
}