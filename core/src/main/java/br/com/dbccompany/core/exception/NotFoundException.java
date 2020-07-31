package br.com.dbccompany.core.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(final String message) {
        super(message);
    }
}
