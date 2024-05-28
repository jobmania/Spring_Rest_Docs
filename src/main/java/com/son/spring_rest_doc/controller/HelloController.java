package com.son.spring_rest_doc.controller;

import com.son.spring_rest_doc.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    // https://blog.neonkid.xyz/272
    @GetMapping("/hello")
    public User hello(){
        return new User(1,"엄준식","ppap@naver.com");
    }

}
