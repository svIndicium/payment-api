package hu.indicium.dev.payment.infrastructure.util;

import java.util.ArrayList;
import java.util.List;

public class ErrorDto {
    private String field;
    private String message;
    private String stackTrace;
    private List<ErrorDto> errors;

    public void addError(ErrorDto errorDto) {
        if (this.errors == null) {
            errors = new ArrayList<>();
        }
        this.errors.add(errorDto);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public List<ErrorDto> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDto> errors) {
        this.errors = errors;
    }
}