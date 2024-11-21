package ru.danchenski.TaskManagementSystem.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskErrorResponse {
    private String message;
    private long timestamp;
}
