package com.example.pollingexample.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResultResponse {
    private String requestType;
    private String request;
    private Object response;
    private String requestId;
    private String status;
}
