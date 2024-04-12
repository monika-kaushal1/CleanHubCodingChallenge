package com.cleanhub.codingchallenge.service.impl;


import com.cleanhub.codingchallenge.domain.Customer;
import com.cleanhub.codingchallenge.domain.Order;
import com.cleanhub.codingchallenge.exceptions.CleanHubServiceException;
import com.cleanhub.codingchallenge.service.CleanHubAPIService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Wrapper service class for caling CleanHub APIs.
 */
@Service
public class CleanHubAPIServiceImpl implements CleanHubAPIService {

    private final static int RATELIMITER_BATCH_SIZE = 10;

    private final static int RATELIMITER_BATCH_DELAY = 10000;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${cleanhub.api.base.url}")
    private String CLEANHUB_API_BASE_URL;

    @Value("${logo.url.string}")
    private String LOGO_URL_STRING;

    @Value("${customer.order.route.string}")
    private String CUSTOMER_ORDER_ROUTE_STRING;

    /**
     * Returns orders for all the customers using CleanHubMarketplace API.
     *
     * @return Map<String, Order>
     * @throws Exception
     */
    @Override
    public Map<String, Order> fetchAllCustomerOrders() throws Exception {
        Optional<List<Customer>> activeCustomers = fetchActiveCustomers();
        List<Customer> activeCust = activeCustomers.orElseThrow(() -> new CleanHubServiceException("No active customers found"));

        if (CollectionUtils.isEmpty(activeCust)) {
            throw new CleanHubServiceException("No active customers found");
        }
        // Smaller list for managing rate limiting from CleanHub API
        List<List<Customer>> partition = ListUtils.partition(activeCust, RATELIMITER_BATCH_SIZE);

        Map<String, Order> customerOrders = new HashMap<>();

        for (List<Customer> customers : partition) {
            for (Customer customer : customers) {
                Order order = fetchCustomerOrders(customer.getLandingPageRoute()).orElseThrow(
                        () -> new CleanHubServiceException("No orders returned for customer: " + customer.getCompanyName())
                );
                customerOrders.put(customer.getCompanyName(), order);
            }
            try {
                Thread.sleep(RATELIMITER_BATCH_DELAY);
            } catch (Exception ex) {
                throw new CleanHubServiceException("Error while fetching customer orders. Reason:" + ex.getMessage());
            }
        }

        return customerOrders;
    }

    /**
     * fetches all active customers using CleanHub Marketplace API
     *
     * @return <List<Customer>
     */
    private Optional<List<Customer>> fetchActiveCustomers() {
        ResponseEntity<List<Customer>> responseEntity =
                restTemplate.exchange(
                        CLEANHUB_API_BASE_URL + LOGO_URL_STRING,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        }
                );
        List<Customer> users = responseEntity.getBody();
        return Optional.ofNullable(users);
    }

    /**
     * Fetches a customer's order using CleanHub Marketplace API.
     *
     * @param customerRoute
     * @return Order
     */
    private Optional<Order> fetchCustomerOrders(String customerRoute) throws Exception {
        String url =
                CLEANHUB_API_BASE_URL + CUSTOMER_ORDER_ROUTE_STRING + customerRoute;
        ResponseEntity<Order> response = restTemplate.exchange(url, HttpMethod.GET, null, Order.class);

        Order order = response.getBody();
        return Optional.ofNullable(order);
    }
}

