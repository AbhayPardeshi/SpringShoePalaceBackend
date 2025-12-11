package com.example.shoepalace.model_internal.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String id;

    @NotBlank
    private String label;          // "Home", "Office", "Parents"

    @NotBlank
    private String line1;

    private String line2;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String country;

    private String phoneNumber;

    private boolean isDefault = false;

    private Instant addedAt = Instant.now();
    private Instant updatedAt;
}
