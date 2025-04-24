package com.example.pollingexample.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriorityTask implements Comparable<PriorityTask> {

    private String requestType;
    private String requestBody;
    private String requestId;
    private int priority;

    @Override
    public int compareTo(PriorityTask o) {
        return 0;
    }
}
