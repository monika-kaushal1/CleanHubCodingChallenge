package com.cleanhub.codingchallenge.utils;


import com.cleanhub.codingchallenge.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class InputValidators {

    /**
     * This function will validate the date formats and endDate is greater than startDate.
     * @param startDate
     * @param endDate
     * @return
     * @throws ValidationException
     */
    public static boolean validateDates(String startDate, String endDate) throws ValidationException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate sDate = null;
        LocalDate eDate = null;

        try {
            sDate = LocalDate.parse(startDate, formatter);
            eDate = LocalDate.parse(endDate, formatter);
        } catch (Exception e) {
            throw new ValidationException("Date format is not correct" + e);
        }

        if (eDate.compareTo(sDate) < 0) {
            throw new ValidationException("End Date should be more than start Date");
        }
        return true;
    }
}
