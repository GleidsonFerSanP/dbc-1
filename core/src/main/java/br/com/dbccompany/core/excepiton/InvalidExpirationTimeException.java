package br.com.dbccompany.core.excepiton;

public class InvalidExpirationTimeException extends RuntimeException {
    public InvalidExpirationTimeException(String message) {
        super(message);
    }
}
