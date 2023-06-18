package com.example.mas_11c_janowski_bartosz_s23375.controller;

import com.example.mas_11c_janowski_bartosz_s23375.models.Store.MusicStore;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.StorageRoom;
import com.example.mas_11c_janowski_bartosz_s23375.models.withAttribute.Stock;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.StorageRoomRepository;
import com.example.mas_11c_janowski_bartosz_s23375.services.InstrumentService;
import com.example.mas_11c_janowski_bartosz_s23375.services.MusicStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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


    @GetMapping("/music_stores/{idStore}/storage/{idStorageRoom}")
    public String stockAddForm(@PathVariable Long idStore, @PathVariable Long idStorageRoom, Model model){
        model.addAttribute("instruments", instrumentService.listOfInstruments());
        model.addAttribute("idStorageRoom", idStorageRoom);
        model.addAttribute("idMusicStore", idStore);
        return "addItemForm";
    }

    @PostMapping("/music_stores/{idMusicStore}/submitStock/{idStorageRoom}")
    public String handleFormSubmission(@PathVariable Long idMusicStore, @PathVariable Long idStorageRoom,
                                       @ModelAttribute("stock") Stock stock, Model model)
    {
        // validation
        boolean hasErrors = false;

        if(stock.getStockNumber() == null) {
            hasErrors = true;
            model.addAttribute("stockNumberError", "This field is required");
        }
        else if(stock.getStockNumber() < 1) {
            hasErrors = true;
            model.addAttribute("stockNumberError", "Quantity has to be a positive integer");
        }

        if(stock.getInstrument() == null) {
            hasErrors = true;
            model.addAttribute("instrumentError", "This field is required");
        }
        else if(!instrumentService.verifyInstrumentExists(stock.getInstrument())) {
            hasErrors = true;
            model.addAttribute("instrumentError", "Chosen instrument doesn't exist in the system");
        }


        model.addAttribute("stock", stock);

        model.addAttribute("instruments", instrumentService.listOfInstruments());
        model.addAttribute("idStorageRoom", idStorageRoom);
        model.addAttribute("idMusicStore", idMusicStore);

//        if(true) {
//            return "redirect:/music_stores/" + idMusicStore + "/storage/" + idStorageRoom + "?success=true";
//        }

        return "addItemForm";
    }
}
