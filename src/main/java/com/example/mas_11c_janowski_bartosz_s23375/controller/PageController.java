package com.example.mas_11c_janowski_bartosz_s23375.controller;

import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Instrument;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.MusicStore;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.StorageRoom;
import com.example.mas_11c_janowski_bartosz_s23375.models.withAttribute.Stock;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.StorageRoomRepository;
import com.example.mas_11c_janowski_bartosz_s23375.services.InstrumentService;
import com.example.mas_11c_janowski_bartosz_s23375.services.MusicStoreService;
import com.example.mas_11c_janowski_bartosz_s23375.services.StockService;
import com.example.mas_11c_janowski_bartosz_s23375.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final MusicStoreService musicStoreService;

    private final InstrumentService instrumentService;

    private final StorageService storageService;

    private final StockService stockService;

    /**
     * Displays a list of all music stores.
     *
     * @return the view name for the music stores list.
     */
    @GetMapping("/music_stores")
    public String musicStoreList(Model model){
        model.addAttribute("musicStores", musicStoreService.listOfMusicStores());
        return "musicStoreList";
    }

    /**
     * Displays the details of a music store.
     *
     * @param idStore        ID of the music store, collected from the URL.
     * @param model          model to hold attributes for the view.
     *
     * @return the view name for the music store's details.
     */
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


    /**
     * Displays the stock add form for a music store's storage room.
     *
     * @param idStore        ID of the music store, collected from the URL.
     * @param idStorageRoom  ID of the storage room, collected from the URL.
     * @param success        optional success flag indicating a successful update, collected from the URL.
     * @param model          model to hold attributes for the view.
     *
     * @return the view name for the stock add form.
     */
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

    /**
     * Handles the form submission for adding stock to a music store's storage room.
     *
     * @param idMusicStore  ID of the music store, collected from the URL.
     * @param idStorageRoom ID of the storage room, collected from the URL.
     * @param stockNumber   quantity of stock to be added, passed from the form.
     * @param instrument    instrument to be added to the stock, passed from the form.
     * @param model         model to hold attributes for the view.
     *
     * @return the view name to display after handling the form submission.
     */
    @PostMapping("/music_stores/{idMusicStore}/submitStock/{idStorageRoom}")
    public String handleFormSubmission(@PathVariable Long idMusicStore, @PathVariable Long idStorageRoom,
                                       @RequestParam(required = false) Double stockNumber,
                                       @RequestParam(required = false) Instrument instrument,
                                       Model model)
    {
        // validation
        boolean hasErrors = false;

        // stock number validation
        if(stockNumber == null) {
            hasErrors = true;
            model.addAttribute("stockNumberError", "This field is required");
        } else {
            int intValue = stockNumber.intValue();
            if (stockNumber != intValue || stockNumber < 1) {
                hasErrors = true;
                model.addAttribute("stockNumberError", "Quantity has to be a positive integer");
            }
        }

        // instrument validation
        if(instrument == null) {
            hasErrors = true;
            model.addAttribute("instrumentError", "This field is required");
        }
        else if(!instrumentService.verifyInstrumentExists(instrument)) {
            hasErrors = true;
            model.addAttribute("instrumentError", "Chosen instrument doesn't exist in the system");
        }

        Optional<StorageRoom> relatedStorage = storageService.fetchStorageRoom(idStorageRoom);
        // storage validation
        if(relatedStorage.isEmpty()) {
            hasErrors = true;
            model.addAttribute("unexpectedError", "Storage selected for this operation doesn't exist in the system");
        }


        // Adding previously entered data if there was at least 1 error
        if(hasErrors) {
            int intValue = stockNumber != null ? stockNumber.intValue() : 0;
            model.addAttribute("stockNumber", intValue);
            model.addAttribute("chosenInstrument", instrument);
        }

        model.addAttribute("instruments", instrumentService.listOfInstruments());
        model.addAttribute("idStorageRoom", idStorageRoom);
        model.addAttribute("idMusicStore", idMusicStore);

        if(!hasErrors) {
            boolean successfulUpdate;

            Stock stock = Stock.builder()
                    .stockNumber(stockNumber.intValue())
                    .instrument(instrument)
                    .storageRoom(relatedStorage.get())
                    .build();

            successfulUpdate = stockService.updateStock(stock);

            if(successfulUpdate) {
                return "redirect:/music_stores/" + idMusicStore + "/storage/" + idStorageRoom + "?success=true";
            } else {
                model.addAttribute("unexpectedError", "There was an unexpected error when handling your request");
                return "addItemForm";
            }
        }

        return "addItemForm";
    }
}
