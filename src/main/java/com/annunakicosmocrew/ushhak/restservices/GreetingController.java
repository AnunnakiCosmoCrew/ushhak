package com.annunakicosmocrew.ushhak.restservices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
public class GreetingController {
    private final AtomicLong counter = new AtomicLong();

    public record Greeting(long id, String content) {
    }

    @GetMapping("/areyoualive")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), "Yes, I am alive");
    }
}
