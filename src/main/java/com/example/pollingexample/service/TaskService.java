package com.example.pollingexample.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pollingexample.enums.StatusEnum;
import com.example.pollingexample.model.Task;

import java.util.List;

public interface TaskService extends IService<Task> {
    Task addTask(String requestType, String requestBody);
    void writeResult(String requestId, String res);
    public void setTaskStatus(String requestId, StatusEnum status);
    List<Task> getTasks();
    Task getById(String requestId);
}
