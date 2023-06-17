package com.example.mas_11c_janowski_bartosz_s23375.controller;

import com.example.mas_11c_janowski_bartosz_s23375.services.MusicStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final MusicStoreService musicStoreService;

    @GetMapping("/music_stores")
    public String musicList(Model model){
        model.addAttribute("musicStores", musicStoreService.listOfMusicStores());
        return "musicList";
    }

}
