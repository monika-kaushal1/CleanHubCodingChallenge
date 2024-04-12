package com.cleanhub.codingchallenge.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    private String companyName;
    private String landingPageRoute;
    private Order order;

    public Customer(String companyName, String landingPageRoute, Order order) {
        this.companyName = companyName;
        this.landingPageRoute = landingPageRoute;
        this.order = order;
    }
}

