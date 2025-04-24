package com.example.pollingexample.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
    SUBMITTED("SUBMITTED"),
    RUNNING("RUNNING"),
    SUCCEEDED("SUCCEEDED"),
    FAILED("FAILED");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }


}
