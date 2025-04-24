package com.example.pollingexample.controller;

import com.example.pollingexample.Request.TaskRequest;
import com.example.pollingexample.Request.TaskResultRequest;
import com.example.pollingexample.config.CommonResult;
import com.example.pollingexample.enums.RequestEnum;
import com.example.pollingexample.model.Task;
import com.example.pollingexample.response.TaskResponse;
import com.example.pollingexample.response.TaskResultResponse;
import com.example.pollingexample.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/task")
public class TaskController  {

    @Autowired
    private TaskService taskService;

    @PostMapping()
    @Operation(summary = "Add a new task", description = "Creates a new task")
    public CommonResult<TaskResponse> addTask(@RequestBody TaskRequest request) {
        Task task = taskService.addTask(request.getRequestType(), request.getRequestBody());
        TaskResponse response = TaskResponse.builder().requestId(task.getRequestId()).build();
        return CommonResult.success(response, "Task successfully added");
    }

    @GetMapping(value = "/result")
    @Operation(summary = "Get task result", description = "Get task result")
    public CommonResult<TaskResultResponse> getTaskResult(TaskResultRequest request) {
        Task task = taskService.getById(request.getRequestId());
        RequestEnum reqEnum = RequestEnum.findByTaskName(task.getRequestType());
        ObjectMapper mapper = new ObjectMapper();
        try {
            TaskResultResponse response = TaskResultResponse
                    .builder()
                    .requestId(task.getRequestId())
                    .response(mapper.readValue(task.getResponse(), reqEnum.getResponseClass()))
                    .status(task.getStatus())
                    .requestType(task.getRequestType())
                    .request(task.getRequest())
                    .build();
            return CommonResult.success(response, "Task result successfully retrieved");
        } catch (JsonProcessingException e) {
            return CommonResult.fail(e.getMessage(), 400);
        }
    }

    @GetMapping()
    @Operation(summary = "Get all tasks", description = "Fetches all the tasks")
    public CommonResult<List<TaskResultResponse>> getAllTasks() {
        List<Task> tasks = taskService.getTasks();
        List<TaskResultResponse> res = new ArrayList<>();
        tasks.forEach(task-> {
            TaskResultResponse resObj = TaskResultResponse.builder()
                    .request(task.getRequest())
                    .requestType(task.getRequestType())
                    .status(task.getStatus())
                    .response(task.getResponse())
                    .requestId(task.getRequestId())
                    .build();
            res.add(resObj);
        });
        return CommonResult.success(res, "All tasks retrieved");
    }
}
