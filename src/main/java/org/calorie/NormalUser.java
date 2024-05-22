package org.calorie;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.calorie.CalorieTrackerApp.fileExists;
import static org.calorie.CalorieTrackerApp.isValidDate;

public class NormalUser {
    private String username;
    private boolean isAuthenticated = false;
    private Scanner scanner;

    public NormalUser() {
        scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        // Perform login authentication
        isAuthenticated = new User().authenticateUser(username, password, "normal");
        if (isAuthenticated) {
            System.out.println("Password Match");
            showMenu();
        } else {
            System.out.println("Password Not Match");
        }
    }

    private void showMenu() {
        while (true) {
            System.out.println("1. Create");
            System.out.println("2. Query by date");
            System.out.println("3. Query by week");
            System.out.println("4. Query by month");
            System.out.println("5. Edit");
            System.out.println("6. Delete");
            System.out.println("7. Set Goal");
            System.out.println("8. Edit Goal");
            System.out.println("9. Delete Goal");
            System.out.println("10. Set Calorie Quantity");
            System.out.println("11. Edit Calorie Quantity");
            System.out.println("12. Delete Calorie Quantity");
            System.out.println("13. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    create_1();
                    break;
                case 2:
                    queryByDateForNormalUser_2();

                    break;
                case 3:
                    queryByWeekForNormalUser_3();
                    break;
                case 4:
                    queryByMonthForNormalUser_4();
                    break;
                case 5:
                    editNormalUserFoodEating_5();
                    break;
                case 6:
                    deleteNormalUserFoodEating_6();
                    break;
                case 7:
                    setGoalForNormalUser_7();
                    break;
                case 8:
                    editGoalForNormalUser_8();
                    break;
                case 9:
                    deleteGoalForNormalUser_9();
                    break;
                case 10:
                    setCalorieVitaminProtein_10();
                    // Add modify logic
                    break;
                case 11:
                    editCalorieQuantity_11();
                    break;
                case 12:
                    deleteCalorieQuantity_12();
                    break;

                case 13:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void create_1() {
        FoodCalorieClass.foodCalorieDisplayForNormalUser();
        System.out.print("Enter food name: ");
        String foodName = scanner.next();
        System.out.print("Enter food amount (In Gram): ");
        double foodAmount = scanner.nextDouble();
        Goal.DisplayGoalDataForNormalUser(username);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();

        if(isValidDate(date)){
            if(new FoodCalorieClass().checkFoodExistance(foodName))// check calorie table
            {
                if(new Goal().checkGoalDataByUsernameAndDate(username,date)){ // check goal table
                    new FoodEntryClass(username,foodName,foodAmount,date).saveFoodEntry();
                }
                else{
                    System.out.println("Sorry,this food has no exist in food goal list , Please set Goal");
                }

            }
            else{
                System.out.println("Sorry,this food has no exist in food calorie quantity list, Please Set Calorie Quantity ");
            }
        }
        else{
           System.out.println("Sorry, The date is invalid");
        }



    }
    public void queryByDateForNormalUser_2(){
        FoodEntryClass.DisplayFoodEntryList(username);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();

        if(isValidDate(date)){
            if(FoodEntryClass.checkFoodEntryByDate(username,date)){
                System.out.println("Goal of "+date+" :>  Target Calorie: "+Goal.goalForNormalUserByDate(username,date));
                FoodEntryClass.DisplayFoodEntryForNormalUserByDate(username,date);
            }
            else{
                System.out.println("Sorry, There is no food entry  for this date");
            }
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }

    }
    public void queryByWeekForNormalUser_3(){
        Goal.DisplayGoalDataForNormalUser(username);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();
        if(isValidDate(date)){
            System.out.println("Goal of last week:>  Target Calorie: "+Goal.goalForNormalUserByNumberOfDays(username,date,7));
            FoodEntryClass.DisplayFoodEntryForNormalUserByNumberOfDays(username,date,7);
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }


    }
    public void queryByMonthForNormalUser_4(){
        Goal.DisplayGoalDataForNormalUser(username);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();

        if(isValidDate(date)){
            System.out.println("Goal of last week:>  Target Calorie: "+Goal.goalForNormalUserByNumberOfDays(username,date,30));
            FoodEntryClass.DisplayFoodEntryForNormalUserByNumberOfDays(username,date,30);
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }



    }
    private void editNormalUserFoodEating_5() {
        FoodEntryClass.DisplayFoodEntryList(username);
        System.out.print("Enter food name: ");
        String foodName = scanner.next();
        System.out.print("Enter food amount (In Gram): ");
        double foodAmount = scanner.nextDouble();
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();
        System.out.print("Enter Serial Number(SN): ");
        int sn=scanner.nextInt();

        if(isValidDate(date)){
            // Save to file
            // check food in calorie quantity list
            if(new FoodCalorieClass().checkFoodExistance(foodName))// check calorie table
            {
                if(new Goal().checkGoalDataByUsernameAndDate(username,date)){ // check goal table
                    new FoodEntryClass(username,foodName,foodAmount,date).editFoodEntry(sn-1);
                }
                else{
                    System.out.println("Sorry,this food has no exist in food goal list , Please set Goal");
                }

            }
            else{
                System.out.println("Sorry,this food has no exist in food calorie quantity list, Please Set Calorie Quantity ");
            }
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }


    }

    private void deleteNormalUserFoodEating_6() {
        FoodEntryClass.DisplayFoodEntryList(username);
        System.out.print("Enter Serial Number(SN): ");
        int sn=scanner.nextInt();
        // Save to file
        new FoodEntryClass(username,null,0,null).deleteFoodEntry(sn-1);

    }



   public void setGoalForNormalUser_7(){
        Goal.DisplayGoalDataForNormalUser(username);
       System.out.print("Enter date (Format: YYYY-MM-DD): ");
       String date = scanner.next();
       System.out.print("Enter Calorie (In  Calorie): ");
       double calorieAmount = scanner.nextDouble();

       if(isValidDate(date)){
// save data to txt file by checking
           if(new Goal().checkGoalDataByUsernameAndDate(username,date))
           {
               System.out.println("Sorry,the date is wrong and the date has been already inputted ");
           }
           else {
               new Goal(username,date,calorieAmount,0,0).saveGoal();
           }
       }
       else{
           System.out.println("Sorry, The date is invalid");
       }



    }

    public void editGoalForNormalUser_8(){
        Goal.DisplayGoalDataForNormalUser(username);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();
        System.out.print("Enter Calorie (In  Calorie): ");
        double calorieAmount = scanner.nextDouble();
        if(isValidDate(date)){
// save data to txt file by checking
            if(new Goal().checkGoalDataByUsernameAndDate(username,date))
            {

                new Goal(username,date,calorieAmount,0,0).editGoal();
            }
            else {
                System.out.println("Sorry,this day goal have no in the list");
            }
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }



    }
    public void deleteGoalForNormalUser_9(){
        Goal.DisplayGoalDataForNormalUser(username);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();

        if(isValidDate(date)){
// save data to txt file by checking
            if(new Goal().checkGoalDataByUsernameAndDate(username,date))
            {

                new Goal(username,date,0,0,0).deleteGoal();
            }
            else {
                System.out.println("Sorry,this day goal have no in the list");
            }
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }



    }
    public void setCalorieVitaminProtein_10()
    {
        FoodCalorieClass.foodCalorieDisplayForNormalUser();
        System.out.print("Enter food name: ");
        String foodName = scanner.next();
        System.out.print("Enter food amount (In Gram): ");
        double foodAmount = scanner.nextDouble();
        System.out.print("Enter Calorie (In  Calorie): ");
        double calorieAmount = scanner.nextDouble();

        System.out.print("Enter Vitamin (In Gram): ");
        double vitaminAmount = scanner.nextDouble();
        System.out.print("Enter Protein (In Gram): ");
        double proteinAmount = scanner.nextDouble();

        // check this food added or not
        if(new FoodCalorieClass().checkFoodExistance(foodName))
        {
            System.out.print("Sorry,Already added this food information");
        }
        else{
            FoodCalorieClass food1 = new FoodCalorieClass(foodName, foodAmount, calorieAmount,vitaminAmount,proteinAmount);
            food1.saveFoodCalorieQuantity();
        }


    }
    public  void editCalorieQuantity_11()
    {
        FoodCalorieClass.foodCalorieDisplayForPowerUser();
        System.out.print("Enter food name: ");
        String foodName = scanner.next();
        System.out.print("Enter food amount (In Gram): ");
        double foodAmount = scanner.nextDouble();
        System.out.print("Enter Calorie (In  Calorie): ");
        double calorieAmount = scanner.nextDouble();

        System.out.print("Enter Vitamin (In Gram): ");
        double vitaminAmount = scanner.nextDouble();
        System.out.print("Enter Protein (In Gram): ");
        double proteinAmount = scanner.nextDouble();
        // Food information
        if(new FoodCalorieClass().checkFoodExistance(foodName))
        {
            //
            FoodCalorieClass food1 = new FoodCalorieClass(foodName, foodAmount, calorieAmount,vitaminAmount,proteinAmount);
            food1.editFoodCalorieQuantity();

        }
        else{
            System.out.println("Sorry,this food have no in the list");
        }

    }
    public  void deleteCalorieQuantity_12()
    {
        FoodCalorieClass.foodCalorieDisplayForPowerUser();
        System.out.print("Enter food name: ");
        String foodName = scanner.next();
        // Food information
        if(new FoodCalorieClass().checkFoodExistance(foodName))
        {
            //
            FoodCalorieClass food1 = new FoodCalorieClass(foodName, 0, 0,0,0);
            food1.deleteFoodCalorieQuantity();

        }
        else{
            System.out.println("Sorry,this food have no in the list");
        }

    }

    public static void main(String[] args) {
        new NormalUser();
    }
}
