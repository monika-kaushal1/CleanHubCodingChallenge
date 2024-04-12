package com.cleanhub.codingchallenge.db;

import com.cleanhub.codingchallenge.domain.OrderQuantityHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustumerDBTest {

    private CustumerDB custumerDB;

    @BeforeEach
    void setUp() {
        custumerDB = CustumerDB.getInstance();
    }

    @Test
    void testAddCustQuantity_AddsQuantityForCustomer() {
        // Given
        String companyName = "ABC Corp";
        double quantity = 100.0;
        LocalDate date = LocalDate.now().minusDays(1);

        // When
        custumerDB.addCustQuantity(companyName, quantity, date);

        // Then
        Map<String, TreeMap<LocalDate, Double>> allData = custumerDB.getAllData();
        assertTrue(allData.containsKey(companyName));
        assertEquals(1, allData.get(companyName).size());
        assertEquals(quantity, allData.get(companyName).get(date));
    }

    @Test
    void testGetCustomerQuantityByDate_ReturnsCorrectQuantityForStartDate() {
        // Given
        String companyName = "XYZ Ltd";
        double quantity = 200.0;
        LocalDate date = LocalDate.now();

        custumerDB.addCustQuantity(companyName, quantity, date);

        // When
        List<OrderQuantityHistory> orderQuantityHistoryList = custumerDB.getCustomerQuantityByDate(date, "start_date");

        // Then
        assertEquals(200.0, orderQuantityHistoryList.get(0).getQuantity());
    }

    @Test
    void testGetCustomerQuantityByDate_ReturnsCorrectQuantityForEndDate() {
        // Given
        String companyName = "XYZ Ltd";
        double quantity = 300.0;
        LocalDate date = LocalDate.now().plusDays(2);

        custumerDB.addCustQuantity(companyName, quantity, date);

        // When
        List<OrderQuantityHistory> orderQuantityHistoryList = custumerDB.getCustomerQuantityByDate(date, "end_date");

        // Then
        assertEquals(300.0, orderQuantityHistoryList.get(0).getQuantity());
    }

    // Add more test cases to cover edge cases and boundary conditions
}
