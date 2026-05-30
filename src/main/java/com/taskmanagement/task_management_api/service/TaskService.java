package com.taskmanagement.task_management_api.service;

import com.taskmanagement.task_management_api.dto.request.TaskRequest;
import com.taskmanagement.task_management_api.dto.response.TaskResponse;

public interface TaskService {
    public TaskResponse createTask(TaskRequest request);

    public TaskResponse findAllTasks(TaskRequest request);

    public TaskResponse updateTask(TaskRequest request);

    public TaskResponse deleteTask(TaskRequest request);
}
