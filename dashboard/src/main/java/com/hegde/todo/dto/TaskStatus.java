package com.hegde.todo.dto;

import com.hegde.todo.exception.AppException;
import org.springframework.http.HttpStatus;
import org.yaml.snakeyaml.util.EnumUtils;

public enum TaskStatus {
    CREATED,
    TODO,
    INPROGRESS,
    BLOCKER,
    DONE;

    public static TaskStatus getTaskStatus(String taskStatusStr){
        try {
            return EnumUtils.findEnumInsensitiveCase(TaskStatus.class,taskStatusStr);
        }catch (IllegalArgumentException exception){
            throw new AppException("Invalid Task status", HttpStatus.BAD_REQUEST);
        }
    }
}
