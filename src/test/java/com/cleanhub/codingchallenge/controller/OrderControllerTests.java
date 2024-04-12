package com.cleanhub.codingchallenge.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTests {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testGetTopCustomers() {
        String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        restTemplate.getForEntity("http://localhost:" + port + "/orders/addCustomers", String.class);
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/orders/history?size=1&startDate=" + startDate + "&endDate=" + endDate, List.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // since test data uses Random function to generate quantities, we cannot assert
        // the result Customers, so validating size of the response.
        Assertions.assertThat(Objects.requireNonNull(responseEntity.getBody()).size()).isEqualTo(1);
    }

}
