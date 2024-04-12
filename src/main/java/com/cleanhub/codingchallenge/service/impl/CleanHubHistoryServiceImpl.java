package com.cleanhub.codingchallenge.service.impl;

import com.cleanhub.codingchallenge.exceptions.CleanHubServiceException;
import com.cleanhub.codingchallenge.repository.OrderHistoryRepository;
import com.cleanhub.codingchallenge.service.CleanHubHistoryService;
import com.cleanhub.codingchallenge.domain.OrderQuantityHistory;
import com.cleanhub.codingchallenge.domain.CustomerOrderDiff;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CleanHubHistoryServiceImpl implements CleanHubHistoryService {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Override
    public List<String> getTopCustomers(int leaderboardSize, String startDate, String endDate) throws CleanHubServiceException {

        Map<String, Double> customerOrderMinMap = orderHistoryRepository.getCustomerQuantityByDateGt(startDate).stream()
                .collect(Collectors.toMap(OrderQuantityHistory::getCustomerName, OrderQuantityHistory::getQuantity));

        Map<String, Double> customerOrderMaxMap = orderHistoryRepository.getCustomerQuantityByDateLt(endDate).stream()
                .collect(Collectors.toMap(OrderQuantityHistory::getCustomerName, OrderQuantityHistory::getQuantity));

        List<CustomerOrderDiff> customerOrderDiffs = customerOrderMinMap.entrySet().stream().map(c -> new CustomerOrderDiff(c.getKey(),
                        (customerOrderMaxMap.getOrDefault(c.getKey(), (double) 0) - c.getValue())))
                .toList();

        if (CollectionUtils.isEmpty(customerOrderDiffs)) {
            throw new CleanHubServiceException("No customers for this date range");
        }

        PriorityQueue<CustomerOrderDiff> es = new PriorityQueue<>((c1, c2) -> c1.getOrderDiff() <= c2.getOrderDiff() ? 1 : -1);

        es.addAll(customerOrderDiffs);

        return IntStream.range(0, Math.min(leaderboardSize, es.size()))
                .mapToObj(i -> Objects.requireNonNull(es.poll()).getCustomerId()).collect(Collectors.toList());
    }

    /**
     * This function will insert dummy values for testing in the DB.
     */
    @Override
    public Map<String, TreeMap<LocalDate, Double>> addCustomers() {

        Random random = new Random();
        // Iterate over 5 customers
        for (int i = 1; i <= 5; i++) {
            String customerName = "cust" + i;
            double quantity = 10.0; // Initial quantity
            LocalDate currentDate = LocalDate.now();
            // Iterate over dates
            for (int j = 0; j < 5; j++) {
                // Generate a random increment in quantity
                double randomIncrement = random.nextDouble() * 10.0; // Random value between 0 and 10
                quantity += randomIncrement;
                // Add order for the current customer with increasing date and quantity
                orderHistoryRepository.addCustomerOrder(customerName, quantity, currentDate);
                // Increment date for the next iteration
                currentDate = currentDate.plusDays(1);
            }
        }

        return this.orderHistoryRepository.getAllData();
    }

}
