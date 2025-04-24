package com.example.pollingexample.Dispatcher;

public interface Dispatcher {

    public Object process(Object requestBody) throws InterruptedException;

    String getRequestType();
}
