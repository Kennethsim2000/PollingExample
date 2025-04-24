package com.example.pollingexample.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "request_task")
public class Task {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String requestType;

    private String request;

    private String response;

    /**
     * SUBMITTED, ACCEPTED, RUNNING, SUCCEEDED, FAILED
     */
    private String status;

    private Date createTime;

    private Date updateTime;

    private String requestId;
}


