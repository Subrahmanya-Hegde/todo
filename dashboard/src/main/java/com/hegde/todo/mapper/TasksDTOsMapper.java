package com.hegde.todo.mapper;

import com.hegde.todo.domain.Task;
import com.hegde.todo.dto.response.ListTasksResponse;
import com.hegde.todo.dto.response.TaskCreationResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TasksDTOsMapper {

    TaskCreationResponse toTaskCreationResponse(Task task);

    ListTasksResponse toListTasksResponse(Task task);
}
