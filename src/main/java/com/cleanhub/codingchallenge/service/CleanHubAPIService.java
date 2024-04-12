package com.cleanhub.codingchallenge.service;

import com.cleanhub.codingchallenge.domain.Order;

import java.util.Map;

/**
 * To access clean hub APIs
 */
public interface CleanHubAPIService {

    /**
     * fetches Orders for all active customers
     * @return Map of customer id and order details.
     * @throws Exception
     */
    Map<String, Order> fetchAllCustomerOrders() throws Exception;

}