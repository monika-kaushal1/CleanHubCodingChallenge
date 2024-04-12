package com.cleanhub.codingchallenge.repository;

import com.cleanhub.codingchallenge.domain.OrderQuantityHistory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface OrderHistoryRepository {

    /**
     * Add a  new order history details.
     *
     * @param customerName
     * @param quantity
     */
    void addCustomerOrder(String customerName, double quantity, LocalDate date);

    List<OrderQuantityHistory> getCustomerQuantityByDateGt(String startDate);

    List<OrderQuantityHistory> getCustomerQuantityByDateLt(String endDate);

    /**
     * To get all customers order map.
     */
    Map<String, TreeMap<LocalDate, Double>> getAllData();
}
