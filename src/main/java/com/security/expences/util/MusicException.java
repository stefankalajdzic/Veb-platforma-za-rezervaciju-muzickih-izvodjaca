package com.security.expences.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class MusicException extends RuntimeException{

    private static final long serialVersionUID = 1532324752833438579L;

    private HttpStatus status;
    private int expenseStatus;
    private ZonedDateTime dateTime;
    private String message;
    private Map<String, List<String>> validationErrors;
    private String debugMessage;

    public MusicException() {
        dateTime = ZonedDateTime.now();
    }

    public MusicException(HttpStatus status) {
        this();
        this.status = status;
    }

    public MusicException(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = ex.getMessage();
        this.debugMessage = ex.getLocalizedMessage();
    }

    public MusicException(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public MusicException(int expenseStatus, HttpStatus status, String message) {
        this();
        this.status = status;
        this.expenseStatus = expenseStatus;
        this.message = message;
    }

    public MusicException(HttpStatus status, String message,
                          Map<String, List<String>> validationErrors) {
        this();
        this.status = status;
        this.message = message;
        this.validationErrors = validationErrors;
    }

    public MusicException(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public MusicException(HttpStatus status, MethodArgumentNotValidException ex,
                          Map<String, List<String>> validationErrors) {
        this();
        this.status = status;
        this.message = ex.getMessage();
        this.validationErrors = validationErrors;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getExpenseStatus() {
        return expenseStatus;
    }

    public void setExpenseStatus(int expenseStatus) {
        this.expenseStatus = expenseStatus;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, List<String>> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, List<String>> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }
}
