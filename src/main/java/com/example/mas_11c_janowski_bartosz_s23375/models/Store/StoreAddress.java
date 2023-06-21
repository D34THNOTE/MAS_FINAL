package com.example.mas_11c_janowski_bartosz_s23375.models.Store;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreAddress {
    @NotBlank(message = "Street is required")
    @Size(min = 2, max = 255)
    private String street;

    @NotBlank(message = "Street number is required")
    @Size(min = 2, max = 255)
    private String streetNumber;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 255)
    private String city;

    @NotBlank(message = "Zip code is required")
    @Size(min = 2, max = 255)
    private String zipCode;

    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 255)
    private String country;
}
