package com.burakkocak.multithreading.threadsafety;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

@SpringBootApplication
public class MultithreadingThreadSafetyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MultithreadingThreadSafetyApplication.class, args);
    }

    @Override
    @Order(1)
    public void run(String... args) {

        IncrementValue incrementValue = new IncrementValue();
        incrementValue.multiThreadIncrementExample();

    }

}
