package br.com.dbccompany.core.exception;

public class InvalidCodeException extends RuntimeException {
    public InvalidCodeException(final String message) {
        super(message);
    }
}
