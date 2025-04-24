package com.example.pollingexample.Request;

import lombok.Data;

@Data
public class TaskRequest {
    private String requestType;
    private String requestBody;
}
