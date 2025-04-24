package com.example.pollingexample.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pollingexample.Request.PriorityTask;
import com.example.pollingexample.enums.StatusEnum;
import com.example.pollingexample.jobs.RequestTaskRunningJob;
import com.example.pollingexample.mapper.TaskMapper;
import com.example.pollingexample.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;



@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService  {
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private RequestTaskRunningJob requestTaskRunningJob;

    @Override
    public Task addTask(String requestType, String requestBody) {
        Task task = new Task();
        task.setStatus(StatusEnum.SUBMITTED.getStatus());
        task.setRequest(requestBody);
        task.setRequestType(requestType);
        task.setRequestId(UUID.randomUUID().toString());
        task.setCreateTime(new Date());
        task.setUpdateTime(new Date());
        this.save(task);
        PriorityTask priorityTask = PriorityTask.builder()
                .priority(2)
                .requestBody(requestBody)
                .requestType(requestType)
                .requestId(task.getRequestId())
                .build();
        requestTaskRunningJob.submitTask(priorityTask);
        return task;
    }

    @Override
    public void setTaskStatus(String requestId, StatusEnum status) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("request_id", requestId);
        Task task = this.getOne(wrapper);
        task.setStatus(status.getStatus());
        this.updateById(task);
    }

    @Override
    public List<Task> getTasks() {
        return this.list();
    }

    @Override
    public Task getById(String requestId) {
        return taskMapper.selectById(requestId);
    }

    @Override
    public void writeResult(String requestId, String res) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("request_id", requestId);
        Task task = this.getOne(wrapper);
        task.setResponse(res);
        task.setStatus(StatusEnum.SUCCEEDED.getStatus());
        this.updateById(task);
    }
}
