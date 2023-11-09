package com.hegde.todo.handler;

import com.hegde.todo.dto.ErrorDto;
import com.hegde.todo.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//TODO : Create common module and move common class or functionality there.
@ControllerAdvice
public class AppExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<ErrorDto> handleAppException(AppException appException){
        return ResponseEntity.status(appException.getCode())
                .body(ErrorDto.builder()
                        .message(appException.getMessage())
                        .build());
    }
}
