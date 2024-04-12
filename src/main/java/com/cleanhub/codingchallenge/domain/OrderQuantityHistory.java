package com.cleanhub.codingchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderQuantityHistory {
    private String customerName;
    private LocalDate date;
    private Double quantity;
}
