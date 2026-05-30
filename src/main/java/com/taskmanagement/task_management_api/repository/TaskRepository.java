package com.taskmanagement.task_management_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanagement.task_management_api.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
}
