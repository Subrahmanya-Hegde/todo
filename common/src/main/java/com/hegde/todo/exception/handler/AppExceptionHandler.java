package com.hegde.todo.exception.handler;

import com.hegde.todo.dto.ErrorDto;
import com.hegde.todo.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AppExceptionHandler {

    /**
     * Exception handler for AppException.
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<ErrorDto> handleAppException(AppException appException){
        return ResponseEntity.status(appException.getCode())
                .body(ErrorDto.builder()
                        .message(appException.getMessage())
                        .build());
    }

    /**
     * Exception handler for Exception class. This is the default exception.
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorDto> handleDefaultException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto.builder()
                        .message(exception.getMessage())
                        .build());
    }
}
