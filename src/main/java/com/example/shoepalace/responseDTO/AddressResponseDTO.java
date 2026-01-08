package com.example.shoepalace.responseDTO;

import lombok.Data;

@Data
public class AddressResponseDTO {
    private String id;
    private String label;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phoneNumber;
    private boolean isDefault;
}
