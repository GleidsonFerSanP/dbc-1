package br.com.dbccompany.core.excepiton;

public class InvalidCodeException extends RuntimeException {
    public InvalidCodeException(final String message) {
        super(message);
    }
}
