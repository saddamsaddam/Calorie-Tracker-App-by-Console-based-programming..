package org.calorie;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {

    public static boolean isValidDate(String dateStr) {
        // Define the date format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // Parse the input date string to LocalDate
            LocalDate date = LocalDate.parse(dateStr, dateFormatter);
            return true; // If parsing successful, date is valid
        } catch (DateTimeParseException e) {
            return false; // If parsing fails, date is invalid
        }
    }

    public static void main(String[] args) {
        // Example usage
        String inputDate = "2024-40-13";
        System.out.println("Is date valid? " + isValidDate(inputDate));
    }
}
