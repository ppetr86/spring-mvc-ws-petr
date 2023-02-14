package com.appsdeveloperblog.app.ws.service.impl.async;

import java.util.concurrent.CompletableFuture;

import static com.appsdeveloperblog.app.ws.shared.Utils.delay;


public class HelloWorldService {

    public String helloWorld() {
        delay(1000);
        System.out.println("inside helloWorld");
        return "hello world";
    }

    public String hello() {
        delay(1000);
        System.out.println("inside hello");
        return "hello";
    }

    public String world() {
        delay(1000);
        System.out.println("inside world");
        return " world!";
    }

    public CompletableFuture<String> worldFuture(String input) {
        return CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return input + " world!";
        });
    }

}
