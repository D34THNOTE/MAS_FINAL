package com.example.mas_11c_janowski_bartosz_s23375.controller;

import com.example.mas_11c_janowski_bartosz_s23375.models.Store.MusicStore;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.StorageRoom;
import com.example.mas_11c_janowski_bartosz_s23375.models.withAttribute.Stock;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.StorageRoomRepository;
import com.example.mas_11c_janowski_bartosz_s23375.services.InstrumentService;
import com.example.mas_11c_janowski_bartosz_s23375.services.MusicStoreService;
import com.example.mas_11c_janowski_bartosz_s23375.services.StockService;
import com.example.mas_11c_janowski_bartosz_s23375.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final MusicStoreService musicStoreService;

    private final InstrumentService instrumentService;

    private final StorageService storageService;

    private final StockService stockService;

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
    public String stockAddForm(@PathVariable Long idStore, @PathVariable Long idStorageRoom,
                               @RequestParam(value = "success", required = false) String success, Model model)
    {
        model.addAttribute("instruments", instrumentService.listOfInstruments());
        model.addAttribute("idStorageRoom", idStorageRoom);
        model.addAttribute("idMusicStore", idStore);

        if(success != null) {
            model.addAttribute("success", "Storage successfully updated!");
        }

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

        // Adding previously entered data if there was at least 1 error
        if(hasErrors) {
            model.addAttribute("stock", stock);
        }

        model.addAttribute("instruments", instrumentService.listOfInstruments());
        model.addAttribute("idStorageRoom", idStorageRoom);
        model.addAttribute("idMusicStore", idMusicStore);

        if(!hasErrors) {
            boolean successfulUpdate;
            Optional<StorageRoom> relatedStorage = storageService.fetchStorageRoom(idStorageRoom);
            relatedStorage.ifPresent(stock::setStorageRoom);

            if(stockService.doesRecordExist(stock)) {
                successfulUpdate = stockService.updateStock(stock);
            } else {
                successfulUpdate = stockService.insertNewStock(stock);
            }

            if(successfulUpdate) {
                return "redirect:/music_stores/" + idMusicStore + "/storage/" + idStorageRoom + "?success=true";
            } else {
                return "addItemForm";
            }
        }

        return "addItemForm";
    }
}
