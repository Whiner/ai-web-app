package org.donntu.ai.backend.exception;

public class AlreadyExistException extends Exception{
    public AlreadyExistException() {
    }

    public AlreadyExistException(String message) {
        super(message);
    }
}
