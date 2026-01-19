package com.example.shoepalace.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponseDTO<T> {
    private final List<T> items;
    private final long totalItems;
    private final long totalPages;
    private final int currentPage;
    private final int size;
}

