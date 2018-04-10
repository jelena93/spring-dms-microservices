package descriptor.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import descriptor.dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Set;

@ControllerAdvice
public class DescriptorCustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDto errorDto = fromBindingErrors(exception.getBindingResult());
        System.out.println("*** error " + errorDto);
        return super.handleExceptionInternal(exception, errorDto, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDto errorDto = fromBindingErrors(ex.getBindingResult());
        System.out.println("//// error " + errorDto);
        return super.handleExceptionInternal(ex, errorDto, headers, status, request);
    }

    private static ErrorDto fromBindingErrors(Errors errors) {
        ErrorDto error = new ErrorDto("Validation failed. " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.getErrors().add(objectError.getDefaultMessage());
        }
        return error;
    }
//
//    @ExceptionHandler({Exception.class, RuntimeException.class})
//    public ResponseEntity<ErrorDto> defaultErrorHandler(Exception e) {
//        e.printStackTrace();
//        ErrorDto error = new ErrorDto(e.getMessage());
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorDto> handleHttpClientErrorException(HttpClientErrorException e) throws IOException {
        HttpStatus statusCode = e.getStatusCode();
        String body = e.getResponseBodyAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorDto error = objectMapper.readValue(e.getResponseBodyAsString(), ErrorDto.class);
        System.out.println(statusCode + " " + body);
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
