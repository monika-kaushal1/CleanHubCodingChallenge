package com.cleanhub.codingchallenge.service;

import com.cleanhub.codingchallenge.domain.OrderQuantityHistory;
import com.cleanhub.codingchallenge.exceptions.CleanHubServiceException;
import com.cleanhub.codingchallenge.repository.OrderHistoryRepository;
import com.cleanhub.codingchallenge.service.impl.CleanHubHistoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CleanHubHistoryServiceTest {

    @Mock
    private OrderHistoryRepository orderHistoryRepository;

    @InjectMocks
    private CleanHubHistoryServiceImpl cleanHubHistoryService;

    @Test
    void testGetTopCustomers_ReturnsEmptyListWhenNoDataAvailable() throws CleanHubServiceException {
        // Given
        String startDate = "2024-01-01";
        String endDate = "2024-01-31";

        when(orderHistoryRepository.getCustomerQuantityByDateGt(startDate)).thenReturn(new ArrayList<>());
        when(orderHistoryRepository.getCustomerQuantityByDateLt(endDate)).thenReturn(new ArrayList<>());

        CleanHubServiceException exception = assertThrows(CleanHubServiceException.class, () -> cleanHubHistoryService.getTopCustomers(5, startDate, endDate));
        assertEquals("No customers for this date range", exception.getMessage());
    }

    @Test
    void testGetTopCustomers_ReturnsTopCustomers() throws CleanHubServiceException {
        // Given
        String startDate = "2024-01-01";
        String endDate = "2024-01-31";

        List<OrderQuantityHistory> customerOrderMinList = Arrays.asList(new OrderQuantityHistory("Customer1", LocalDate.parse("2024-01-01"), 10.0), new OrderQuantityHistory("Customer2", LocalDate.parse("2024-01-02"), 20.0), new OrderQuantityHistory("Customer3", LocalDate.parse("2024-01-03"), 15.0));

        List<OrderQuantityHistory> customerOrderMaxList = Arrays.asList(new OrderQuantityHistory("Customer1", LocalDate.parse("2024-01-20"), 20.0), new OrderQuantityHistory("Customer2", LocalDate.parse("2024-01-21"), 35.0), new OrderQuantityHistory("Customer3", LocalDate.parse("2024-01-21"), 27.0));

        when(orderHistoryRepository.getCustomerQuantityByDateGt(startDate)).thenReturn(customerOrderMinList);
        when(orderHistoryRepository.getCustomerQuantityByDateLt(endDate)).thenReturn(customerOrderMaxList);

        // When
        List<String> result = cleanHubHistoryService.getTopCustomers(2, startDate, endDate);

        // Then
        assertEquals(2, result.size());
        assertEquals("Customer2", result.get(0));
        assertEquals("Customer3", result.get(1));
    }
}
