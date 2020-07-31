package br.com.dbccompany.core.exception;

public class VoteAlreadyExistsException extends RuntimeException {
    public VoteAlreadyExistsException(final String message) {
        super(message);
    }
}
