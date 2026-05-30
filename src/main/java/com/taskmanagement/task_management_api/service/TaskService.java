package com.taskmanagement.task_management_api.service;

import java.util.List;
import com.taskmanagement.task_management_api.dto.request.TaskRequest;
import com.taskmanagement.task_management_api.dto.response.TaskResponse;

public interface TaskService {
    public TaskResponse createTask(TaskRequest request);

    public List<TaskResponse> findAllTasks();

    public TaskResponse updateTask(Long id, TaskRequest request);

    public void deleteTask(Long id);
}
