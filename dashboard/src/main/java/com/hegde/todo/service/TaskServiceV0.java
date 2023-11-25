package com.hegde.todo.service;

import com.hegde.todo.domain.Task;
import com.hegde.todo.domain.User;
import com.hegde.todo.domain.UserPrincipal;
import com.hegde.todo.dto.ETA;
import com.hegde.todo.dto.PageResponse;
import com.hegde.todo.dto.TaskStatus;
import com.hegde.todo.dto.request.TaskCreateRequest;
import com.hegde.todo.dto.request.UpdateTaskRequest;
import com.hegde.todo.dto.response.ListTasksResponse;
import com.hegde.todo.dto.response.TaskCreationResponse;
import com.hegde.todo.dto.response.ViewTaskResponse;
import com.hegde.todo.exception.AppException;
import com.hegde.todo.msq.producer.Producer;
import com.hegde.todo.mapper.TasksDTOsMapper;
import com.hegde.todo.repository.TaskRepository;
import com.hegde.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceV0 implements TaskService {

    private final TaskRepository taskRepository;
    private final TasksDTOsMapper tasksDTOsMapper;
    private final UserRepository userRepository;
    private final Producer producer;

    @Override
    public TaskCreationResponse createTask(TaskCreateRequest taskCreateRequest) {
        Task task = taskRepository.save(getTask(taskCreateRequest));
        producer.send("tasks-topic", task.toString());
        return TaskCreationResponse.builder()
                .id(task.getId())
                .taskTitle(task.getTitle())
                .build();
    }

    @Override
    public PageResponse<ListTasksResponse> listTasks(UserPrincipal userPrincipal, int pageNo, int pageSize) {
        User currentUser = getUser(userPrincipal.getUsername());
        Page<Task> listTasksResponsePage = taskRepository.findByCreatedByOrAssignedTo(currentUser.getUuid(), currentUser,
                PageRequest.of(pageNo, pageSize));
        return new PageResponse<>(!listTasksResponsePage.isLast(),
                listTasksResponsePage.getTotalElements(), pageNo, pageSize,
                convertToListTasksResponse(listTasksResponsePage.getContent()));
    }

    @Override
    public ViewTaskResponse viewTicket(long taskId, UserPrincipal userPrincipal) {
        User currentUser = getUser(userPrincipal.getUsername());
        Task task = taskRepository.findByIdAndCreatedByOrAssignedTo(taskId, currentUser.getUuid(), currentUser)
                .orElseThrow(() -> new AppException("Task doesn't belong to the user", HttpStatus.BAD_REQUEST));
        return tasksDTOsMapper.toViewTaskResponse(task, userRepository);
    }

    @Override
    public List<String> listTaskStatusFlow() {
        return Arrays.stream(TaskStatus.values()).map(String::valueOf).toList();
    }

    @Override
    public ViewTaskResponse updateTask(long taskId, UpdateTaskRequest updateTaskRequest, UserPrincipal userPrincipal) {
        User currentUser = getUser(userPrincipal.getUsername());
        Task task = taskRepository.findByIdAndCreatedByOrAssignedTo(taskId, currentUser.getUuid(), currentUser)
                .orElseThrow(() -> new AppException("Task doesn't belong to the user", HttpStatus.BAD_REQUEST));
        task.setStatus(TaskStatus.getTaskStatus(updateTaskRequest.status()));
        taskRepository.save(task);
        return tasksDTOsMapper.toViewTaskResponse(task, userRepository);
    }

    private LocalDateTime getEta(ETA eta) {
        return LocalDateTime.now().plusDays(eta.days()).plusHours(eta.hrs()).plusMinutes(eta.min());
    }

    //TODO - Move this to TasksDTOsMapper if possible
    private Task getTask(TaskCreateRequest taskCreateRequest) {
        User createdBy = getUser(taskCreateRequest.createdBy());
        return Task.builder()
                .description(taskCreateRequest.description())
                .status(TaskStatus.CREATED)
                .assignedTo(getUser(taskCreateRequest.assignedTo()))
                .createdBy(createdBy.getUuid())
                .estimatedCompletionTime(getEta(taskCreateRequest.eta()))
                .title(taskCreateRequest.title())
                //TODO - Update this to the current logged-in user.
                .updatedBy(createdBy.getUuid())
                .build();
    }

    //TODO - Caching
    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.BAD_REQUEST));
    }

    private List<ListTasksResponse> convertToListTasksResponse(List<Task> tasks) {
        return tasks.stream().map(tasksDTOsMapper::toListTasksResponse).toList();
    }
}
