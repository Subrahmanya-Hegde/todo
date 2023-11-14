package com.hegde.todo.exception.handler;

import com.hegde.todo.dto.ErrorDto;
import com.hegde.todo.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    /**
     * Exception handler for AppException.
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<ErrorDto> handleAppException(AppException appException){
        log.error(appException.getMessage());
        return ResponseEntity.status(appException.getCode())
                .body(ErrorDto.builder()
                        .message(appException.getMessage())
                        .dateTime(LocalDateTime.now())
                        .build());
    }

    /**
     * Exception handler for Exception class. This is the default exception.
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorDto> handleDefaultException(Exception exception){
        log.error(exception.getMessage());
        return ResponseEntity.internalServerError()
                .body(ErrorDto.builder()
                        .message("There was error processing your request")
                        .dateTime(LocalDateTime.now())
                        .build());
    }
}
