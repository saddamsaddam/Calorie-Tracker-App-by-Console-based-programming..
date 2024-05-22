package org.calorie;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.calorie.CalorieTrackerApp.fileExists;
import static org.calorie.CalorieTrackerApp.isValidDate;

public class FoodEntryClass {
    private String username;
    private String foodName;
    private double foodAmount;
    private String date;
int sn=0;
    // Constructor
    public  FoodEntryClass(){

    }
    public FoodEntryClass(String username, String foodName, double foodAmount, String date) {
        this.username = username;
        this.foodName = foodName;
        this.foodAmount = foodAmount;
        this.date = date;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(double foodAmount) {
        this.foodAmount = foodAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // toString method to represent the object as a string
    @Override
    public String toString() {
        return "FoodEntryClass{" +
                "username='" + username + '\'' +
                ", foodName='" + foodName + '\'' +
                ", foodAmount=" + foodAmount +
                ", date=" + date +
                '}';
    }
    public static boolean checkFoodEntryByDate(String username, String date) {
        List<FoodEntryClass> foodEntries = retrieveFoodEntryData("userFood.txt");

        // Check if there exists a food entry for the specified username and date
        for (FoodEntryClass entry : foodEntries) {
            if (entry.getUsername().equals(username) && entry.getDate().equals(date)) {
                return true; // Food entry found
            }
        }

        return false; // Food entry not found
    }

    public static void DisplayFoodEntryForNormalUserByDate(String username, String date) {
        List<FoodEntryClass> foodEntries = retrieveFoodEntryData("userFood.txt");
        double sumCalorie=0.0;

        // Check if there exists a food entry for the specified username and date
        System.out.println("FoodName||FoodAmount||FoodCalorie");
        for (FoodEntryClass entry : foodEntries) {
            if (entry.getUsername().equals(username) && entry.getDate().equals(date)) {
                double value=FoodCalorieClass.calculateCalorieOfFood(entry.foodName,entry.foodAmount);
                System.out.println(entry.getFoodName()+" || "+entry.getFoodAmount()+" || "+value);
                sumCalorie=sumCalorie+value;
            }
        }
        System.out.println("Eaten total Calorie: "+sumCalorie+"\n \n");

    }
    public static void DisplayFoodEntryForPowerUserByDate(String username, String date) {
        List<FoodEntryClass> foodEntries = retrieveFoodEntryData("userFood.txt");
        double sumCalorie=0.0;
        double sumVitamin=0.0;
        double sumProtein=0.0;

        // Check if there exists a food entry for the specified username and date
        System.out.println("FoodName||FoodAmount||FoodCalorie||FoodVitamin||FoodProtein");
        for (FoodEntryClass entry : foodEntries) {
            if (entry.getUsername().equals(username) && entry.getDate().equals(date)) {
                double[] value=FoodCalorieClass.calculateCalorieVitaminProteinOfFood(entry.foodName,entry.foodAmount);
                System.out.println(entry.getFoodName()+" || "+entry.getFoodAmount()+" || "+value[0]+" || "+value[1]+" || "+value[2]);
                sumCalorie=sumCalorie+value[0];
                sumVitamin=sumVitamin+value[1];
                sumProtein=sumProtein+value[2];
            }
        }
        System.out.println("Eaten total:>\nCalorie: "+sumCalorie+"\nVitamin: "+sumVitamin+"\nProtein: "+sumProtein);

    }
    public static void DisplayFoodEntryForNormalUserByNumberOfDays(String username, String lastDate,int numberOfDays) {
        List<FoodEntryClass> foodEntries = retrieveFoodEntryData("userFood.txt");
        double sumCalorie=0.0;
        String[] previousDates = FoodEntryClass.DayList(lastDate, numberOfDays);
        // Check if there exists a food entry for the specified username and date
        System.out.println("FoodName||FoodAmount||FoodCalorie");
        for (String date : previousDates) {
            for (FoodEntryClass entry : foodEntries) {
                if (entry.getUsername().equals(username) && entry.getDate().equals(date)) {
                    double value=FoodCalorieClass.calculateCalorieOfFood(entry.foodName,entry.foodAmount);
                    System.out.println(entry.getFoodName()+" || "+entry.getFoodAmount()+" || "+value);
                    sumCalorie=sumCalorie+value;
                }
            }
        }

        System.out.println("Eaten total Calorie in last "+numberOfDays+": "+sumCalorie+"\n \n");

    }
    public static void DisplayFoodEntryForPowerUserByNumberOfDays(String username, String lastDate,int numberOfDays) {
        List<FoodEntryClass> foodEntries = retrieveFoodEntryData("userFood.txt");
        double sumCalorie=0.0;
        double sumVitamin=0.0;
        double sumProtein=0.0;
        String[] previousDates = FoodEntryClass.DayList(lastDate, numberOfDays);
        // Check if there exists a food entry for the specified username and date
        System.out.println("FoodName||FoodAmount||FoodCalorie||FoodVitamin||FoodProtein");
        for (String date : previousDates) {
            for (FoodEntryClass entry : foodEntries) {
                if (entry.getUsername().equals(username) && entry.getDate().equals(date)) {
                    double value[]=FoodCalorieClass.calculateCalorieVitaminProteinOfFood(entry.foodName,entry.foodAmount);
                    System.out.println(entry.getFoodName()+" || "+entry.getFoodAmount()+" || "+value[0]+" || "+value[1]+" || "+value[2]);
                    sumCalorie=sumCalorie+value[0];
                    sumVitamin=sumVitamin+value[1];
                    sumProtein=sumProtein+value[2];
                }
            }
        }


        System.out.println("Eaten in last " +numberOfDays+" days total:>\nCalorie: "+sumCalorie+"\nVitamin: "+sumVitamin+"\nProtein: "+sumProtein+"\n\n");
    }
    public static String[] DayList(String lastDate, int numPreviousDates) {
        // Parse the lastDate string into a LocalDate object
        LocalDate lastLocalDate = LocalDate.parse(lastDate);

        // Initialize a list to store the previous dates
        List<String> previousDates = new ArrayList<>();

        // Formatter for converting LocalDate to string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Loop to generate previous dates
        for (int i = 0; i < numPreviousDates; i++) {
            // Subtract 'i' days from the last date
            LocalDate previousDate = lastLocalDate.minusDays(i);
            // Format the previous date and add it to the list
            previousDates.add(previousDate.format(formatter));
        }

        // Convert the list to an array and return
        return previousDates.toArray(new String[0]);
    }
    public void saveFoodEntry() {
        try {
            FileWriter writer = new FileWriter("userFood.txt", true); // Append mode

            if (!fileExists("userFood.txt")) {
                // Write header if the file is empty
                writer.write("Username,Food Name,Food Amount,Date\n");
            }

            // Write food entry information to file
            writer.write(username + "," + foodName + "," + foodAmount + "," + date + "\n");
            writer.close();
            System.out.println("Food entry saved successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public  void exportFoodEntry(String username){
        List<FoodEntryClass> foodEntries = retrieveFoodEntryData("userFood.txt");
        removeAllDataExceptHeader("exportUserFood.txt");
        try {
            FileWriter writer = new FileWriter("exportUserFood.txt", true); // Append mode

            if (!fileExists("exportUserFood.txt")) {
                // Write header if the file is empty
                writer.write("Username,Food Name,Food Amount,Date\n");
            }
            foodEntries.forEach(e->{
                if(e.getUsername().equals(username)){
                    try {
                        writer.write(e.getUsername() + "," + e.getFoodName() + "," + e.getFoodAmount() + "," + e.getDate() + "\n");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            // Write food entry information to file

            writer.close();
            System.out.println("Food entry exported successfully to exportUserFood.txt!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }

    }
    public  void importFoodEntry(String username){
        List<FoodEntryClass> foodEntries = retrieveFoodEntryData("importUserFood.txt");
        try {
            FileWriter writer = new FileWriter("userFood.txt", true); // Append mode

            if (!fileExists("userFood.txt")) {
                // Write header if the file is empty
                writer.write("Username,Food Name,Food Amount,Date\n");
            }
            foodEntries.forEach(e->{

                if(new FoodCalorieClass().checkFoodExistance(e.getFoodName()))// check calorie table
                {
                    if(new Goal().checkGoalDataByUsernameAndDate(e.getUsername(),e.getDate())){ //check goal table
                        try {
                            if(isValidDate(e.getDate())){
                                writer.write(e.getUsername() + "," + e.getFoodName() + "," + e.getFoodAmount() + "," + e.getDate() + "\n");
                            }
                            else{
                                System.out.println("Sorry, The date is invalid");
                            }

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

            });
            // Write food entry information to file

            writer.close();
            System.out.println("Food entry imported successfully from importUserFood.txt!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }

    }
    public void editFoodEntry(int count) {
        sn=0;
        List<FoodEntryClass> foodEntries=retrieveFoodEntryData("userFood.txt");
        removeAllDataExceptHeader("userFood.txt");
        foodEntries.forEach(e->{
            try {
                FileWriter writer = new FileWriter("userFood.txt", true); // Append mode

                if (!fileExists("userFood.txt")) {
                    // Write header if the file is empty
                    writer.write("Username,Food Name,Food Amount,Date\n");
                }
                if(e.getUsername().equals(username))
                {
                    if(sn==count)
                    {
                        writer.write(username + "," + foodName + "," + foodAmount + "," + date + "\n");
                    }
                    else {
                        writer.write(e.getUsername() + "," + e.foodName + "," + e.getFoodAmount() + "," + e.getDate() + "\n");
                    }
                    sn++;
                }
                else {
                    writer.write(e.getUsername() + "," + e.foodName + "," + e.getFoodAmount() + "," + e.getDate() + "\n");
                }
                // Write food entry information to file

                writer.close();

            } catch (IOException f) {
                System.out.println("An error occurred while writing to the file: " + f.getMessage());
            }
        });
      System.out.println("Food Entry Edited Successfully");
    }

    public void deleteFoodEntry(int count) {
        sn=0;
        List<FoodEntryClass> foodEntries=retrieveFoodEntryData("userFood.txt");
        removeAllDataExceptHeader("userFood.txt");
        foodEntries.forEach(e->{
            try {
                FileWriter writer = new FileWriter("userFood.txt", true); // Append mode

                if (!fileExists("userFood.txt")) {
                    // Write header if the file is empty
                    writer.write("Username,Food Name,Food Amount,Date\n");
                }
                if(e.getUsername().equals(username))
                {
                    if(sn==count)
                    {
                       // nothing do
                    }
                    else {
                        writer.write(e.getUsername() + "," + e.foodName + "," + e.getFoodAmount() + "," + e.getDate() + "\n");
                    }
                    sn++;
                }
                else {
                    writer.write(e.getUsername() + "," + e.foodName + "," + e.getFoodAmount() + "," + e.getDate() + "\n");
                }
                // Write food entry information to file

                writer.close();

            } catch (IOException f) {
                System.out.println("An error occurred while writing to the file: " + f.getMessage());
            }
        });
        System.out.println("Food Entry deleted Successfully");
    }
    public static void DisplayFoodEntryList(String username) {
        List<FoodEntryClass> foodEntries = retrieveFoodEntryData("userFood.txt");
        // Display food entries for the specified username
        System.out.println("\nFood entries: \n");
        System.out.println("SN || FoodName || FoodAmount ||  Date");
        int i=1;
        for (FoodEntryClass entry : foodEntries) {
            if (entry.getUsername().equals(username)) {
                System.out.println(i+") "+entry.getFoodName()+"  ||  "+entry.getFoodAmount()+"  ||  "+entry.getDate());
           i++;
            }
        }
    }


    public static List<FoodEntryClass> retrieveFoodEntryData(String fileName) {
        List<FoodEntryClass> foodEntries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Skip header
            reader.readLine();

            // Read each line and create FoodEntryClass object
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String username = parts[0];
                    String foodName = parts[1];
                    double foodAmount = Double.parseDouble(parts[2]);
                    String date = parts[3];
                    FoodEntryClass foodEntry = new FoodEntryClass(username, foodName, foodAmount, date);
                    foodEntries.add(foodEntry);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file: " + e.getMessage());
        }

        return foodEntries;
    }

    public void removeAllDataExceptHeader(String fileName) {
        try {  //"userFood.txt"
            File inputFile = new File(fileName);
            File tempFile = new File("tempFile.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            // Read the header and write it to the temporary file
            String header = reader.readLine();
            writer.write(header);
            writer.newLine();

            // Skip through the rest of the file (excluding header)
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(""); // Writing an empty string effectively removes the line
                writer.newLine();
            }

            reader.close();
            writer.close();

            // Replace the original file with the temporary file
            if (!inputFile.delete()) {
                System.out.println("Could not delete the original file.");
                return;
            }

            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename the temporary file.");
            }



        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
