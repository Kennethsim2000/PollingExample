package com.example.pollingexample.enums;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;

@Getter
public enum RequestEnum {

    OBTAIN_GREETING("OBTAIN_GREETING", String.class, String.class),
    OBTAIN_USER("OBTAIN_USER", String.class, Page.class);


    private String taskName;
    private Class<?> requestClass;
    private Class<?> responseClass;

    RequestEnum(String taskName, Class<?> requestClass, Class<?> responseClass) {
        this.taskName = taskName;
        this.requestClass = requestClass;
        this.responseClass = responseClass;
    }

    public static RequestEnum findByTaskName(String taskName) {
        for(RequestEnum requestEnum : RequestEnum.values()) {
            if(requestEnum.getTaskName().equals(taskName)) {
                return  requestEnum;
            }
        }
        return null;
    }

}
