package com.cleanhub.codingchallenge.controller;

import com.cleanhub.codingchallenge.domain.Order;
import com.cleanhub.codingchallenge.service.CleanHubAPIService;
import com.cleanhub.codingchallenge.service.CleanHubHistoryService;
import com.cleanhub.codingchallenge.utils.InputValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private CleanHubAPIService cleanHubAPIService;

    @Autowired
    private CleanHubHistoryService cleanHubHistoryService;

    @GetMapping
    public ResponseEntity getAllCustomerOrder() {
        try {
            Map<String, Order> customerOrderMap = cleanHubAPIService.fetchAllCustomerOrders();
            return new ResponseEntity<>(customerOrderMap, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request: " + e.getMessage());
        }
    }

    @GetMapping("/history")
    public ResponseEntity getTopCustomers(@RequestParam(name = "size", required = false, defaultValue = "10") String size, @RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate) {
        try {
            //validate user inputs.
            InputValidators.validateDates(startDate, endDate);

            List<String> topCustomers = cleanHubHistoryService.getTopCustomers(Integer.parseInt(size), startDate, endDate);
            return new ResponseEntity<>(topCustomers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    /**
     * This API is to insert Test data if needed.
     *
     * @return Map<String, TreeMap < LocalDate, Double>>
     */
    @GetMapping("/addCustomers")
    public ResponseEntity addCustomers() {
        try {
            Map<String, TreeMap<LocalDate, Double>> stringTreeMapMap = cleanHubHistoryService.addCustomers();

            return new ResponseEntity<>(stringTreeMapMap, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}




