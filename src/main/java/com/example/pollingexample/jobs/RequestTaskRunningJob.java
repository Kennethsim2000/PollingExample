package com.example.pollingexample.jobs;

import com.example.pollingexample.Dispatcher.Dispatcher;
import com.example.pollingexample.Request.PriorityTask;
import com.example.pollingexample.enums.StatusEnum;
import com.example.pollingexample.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Component
public class RequestTaskRunningJob implements InitializingBean {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);;

    private final PriorityBlockingQueue<PriorityTask> queue = new PriorityBlockingQueue<>();

    @Autowired
    private List<Dispatcher> dispatcherList;

    @Autowired
    @Lazy
    private TaskService taskService;

    public void submitTask(PriorityTask task) {
        queue.add(task);
    }


    @Override
    public void afterPropertiesSet() {
        // Start a background thread for processing instead of blocking here
        Thread processingThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                PriorityTask task = null;
                try {
                    task = queue.take();
                    final PriorityTask finalTask = task;
                    taskService.setTaskStatus(task.getRequestId(), StatusEnum.RUNNING);
                    Dispatcher matchedDispatcher = dispatcherList.stream()
                            .filter(dispatcher -> finalTask.getRequestType().equals(dispatcher.getRequestType()))
                            .findFirst()
                            .orElse(null);

                    if (matchedDispatcher == null) {
                        System.out.println("No dispatcher found for request type: " + task.getRequestType());
                        continue;
                    }

                    final Dispatcher finalDispatcher = matchedDispatcher;
                    executorService.execute(() -> {
                        try {
                            Object response = finalDispatcher.process(finalTask.getRequestBody());
                            String json = new ObjectMapper().writeValueAsString(response);
                            taskService.writeResult(finalTask.getRequestId(), json);
                        } catch (InterruptedException | JsonProcessingException e) {
                            System.err.println("Error processing task: " + e.getMessage());
                        }
                    });
                } catch (InterruptedException e) {
                    if (task != null) {
                        taskService.setTaskStatus(task.getRequestId(), StatusEnum.FAILED);
                    }
                        Thread.currentThread().interrupt();
                        break;
                } catch (Exception e) {
                    if (task != null) {
                        taskService.setTaskStatus(task.getRequestId(), StatusEnum.FAILED);
                    }
                    System.err.println("Error in task processing loop: " + e.getMessage());
                    // Consider adding a small sleep here to avoid tight loop in case of errors
                }
            }
        });

        processingThread.setDaemon(true);
        processingThread.start();
    }


}

/*We want a RequestTaskRunningJob that constantly pops Tasks from the queue, executes them
*
* We have to define an interface Dispatcher that*/
