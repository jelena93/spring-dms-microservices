package document.handler;

import document.dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class DocumentCustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDto errorDto = fromBindingErrors(exception.getBindingResult());
        return super.handleExceptionInternal(exception, errorDto, headers, status, request);
    }

    private static ErrorDto fromBindingErrors(Errors errors) {
        ErrorDto error = new ErrorDto("Validation failed. " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.getErrors().add(objectError.getDefaultMessage());
        }
        return error;
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorDto> defaultErrorHandler(Exception e) {
        System.out.println("usaooo HEEE");
        ErrorDto error = new ErrorDto(e.getMessage());
        System.out.println(error);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolation(Exception ex) {
        ex.printStackTrace();
        ErrorDto error = new ErrorDto(ex.getMessage());
        Throwable cause = ((TransactionSystemException) ex).getRootCause();
        if (cause instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                error.getErrors().add(constraintViolation.getMessage());
            }
        }
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
