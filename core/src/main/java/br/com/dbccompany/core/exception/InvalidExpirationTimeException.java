package br.com.dbccompany.core.exception;

public class InvalidExpirationTimeException extends RuntimeException {
    public InvalidExpirationTimeException(String message) {
        super(message);
    }
}
