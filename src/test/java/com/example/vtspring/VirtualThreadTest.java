package com.example.vtspring;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class VirtualThreadTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void threadFactory() {
        ThreadFactory virtualThreadFactory = Executors.defaultThreadFactory();
        Thread virturalThread = virtualThreadFactory.newThread(() -> {
            log.info("thread test");
        });
        virturalThread.start();
        log.info("main thread");
    }

    @Test
    public void completableFutureTest() {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                log.info("run completable future with virtual thread");
                log.info("Thread : {}", Thread.currentThread().getName());
                log.info("Thread count : {}", Thread.activeCount());
            }, executorService);
            future.join();
            executorService.shutdown();
        }
    }

    @Test
    public void withYield() throws InterruptedException {
        Thread virtualThread = Thread.startVirtualThread(() -> {
            log.info("Virtual thread started");
            try {
                Thread.sleep(Duration.ofSeconds(2));
                log.info("virtual thread woke up");
                Thread.yield();
                log.info("virtual thread completed");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        virtualThread.join();
        log.info("main thread ended");
    }
}
