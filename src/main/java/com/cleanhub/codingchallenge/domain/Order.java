package com.cleanhub.codingchallenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    public UUID uuid;
    public double quantity;

    public Order(UUID uuid, double quantity) {
        this.uuid = uuid;
        this.quantity = quantity;
    }
}
