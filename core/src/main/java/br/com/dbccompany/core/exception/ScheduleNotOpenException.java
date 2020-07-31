package br.com.dbccompany.core.exception;

public class ScheduleNotOpenException extends RuntimeException {
    public ScheduleNotOpenException(final String message) {
        super(message);
    }
}
