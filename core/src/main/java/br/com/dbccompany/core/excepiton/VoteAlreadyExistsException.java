package br.com.dbccompany.core.excepiton;

public class VoteAlreadyExistsException extends RuntimeException {
    public VoteAlreadyExistsException(final String message) {
        super(message);
    }
}
