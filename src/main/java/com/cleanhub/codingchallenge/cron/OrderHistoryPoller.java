package com.cleanhub.codingchallenge.cron;


import com.cleanhub.codingchallenge.domain.Order;
import com.cleanhub.codingchallenge.repository.OrderHistoryRepository;
import com.cleanhub.codingchallenge.service.CleanHubAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class OrderHistoryPoller {

    @Autowired
    private CleanHubAPIService cleanHubAPIService;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    // Scheduled task to periodically fetch data every day at 00:00am.
    @Scheduled(cron = "0 0 0 * * *")
    public void pollCustomerOrderAPI() throws Exception {
        Map<String, Order> orderMap = cleanHubAPIService.fetchAllCustomerOrders();
        for (String key : orderMap.keySet()) {
            orderHistoryRepository.addCustomerOrder(key, orderMap.get(key).getQuantity(), LocalDate.now());
        }
    }
}
