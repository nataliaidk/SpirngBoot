package com.example.kotki;

// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class Hello {
    public String run() {
        return "Hello ! I'm, running";
    }
}