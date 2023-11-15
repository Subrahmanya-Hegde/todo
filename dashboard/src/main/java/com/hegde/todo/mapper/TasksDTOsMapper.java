package com.hegde.todo.mapper;

import com.hegde.todo.domain.Task;
import com.hegde.todo.domain.User;
import com.hegde.todo.dto.UserDto;
import com.hegde.todo.dto.response.ListTasksResponse;
import com.hegde.todo.dto.response.TaskCreationResponse;
import com.hegde.todo.dto.response.ViewTaskResponse;
import com.hegde.todo.exception.AppException;
import com.hegde.todo.repository.UserRepository;
import org.mapstruct.*;
import org.springframework.http.HttpStatus;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TasksDTOsMapper {

    TaskCreationResponse toTaskCreationResponse(Task task);

    ListTasksResponse toListTasksResponse(Task task);

    ViewTaskResponse toViewTaskResponse(Task task, @Context UserRepository userRepository);

    default UserDto map(User user){
        return UserDto.builder()
                .email(user.getEmail())
                .userName(user.getUserName())
                .userId(user.getUuid())
                .build();
    }

    default UserDto map(String uuid, @Context UserRepository userRepository){
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new AppException("User Not found", HttpStatus.BAD_REQUEST));
        return UserDto.builder()
                .email(user.getEmail())
                .userName(user.getUserName())
                .userId(user.getUuid())
                .build();
    }
}
