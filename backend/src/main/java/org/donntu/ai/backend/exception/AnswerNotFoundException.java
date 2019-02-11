package org.donntu.ai.backend.exception;

public class AnswerNotFoundException extends Exception{
    public AnswerNotFoundException() {
    }

    public AnswerNotFoundException(String message) {
        super(message);
    }
}
