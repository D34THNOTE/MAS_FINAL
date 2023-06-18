package com.example.mas_11c_janowski_bartosz_s23375.controller;

import com.example.mas_11c_janowski_bartosz_s23375.models.Store.MusicStore;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.StorageRoom;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.StorageRoomRepository;
import com.example.mas_11c_janowski_bartosz_s23375.services.InstrumentService;
import com.example.mas_11c_janowski_bartosz_s23375.services.MusicStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final MusicStoreService musicStoreService;

    private final InstrumentService instrumentService;

    @GetMapping("/music_stores")
    public String musicStoreList(Model model){
        model.addAttribute("musicStores", musicStoreService.listOfMusicStores());
        return "musicStoreList";
    }

    @GetMapping("/music_stores/{idStore}")
    public String musicStoreDetails(@PathVariable Long idStore, Model model){
        Optional<MusicStore> fetchMusicStore = musicStoreService.musicStoreDetails(idStore);

        if(fetchMusicStore.isPresent()) {
            model.addAttribute("musicStore", fetchMusicStore.get());

            List<StorageRoom> musicStoreStorages = musicStoreService.musicStoreAssociatedStorages(fetchMusicStore.get());
            model.addAttribute("storages", musicStoreStorages);
        }

        return "musicStoreDetails";
    }

    @SuppressWarnings("OptionalIsPresent")
    @GetMapping("/music_stores/{idStore}/storage/{idStorageRoom}")
    public String stockAddForm(@PathVariable Long idStore, @PathVariable Long idStorageRoom, Model model){
        Optional<MusicStore> fetchMusicStore = musicStoreService.musicStoreDetails(idStore);

        if(fetchMusicStore.isPresent()) {
            model.addAttribute("musicStore", fetchMusicStore.get());
        }
        model.addAttribute("instruments", instrumentService.listOfInstruments());
        return "addItemForm";
    }

//    @GetMapping("/submitStock")
//    public String handleFormSubmission() {
//
//    }
}
