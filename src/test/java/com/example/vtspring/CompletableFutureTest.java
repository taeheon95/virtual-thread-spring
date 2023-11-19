package com.example.vtspring;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class CompletableFutureTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void completableFutureTest() {
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var task = CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(Duration.ofMillis(10));
                    log.info("task");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            futureList.add(task);
        }

        log.info("use of memory : {}mb", Runtime.getRuntime().totalMemory() / 1024 / 1024);
        CompletableFuture<Void> future = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
        log.info("end of main thread");
        future.join();
    }

    @Test
    public void virtualCompletableFutureTest() {
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var task = CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(Duration.ofMillis(10));
                    log.info("task");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, Executors.newVirtualThreadPerTaskExecutor());
            futureList.add(task);
        }

        log.info("use of memory : {}mb", Runtime.getRuntime().totalMemory() / 1024 / 1024);
        CompletableFuture<Void> future = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
        log.info("end of main thread");
        future.join();
    }
}
