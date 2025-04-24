package com.example.pollingexample.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonResult<T> {
    private int code;
    private String message;
    private T data;
    private boolean success;

    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<>(200, message, data, true);
    }

    public static <T> CommonResult<T> fail(String message, Integer code) {
        return new CommonResult<>(code, message, null, false);
    }
}