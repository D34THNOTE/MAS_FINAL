package com.example.mas_11c_janowski_bartosz_s23375.models.Store;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class StoreAddress {
    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "Street number is required")
    private String streetNumber;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Zip code is required")
    private String zipCode;

    @NotBlank(message = "Country is required")
    private String country;
}
