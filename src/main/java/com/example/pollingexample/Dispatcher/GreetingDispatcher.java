package com.example.pollingexample.Dispatcher;

import com.example.pollingexample.enums.RequestEnum;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class GreetingDispatcher implements Dispatcher{

    @Override
    public Object process(Object requestBody) throws InterruptedException {
        String person = (String) requestBody;
        TimeUnit.SECONDS.sleep(20);
        return "hello " + person;
    }

    @Override
    public String getRequestType() {
        return RequestEnum.OBTAIN_GREETING.getTaskName();
    }

}

/*process method most of the time requires the requestBody that is present in the task */

