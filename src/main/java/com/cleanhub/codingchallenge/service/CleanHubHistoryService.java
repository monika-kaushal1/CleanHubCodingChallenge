package com.cleanhub.codingchallenge.service;

import com.cleanhub.codingchallenge.exceptions.CleanHubServiceException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * To access clean hub order history.
 */
public interface CleanHubHistoryService {

    /**
     * fetches Top Customers for given start and end dates
     * @input leaderboardSize, startDate, endDate
     * @return List of Customer Names.
     * @throws Exception
     */
    List<String> getTopCustomers(int leaderboardSize, String startDate, String endDate) throws CleanHubServiceException;

    /**
     * helper service to add 5 dummy customers order details for testing.
     */
    Map<String, TreeMap<LocalDate, Double>> addCustomers();

}
