package br.com.ceppantoja.cursomc.resources.exception;

import br.com.ceppantoja.cursomc.service.exception.AuthorizationException;
import br.com.ceppantoja.cursomc.service.exception.DataIntegrityException;
import br.com.ceppantoja.cursomc.service.exception.ObjectNotFoundException;
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
        return getStandardErrorResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;

        ValidationError validationError = new ValidationError(status.value(), "Erro de validação", System.currentTimeMillis());

        e.getBindingResult().getFieldErrors().forEach(error -> validationError.addError(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(status).body(validationError);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> forbidden(ObjectNotFoundException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<StandardError> getStandardErrorResponseEntity(RuntimeException e, HttpStatus status) {
        StandardError standardError = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(status).body(standardError);
    }
}