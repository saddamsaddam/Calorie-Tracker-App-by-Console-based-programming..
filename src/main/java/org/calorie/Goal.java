package org.calorie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.calorie.CalorieTrackerApp.fileExists;
import static org.calorie.CalorieTrackerApp.isValidDate;
import static org.calorie.FoodEntryClass.DayList;

public class Goal {
    private String username;
    private String date;
    private double calorieGoal;
    private double vitaminGoal;
    private double proteinGoal;
    public Goal(){

    }
    // Constructor
    public Goal(String username,String date, double calorieGoal, double vitaminGoal, double proteinGoal) {
        this.username=username;
        this.date = date;
        this.calorieGoal = calorieGoal;
        this.vitaminGoal = vitaminGoal;
        this.proteinGoal = proteinGoal;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(double calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public double getVitaminGoal() {
        return vitaminGoal;
    }

    public void setVitaminGoal(double vitaminGoal) {
        this.vitaminGoal = vitaminGoal;
    }

    public double getProteinGoal() {
        return proteinGoal;
    }

    public void setProteinGoal(double proteinGoal) {
        this.proteinGoal = proteinGoal;
    }

    @Override
    public String toString() {
        return "Date: " + username +
                "Date: " + date +
                ", Calorie Goal: " + calorieGoal +
                ", Vitamin Goal: " + vitaminGoal +
                ", Protein Goal: " + proteinGoal;
    }

    public static double goalForNormalUserByDate(String username, String date) {
        List<Goal> goals = retrieveGoalData("goal.txt");

        // Find the goal for the specified username and date
        for (Goal goal : goals) {
            if (goal.getUsername().equals(username) && goal.getDate().equals(date)) {
                return goal.getCalorieGoal(); // Return the goal value if found
            }
        }

        return 0.0; // Return 0 if no goal is found for the specified username and date
    }
    public static double[] goalForPowerUserByDate(String username, String date) {
        List<Goal> goals = retrieveGoalData("goal.txt");
        double[]  data=new double[3];
        // Find the goal for the specified username and date
        for (Goal goal : goals) {
            if (goal.getUsername().equals(username) && goal.getDate().equals(date)) {

                data[0]=goal.getCalorieGoal();
                data[1]=goal.getVitaminGoal();
                data[2]=goal.getProteinGoal();
            }
        }

        return data; // Return 0 if no goal is found for the specified username and date
    }
    public static double goalForNormalUserByNumberOfDays(String username,String lastDate,int NumberOfDays) {
        List<Goal> goals = retrieveGoalData("goal.txt");
        String[] previousDates = FoodEntryClass.DayList(lastDate, NumberOfDays);
        double calorieSum=0.0;
        // Print the previous dates
        for (String date : previousDates) {

            for (Goal goal : goals) {
                if (goal.getUsername().equals(username) ) {
                    if(date.equals(goal.getDate()))
                    {
                        calorieSum=calorieSum+goal.getCalorieGoal();
                    }
                }
            }

        }
        // Find the goal for the specified username and date


        return calorieSum; // Return 0 if no goal is found for the specified username and date
    }
    public static double[] goalForPowerUserByNumberOfDays(String username,String lastDate,int NumberOfDays) {
        List<Goal> goals = retrieveGoalData("goal.txt");
        String[] previousDates = FoodEntryClass.DayList(lastDate, NumberOfDays);
        double calorieSum=0.0;
        double[]  data=new double[3];
        // Print the previous dates
        for (String date : previousDates) {

            for (Goal goal : goals) {
                if (goal.getUsername().equals(username) ) {
                    if(date.equals(goal.getDate()))
                    {
                       // calorieSum=calorieSum+goal.getCalorieGoal();
                        data[0]=data[0]+goal.getCalorieGoal();
                        data[1]=data[0]+goal.getVitaminGoal();
                        data[2]=data[0]+goal.getProteinGoal();
                    }
                }
            }

        }
        // Find the goal for the specified username and date


        return data; // Return 0 if no goal is found for the specified username and date
    }
    public  void exportGoal(String username){
        List<Goal> goals = retrieveGoalData("goal.txt");
        removeAllDataExceptHeader("exportGoal.txt");
        try {
            FileWriter writer = new FileWriter("exportGoal.txt", true); // Append mode
            if (!fileExists("exportGoal.txt")) {
                // Write header if the file is empty
                writer.write("userName,Date,Calorie Goal,Vitamin Goal,Protein Goal\n");
            }
            goals.forEach(e->{
                if(username.equals(e.username)){
                    try {
                        writer.write(e.getUsername() + "," +e.getDate() + "," + e.getCalorieGoal() + "," + e.getVitaminGoal() + "," + e.getProteinGoal() + "\n");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            writer.close();
            System.out.println("Goal was exported successfully to exportGoal.txt!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }

    }
    public  void importGoal(String username){
        List<Goal> goals = retrieveGoalData("importGoal.txt");
        try {
            FileWriter writer = new FileWriter("goal.txt", true); // Append mode
            if (!fileExists("goal.txt")) {
                // Write header if the file is empty
                writer.write("userName,Date,Calorie Goal,Vitamin Goal,Protein Goal\n");
            }
            goals.forEach(e->{
                if(!checkGoalDataByUsernameAndDate(e.getUsername(),e.getDate()))
                {
                    try {
                        if(isValidDate(e.getDate())){
                            writer.write(e.getUsername() + "," +e.getDate() + "," + e.getCalorieGoal() + "," + e.getVitaminGoal() + "," + e.getProteinGoal() + "\n");
                        }
                        else{
                            System.out.println("Sorry, The date is invalid");
                        }

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            });
            writer.close();
            System.out.println("Goal was imported successfully from importGoal.txt!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }

    }
    public void saveGoal() {
        try {
            FileWriter writer = new FileWriter("goal.txt", true); // Append mode
            if (!fileExists("goal.txt")) {
                // Write header if the file is empty
                writer.write("userName,Date,Calorie Goal,Vitamin Goal,Protein Goal\n");
            }
            writer.write(username + "," +date + "," + calorieGoal + "," + vitaminGoal + "," + proteinGoal + "\n");
            writer.close();
            System.out.println("Goal saved successfully to goal.txt!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public void editGoal() {
        List<Goal> goals = retrieveGoalData("goal.txt");
        removeAllDataExceptHeader("goal.txt");
        goals.forEach(goal->{
            try {
                FileWriter writer = new FileWriter("goal.txt", true); // Append mode
                if (!fileExists("goal.txt")) {
                    // Write header if the file is empty
                    writer.write("userName,Date,Calorie Goal,Vitamin Goal,Protein Goal\n");
                }
                if (goal.getUsername().equalsIgnoreCase(username) && goal.getDate().equalsIgnoreCase(date)) {
                    writer.write(username + "," +date + "," + calorieGoal + "," + vitaminGoal + "," + proteinGoal + "\n");
                }
                else {
                    writer.write(goal.username + "," +goal.getDate() + "," + goal.getCalorieGoal() + "," + goal.getVitaminGoal() + "," + goal.getProteinGoal() + "\n");
                }

                writer.close();

            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file: " + e.getMessage());
            }
        });
        System.out.println("Goal data edited successfully");
    }
    public void deleteGoal() {
        List<Goal> goals = retrieveGoalData("goal.txt");
        removeAllDataExceptHeader("goal.txt");
        goals.forEach(goal->{
            try {
                FileWriter writer = new FileWriter("goal.txt", true); // Append mode
                if (!fileExists("goal.txt")) {
                    // Write header if the file is empty
                    writer.write("userName,Date,Calorie Goal,Vitamin Goal,Protein Goal\n");
                }
                if (goal.getUsername().equalsIgnoreCase(username) && goal.getDate().equalsIgnoreCase(date)) {
                   // nothing to do
                }
                else {
                    writer.write(goal.username + "," +goal.getDate() + "," + goal.getCalorieGoal() + "," + goal.getVitaminGoal() + "," + goal.getProteinGoal() + "\n");
                }

                writer.close();

            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file: " + e.getMessage());
            }
        });
        System.out.println("Goal data deleted successfully");
    }
    public void removeAllDataExceptHeader(String fileName) {
        try {
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
    public static List<Goal> retrieveGoalData(String fileName) {
        List<Goal> goals = new ArrayList<>(); //
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Skip header line if exists
            if ((line = reader.readLine()) != null) {
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if(parts.length==5)
                    {
                        String username = parts[0];
                        String date = parts[1];
                        double calorieGoal = Double.parseDouble(parts[2]);
                        double vitaminGoal = Double.parseDouble(parts[3]);
                        double proteinGoal = Double.parseDouble(parts[4]);
                        Goal goal = new Goal(username, date, calorieGoal, vitaminGoal, proteinGoal);
                        goals.add(goal);
                    }

                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("An error occurred while reading from the file: " + e.getMessage());
        }
        return goals;
    }

    public boolean checkGoalDataByUsernameAndDate(String name, String date) {
        List<Goal> goals = retrieveGoalData("goal.txt");
        for (Goal goal : goals) {
            if (goal.getUsername().equalsIgnoreCase(name) && goal.getDate().equalsIgnoreCase(date)) {
                return true; // Found the goal entry for the specified username and date
            }
        }
        return false; // Goal entry not found for the specified username and date
    }
public static void DisplayGoalDataForNormalUser(String name)
{
    List<Goal> goals = retrieveGoalData("goal.txt");

    System.out.println("\nGoal Data list with Calorie Quantity:\n");
    System.out.println("Date   ||      Calorie quantity(Calorie)");
    for (Goal goal : goals) {
        if (goal.getUsername().equalsIgnoreCase(name) ) {
         System.out.println(goal.getDate()+" || "+ goal.getCalorieGoal());
        }
    }
}

    public static void DisplayGoalDataForPowerUser(String name)
    {
        List<Goal> goals = retrieveGoalData("goal.txt");

        System.out.println("\nGoal Data list with Calorie Quantity:\n");
        System.out.println("Date || Calorie quantity(Calorie) || Vitamin Quantity(Gram) || Protein Quantity(Gram)");
        for (Goal goal : goals) {
            if (goal.getUsername().equalsIgnoreCase(name) ) {
                System.out.println(goal.getDate()+" || "+ goal.getCalorieGoal()+"   ||   "+goal.getVitaminGoal()+"  || "+goal.getProteinGoal());
            }
        }
    }
}
