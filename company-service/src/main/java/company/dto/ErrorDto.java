package company.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorDto {

    private List<String> errors = new ArrayList<>();
    private final String message;

    public ErrorDto(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "errors=" + errors +
                ", message='" + message + '\'' +
                '}';
    }
}
