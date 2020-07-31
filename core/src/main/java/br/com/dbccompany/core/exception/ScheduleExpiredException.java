package br.com.dbccompany.core.exception;

public class ScheduleExpiredException extends RuntimeException {
    public ScheduleExpiredException(final String message) {
        super(message);
    }
}
