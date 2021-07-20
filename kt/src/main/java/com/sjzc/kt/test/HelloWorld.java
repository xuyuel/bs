package com.sjzc.kt.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloWorld {

	@RequestMapping("")

    public String hello() {

           return "helloworld";

    }
}
