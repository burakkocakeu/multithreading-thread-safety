package com.burakkocak.multithreading.threadsafety;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * For Thread safety
 * 1. use public synchronized void increaseValue() {....}
 * 2. use AtomicInteger atomicValue = new AtomicInteger; and atomicValue.incrementAndGet();
 * 3. HashMap (NOT Thread-safe) -> ConcurrentHashMap (Thread-safe)
 * 4. StringBuilder (NOT Thread-safe) -> StringBuffer (Thread-safe)
 */
@Slf4j
public
class IncrementValue {
    int value;

    AtomicInteger atomicValue = new AtomicInteger();

    public void resetValue() {
        value = 0;
    }

    public void increaseValueNonThreadSafely() {
        value++;
    }

    public synchronized void increaseValueThreadSafely() {
        value++;
    }

    public void increaseValueThreadSafelyAtomic() {
        atomicValue.incrementAndGet();
    }

    public void multiThreadIncrementExample() {
        // a. Traditional way of defining a new Runnable in a new Thread.
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                IntStream.range(0, 1000).forEach(e -> increaseValueThreadSafely());
            }
        });

        // b. Lambda expression of defining a new Runnable in a new Thread.
        Thread t2 = new Thread(() -> IntStream.range(0,1000).forEach(e -> increaseValueThreadSafely()));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Thread-safe (synchronized) value is: " + value);

        t1 = new Thread(() -> IntStream.range(0,1000).forEach(e -> increaseValueThreadSafelyAtomic()));
        t2 = new Thread(() -> IntStream.range(0,1000).forEach(e -> increaseValueThreadSafelyAtomic()));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Thread-safe (Atomic) value is: " + atomicValue);

        resetValue();

        t1 = new Thread(() -> IntStream.range(0,1000).forEach(e -> increaseValueNonThreadSafely()));
        t2 = new Thread(() -> IntStream.range(0,1000).forEach(e -> increaseValueNonThreadSafely()));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Non Thread-safe value is: " + value);

    }
}
