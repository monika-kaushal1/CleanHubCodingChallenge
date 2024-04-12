package com.cleanhub.codingchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderDiff {

    private String customerId;
    private Double orderDiff;
}
