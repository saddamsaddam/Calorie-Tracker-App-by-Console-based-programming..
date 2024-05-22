package org.calorie;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class CalorieTrackerApp {
    static String username;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isAuthenticated = false; // Flag to track authentication status
        User currentUser = null;
        // Welcome message
        System.out.println("Welcome to Calorie Tracker App!");
        while (!isAuthenticated) {
            System.out.println("1. Normal user");
            System.out.println("2. Power user");
            System.out.println("3. Admin");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            if (choice == 1) {
                new NormalUser();
            } else if (choice == 2) {
                new PowerUser();
            } else if (choice==3) {
                new AdminUser();
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }

        }
    }

    //file check
    static boolean fileExists(String filename) {
        // Check if the file exists
        File file = new File(filename);
        return file.exists();
    }
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
}
