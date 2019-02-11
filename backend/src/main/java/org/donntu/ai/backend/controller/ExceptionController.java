package org.donntu.ai.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.donntu.ai.backend.dto.ErrorCode;
import org.donntu.ai.backend.dto.ErrorInfo;
import org.donntu.ai.backend.exception.ActionMakesCollisionsException;
import org.donntu.ai.backend.exception.AlreadyExistException;
import org.donntu.ai.backend.exception.AnswerNotFoundException;
import org.donntu.ai.backend.exception.NotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    @ExceptionHandler(ActionMakesCollisionsException.class)
    public ResponseEntity<ErrorInfo> actionMakesCollisionsExceptionHandler(ActionMakesCollisionsException ex) {
        log.error("Error: ", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorInfo(ErrorCode.ACTION_MAKES_COLLISIONS.name(), ex.getMessage())
                );
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorInfo> alreadyExistExceptionHandler(AlreadyExistException ex) {
        log.error("Error: ", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorInfo(ErrorCode.ALREADY_EXIST.name(), ex.getMessage())
                );
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public ResponseEntity<ErrorInfo> answerNotFoundExceptionHandler(AnswerNotFoundException ex) {
        log.error("Error: ", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorInfo(ErrorCode.ANSWER_NOT_FOUND.name(), ex.getMessage())
                );
    }

    @ExceptionHandler(NotExistException.class)
    public ResponseEntity<ErrorInfo> notExistExceptionHandler(NotExistException ex) {
        log.error("Error: ", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorInfo(ErrorCode.NOT_EXIST.name(), ex.getMessage())
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(Exception ex) {
        log.error("Error: ", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorInfo(ErrorCode.UNEXPECTED_EXCEPTION.name(), ex.getMessage())
                );
    }
}
