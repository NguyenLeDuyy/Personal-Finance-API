package com.taskmanagement.task_management_api.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskRequest {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Status is required")
    private String status;

    @NotBlank(message = "Priority is required")
    private String priority;

    private LocalDate dueDate;

    @NotNull(message = "User Id is required")
    private Long userId;
}
