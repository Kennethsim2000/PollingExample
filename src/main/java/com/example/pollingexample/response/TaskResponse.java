package com.example.pollingexample.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponse {
    String requestId;
}
