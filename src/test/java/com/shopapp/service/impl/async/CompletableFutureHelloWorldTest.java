package com.shopapp.service.impl.async;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.shopapp.shared.Utils.startTimer;
import static com.shopapp.shared.Utils.timeTaken;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompletableFutureHelloWorldTest {

    HelloWorldService hws = new HelloWorldService();
    CompletableFutureHelloWorld cfhw = new CompletableFutureHelloWorld(hws);

    @Test
    void allOf() {

        //given

        //when
        String result = cfhw.allOf();

        //then
        assertEquals("Hello World", result);
    }

    @Test
    void anyOf() {

        //given

        //when
        String result = cfhw.anyOf();

        //then
        assertEquals("Hello World", result);
    }

    @Test
    void helloWorld() {

        //given
        //when
        CompletableFuture<String> completableFuture = cfhw.helloWorld();

        //then
        completableFuture.thenAccept(s -> {
            //assertEquals("hello world", s);
            assertEquals("HELLO WORLD", s);
        }).join();
    }

    @Test
    void helloWorld_1() {
        //when
        String hw = cfhw.helloWorld_1();

        //then

        assertEquals("HELLO WORLD", hw);
    }

    @Test
    void helloWorld_3_async_calls() {
        //given
        //when
        String hw = cfhw.helloWorld_3_async_calls();
        System.out.println(hw);

        //then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", hw);
    }

    @Test
    void helloWorld_3_async_calls_custom_threadPool() {

        //given
        //when
        String hw = cfhw.helloWorld_3_async_calls_custom_threadPool();

        //then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", hw);

    }

    @Test
    void helloWorld_3_async_calls_custom_threadpool_async() {

        //given
        //when
        String hw = cfhw.helloWorld_3_async_calls_custom_threadpool_async();

        //then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", hw);

    }

    @Test
    void helloWorld_3_async_calls_log() {

        //given
        //when
        String hw = cfhw.helloWorld_3_async_calls_log();

        //then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", hw);

    }

    @Test
    void helloWorld_3_async_calls_log_async() {

        //given
        //when
        String hw = cfhw.helloWorld_3_async_calls_log_async();

        //then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", hw);

    }

    @Test
    void helloWorld_4_async_calls() {

        //given
        //when
        String hw = cfhw.helloWorld_4_async_calls();
        System.out.println(hw);

        //then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE! BYE!", hw);

    }

    @Test
    @Disabled
    void helloWorld_complete() {

        //given
        //when
        startTimer();

        CompletableFuture<String> completableFuture = cfhw.complete("hello world!");

        //then
        completableFuture.thenAccept(s -> {
            //assertEquals("hello world", s);
            assertEquals("12 - HELLO WORLD!", s);
        }).join();
        timeTaken();


    }

    @Test
    void helloWorld_multiple_async_calls() {

        //given
        //when
        String hw = cfhw.helloWorld_multiple_async_calls();

        //then
        assertEquals("HELLO WORLD!", hw);
    }

    @Test
    void helloWorld_thenCompose() {

        //given
        //when
        startTimer();

        CompletableFuture<String> completableFuture = cfhw.helloWorld_thenCompose();

        //then
        completableFuture.thenAccept(s -> {
            //assertEquals("hello world", s);
            assertEquals("HELLO WORLD!", s);
        }).join();
        timeTaken();


    }

    @Test
    void helloWorld_withSize() {

        //given
        //when
        CompletableFuture<String> completableFuture = cfhw.helloWorld_withSize();

        //then
        completableFuture.thenAccept(s -> {
            assertEquals("11 - HELLO WORLD", s);
        }).join();
    }
}