package com.cleanhub.codingchallenge.service;



import com.cleanhub.codingchallenge.domain.Customer;
import com.cleanhub.codingchallenge.domain.Order;
import com.cleanhub.codingchallenge.exceptions.CleanHubServiceException;
import com.cleanhub.codingchallenge.service.impl.CleanHubAPIServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CleanHubAPIServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CleanHubAPIServiceImpl cleanHubAPIService;

    @Test
    public void testFetchAllCustomerOrders_Successful() throws Exception {

        // Mocking active customers response
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("company1", "route1", new Order(UUID.randomUUID(), 10.0)));
        customers.add(new Customer("company2", "route2", new Order(UUID.randomUUID(), 20.0)));
        ResponseEntity<List<Customer>> activeCustomersResponse = new ResponseEntity<>(customers, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(activeCustomersResponse);

        // Mocking customer order responses
        Order order1 = new Order(UUID.randomUUID(), 10.0);
        Order order2 = new Order(UUID.randomUUID(), 20.0);

        ResponseEntity<Order> customerOrderResponse1 = new ResponseEntity<>(order1, HttpStatus.OK);
        ResponseEntity<Order> customerOrderResponse2 = new ResponseEntity<>(order2, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(Order.class)))
                .thenReturn(customerOrderResponse1)
                .thenReturn(customerOrderResponse2);

        // Call the method
        Map<String, Order> result = cleanHubAPIService.fetchAllCustomerOrders();

        // Assertions
        assertEquals(2, result.size());
        assertTrue(result.containsKey("company1"));
        assertTrue(result.containsKey("company2"));
    }

    @Test
    public void testFetchAllCustomerOrders_NoActiveCustomers() {
        // Mocking empty active customers response
        ResponseEntity<List<Customer>> activeCustomersResponse = new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(activeCustomersResponse);

        // Call the method and assert exception
        CleanHubServiceException exception = assertThrows(CleanHubServiceException.class,
                () -> cleanHubAPIService.fetchAllCustomerOrders());
        assertEquals("No active customers found", exception.getMessage());
    }

    @Test
    public void testFetchAllCustomerOrders_NoOrdersForCustomer() {
        // Mocking active customers response
        List<Customer> customers = Collections.singletonList(new Customer("company1", "route1", new Order(UUID.randomUUID(), 10.0)));

        ResponseEntity<List<Customer>> activeCustomersResponse = new ResponseEntity<>(customers, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(activeCustomersResponse);

        // Mocking empty customer order response
        ResponseEntity<Order> customerOrderResponse = new ResponseEntity<>(null, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(Order.class)))
                .thenReturn(customerOrderResponse);

        // Call the method and assert exception
        CleanHubServiceException exception = assertThrows(CleanHubServiceException.class,
                () -> cleanHubAPIService.fetchAllCustomerOrders());
        assertEquals("No orders returned for customer: company1", exception.getMessage());
    }
}
