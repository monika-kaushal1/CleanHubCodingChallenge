package com.cleanhub.codingchallenge.utils;

import com.cleanhub.codingchallenge.exceptions.ValidationException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.cleanhub.codingchallenge.utils.InputValidators.validateDates;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class InputValidatorsTest {

    @Test
    public void testValidDates() throws ValidationException {
        String startDate = "2024-04-01";
        String endDate = "2024-04-03";
        boolean result = validateDates(startDate, endDate);
        assertTrue(result);
    }

    @Test(expected = ValidationException.class)
    public void testInvalidDateFormat() throws ValidationException {
        String startDate = "2024/04/01"; // Invalid format
        String endDate = "2024-04-03";
        validateDates(startDate, endDate);
    }

    @Test(expected = ValidationException.class)
    public void testEndDateBeforeStartDate() throws ValidationException {
        String startDate = "2024-04-03";
        String endDate = "2024-04-01"; // End date before start date
        validateDates(startDate, endDate);
    }
}
