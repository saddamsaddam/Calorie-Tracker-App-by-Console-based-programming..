package org.calorie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.calorie.CalorieTrackerApp.fileExists;

public class FoodCalorieClass {
    private String foodName;
    private double foodAmount; // Assuming food amount is in grams
    private double calorieQuantity; // Assuming calorie quantity is in calories
    private double protein;
    private double vitamin;
    public FoodCalorieClass()
    {

    }

    // Constructor
    public FoodCalorieClass(String foodName, double foodAmount, double calorieQuantity,double vitamin,double protein) {
        this.foodName = foodName;
        this.foodAmount = foodAmount;
        this.calorieQuantity = calorieQuantity;
        this.vitamin=vitamin;
        this.protein=protein;
    }

    // Getters and setters
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

    public double getCalorieQuantity() {
        return calorieQuantity;
    }


    public void setCalorieQuantity(double calorieQuantity) {
        this.calorieQuantity = calorieQuantity;
    }
    public double getProtein() {
        return protein;
    }


    public void setProtein(double protein) {
        this.protein= protein;
    }

    public double getVitamin() {
        return vitamin;
    }


    public void setVitamin(double vitamin) {
        this.vitamin = vitamin;
    }
    public static double calculateCalorieOfFood(String foodName, double foodAmount) {
        List<FoodCalorieClass> foodList = retrieveFoodCalorieQuantity();

        // Iterate through the food list to find the calorie information for the specified food name
        for (FoodCalorieClass food : foodList) {
            if (food.getFoodName().equals(foodName)) {
                // Calculate the total calorie intake based on the provided food amount
                return (food.getCalorieQuantity()*foodAmount)/food.foodAmount;
            }
        }

        // Return -1 if the food item is not found in the list
        return -1;
    }
    public static double[] calculateCalorieVitaminProteinOfFood(String foodName, double foodAmount) {
        List<FoodCalorieClass> foodList = retrieveFoodCalorieQuantity();
        double[] data=new double[3];
        // Iterate through the food list to find the calorie information for the specified food name
        for (FoodCalorieClass food : foodList) {
            if (food.getFoodName().equals(foodName)) {
                data[0]=((food.getCalorieQuantity()*foodAmount)/food.foodAmount);
                data[1]=((food.getVitamin()*foodAmount)/food.foodAmount);
                data[2]=((food.getProtein()*foodAmount)/food.foodAmount);
            }
        }

        // Return -1 if the food item is not found in the list
        return data;
    }

    public void saveFoodCalorieQuantity() {
        try {
            FileWriter writer = new FileWriter("FoodCalorieQuantity.txt", true); // Append mode

            if (!fileExists("FoodCalorieQuantity.txt")) {
                // Write header if the file is empty
                writer.write("Food Name,Food Amount (Gram),Calorie Quantity (calories),Vitamin(Grams),Protein(Gram)\n");
            }

            // Write food entry information to file
            writer.write(foodName + "," + foodAmount + "," + calorieQuantity + "," + vitamin + "," + protein + "\n");
            writer.close();
            System.out.println("Food calorie , Vitamin, Protein quantity saved successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public void editFoodCalorieQuantity() {
        // store file data
        List<FoodCalorieClass> foodList = retrieveFoodCalorieQuantity();
        removeAllDataExceptHeader();
        foodList.forEach(e->{
            try {
                FileWriter writer = new FileWriter("FoodCalorieQuantity.txt", true); // Append mode

                if (!fileExists("FoodCalorieQuantity.txt")) {
                    // Write header if the file is empty
                    writer.write("Food Name,Food Amount (Gram),Calorie Quantity (calories),Vitamin(Grams),Protein(Gram)\n");
                }
                if(e.getFoodName().equals(foodName))
                {
                    writer.write(foodName + "," + foodAmount + "," + calorieQuantity + "," + vitamin + "," + protein + "\n");
                }else{
                    writer.write(e.foodName + "," + e.getFoodAmount() + "," + e.getCalorieQuantity() + "," + e.getVitamin() + "," + e.getProtein() + "\n");
                }
                // Write food entry information to file

                writer.close();

            } catch (IOException f) {
                System.out.println("An error occurred while writing to the file: " + f.getMessage());
            }
        });
        System.out.println("Successfully food information updated");


    }

    public void deleteFoodCalorieQuantity() {
        // store file data
        List<FoodCalorieClass> foodList = retrieveFoodCalorieQuantity();
        removeAllDataExceptHeader();
        foodList.forEach(e->{
            try {
                FileWriter writer = new FileWriter("FoodCalorieQuantity.txt", true); // Append mode

                if (!fileExists("FoodCalorieQuantity.txt")) {
                    // Write header if the file is empty
                    writer.write("Food Name,Food Amount (Gram),Calorie Quantity (calories),Vitamin(Grams),Protein(Gram)\n");
                }
                if(!e.getFoodName().equals(foodName))
                {
                    writer.write(e.foodName + "," + e.getFoodAmount() + "," + e.getCalorieQuantity() + "," + e.getVitamin() + "," + e.getProtein() + "\n");
                }
                // Write food entry information to file

                writer.close();

            } catch (IOException f) {
                System.out.println("An error occurred while writing to the file: " + f.getMessage());
            }
        });
        System.out.println("Successfully food deleted");

    }
    public void removeAllDataExceptHeader() {
        try {
            File inputFile = new File("FoodCalorieQuantity.txt");
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
    public static List<FoodCalorieClass> retrieveFoodCalorieQuantity() {
        List<FoodCalorieClass> foodList = new ArrayList<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader("FoodCalorieQuantity.txt"))) {
            // Skip header line if exists
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length==5)
                {
                    String foodName = parts[0];
                    double foodAmount = Double.parseDouble(parts[1]);
                    double calorieQuantity = Double.parseDouble(parts[2]);
                    double vitaminQuantity = Double.parseDouble(parts[3]);
                    double proteinQuantity = Double.parseDouble(parts[4]);
                    FoodCalorieClass food = new FoodCalorieClass(foodName, foodAmount, calorieQuantity,vitaminQuantity,proteinQuantity);
                    foodList.add(food);
                }

            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("An error occurred while reading from the file: " + e.getMessage());
        }
        return foodList;
    }
    public boolean checkFoodExistance(String name) {
        List<FoodCalorieClass> foodList = retrieveFoodCalorieQuantity();
        for (FoodCalorieClass food : foodList) {
            if (food.getFoodName().equalsIgnoreCase(name)) {
                return true; // Found the food item
            }
        }
        return false; // Food item not found
    }

    public static void foodCalorieDisplayForNormalUser()
    {
        List<FoodCalorieClass> retrieveFoodCalorieQuantity=retrieveFoodCalorieQuantity();
        System.out.println("\nFood list with Calorie Quantity:\n");
        System.out.println("Food name   ||     Food amount(In Gram)      ||    Calorie quantity(Calorie)");
        retrieveFoodCalorieQuantity.forEach(e->{
            System.out.println(  e.getFoodName()+" || "+e.foodAmount+"|| "+e.getCalorieQuantity());
        });
    }
    // Override toString method for printing
    @Override
    public String toString() {
        return "Food Name: " + foodName + ", Food Amount: " + foodAmount + " grams, Calorie Quantity: " + calorieQuantity + " calories";
    }
    public static void foodCalorieDisplayForPowerUser()
    {
        List<FoodCalorieClass> retrieveFoodCalorieQuantity=retrieveFoodCalorieQuantity();
        System.out.println("\nFood list with Calorie, Vitamin,Protein Quantity:\n");
        System.out.println("Food name || Food amount(In Gram) || Calorie quantity(Calorie) || Vitamin Quantity(Gram)  ||  Protein Quantity(Gram)");
        retrieveFoodCalorieQuantity.forEach(e->{
            System.out.println(  e.getFoodName()+" || "+e.foodAmount+"|| "+e.getCalorieQuantity()+"  || "+e.getVitamin()+"  ||  "+e.getProtein());
        });
    }
    // Override toString method for printing

}
