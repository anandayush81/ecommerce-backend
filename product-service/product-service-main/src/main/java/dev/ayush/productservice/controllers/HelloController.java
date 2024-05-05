package dev.ayush.productservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Created for testing purposes
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/say/{name}/{times}")
    public String sayHello(@PathVariable("name") String name,
                           @PathVariable("times") int times) {
        String answer = "";

        for (int i = 0; i < times; ++i) {
            answer += "Hello " + name;
            answer += "<br>";
        }

        return answer;
    }
}
