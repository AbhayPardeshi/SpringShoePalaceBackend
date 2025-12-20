package com.example.shoepalace.embedded.product;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Varients {
    private List<Variant> variantList = new ArrayList<>();
}
