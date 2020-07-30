package br.com.dbccompany.core.excepiton;

public class OptionVoteInvalidException extends RuntimeException {
    public OptionVoteInvalidException(final String message) {
        super(message);
    }
}
