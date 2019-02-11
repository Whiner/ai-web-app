package org.donntu.ai.backend.exception;

public class NotExistException extends Exception{
    public NotExistException() {
    }

    public NotExistException(String message) {
        super(message);
    }
}
