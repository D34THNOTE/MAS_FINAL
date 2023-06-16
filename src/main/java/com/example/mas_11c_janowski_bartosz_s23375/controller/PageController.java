package com.example.mas_11c_janowski_bartosz_s23375.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/music_stores")
    public String musicList(){
        return "musicList";
    }

}
