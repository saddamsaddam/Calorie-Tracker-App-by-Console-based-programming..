package org.calorie;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.calorie.CalorieTrackerApp.fileExists;
import static org.calorie.CalorieTrackerApp.isValidDate;

public class PowerUser {
    private String username;
    private boolean isAuthenticated = false;
    private Scanner scanner;
    public PowerUser()
    {
        scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        // Perform login authentication
        isAuthenticated = new User().authenticateUser(username, password, "power");
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
            System.out.println("2. View According to Date");
            System.out.println("3. View According to Week");
            System.out.println("4. View According to Monthly");
            System.out.println("5. Set Goal");
            System.out.println("6. Edit Goal");
            System.out.println("7. Delete Goal");
            System.out.println("8. Set Calorie Quantity");
            System.out.println("9. Edit Calorie Quantity");
            System.out.println("10. Delete Calorie Quantity");
            System.out.println("11. Import");
            System.out.println("12. Export");
            System.out.println("13. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    create_1();
                    break;
                case 2:
                    queryByDateForPowerUser_2();
                    break;
                case 3:
                    queryByWeekForPowerUser_3();
                    break;
                case 4:
                    queryByMonthForPowerUser_4();
                    break;
                case 5:
                    setGoalForPowerUser_5();
                    break;
                case 6:
                    editGoalForPowerUser_6();
                    break;
                case 7:
                    deleteGoalForPowerUser_7();

                    break;
                case 8:
                    setCalorieVitaminProtein_8();
                    break;
                case 9:
                    editCalorieVitaminProtein_9();
                    break;
                case 10:
                    deleteCalorieVitaminProtein_10();
                    break;
                case 11:
                    import_11();
                    break;
                case 12:
                    export_12();
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
        FoodCalorieClass.foodCalorieDisplayForPowerUser();
        System.out.print("Enter food name: ");
        String foodName = scanner.next();
        System.out.print("Enter food amount (In Gram): ");
        double foodAmount = scanner.nextDouble();
        Goal.DisplayGoalDataForPowerUser(username);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();
        if(isValidDate(date)){
            // Save to file
            // check food in calorie quantity list
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
    public void queryByDateForPowerUser_2(){
        FoodEntryClass.DisplayFoodEntryList(username);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();

        if(isValidDate(date)){
            if(FoodEntryClass.checkFoodEntryByDate(username,date)){
                double[] data=Goal.goalForPowerUserByDate(username,date);
                System.out.println("Goal of "+date+" :>\nTarget :\nCalorie: "+data[0]+"\nVitamin:"+data[1]+"\nProtein:"+data[2]);
                FoodEntryClass.DisplayFoodEntryForPowerUserByDate(username,date);
            }
            else{
                System.out.println("Sorry, There is no food entry  for this date");
            }
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }


    }
    public void queryByWeekForPowerUser_3(){
        Goal.DisplayGoalDataForPowerUser(username);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();
        if(isValidDate(date)){
            double[] data=Goal.goalForPowerUserByNumberOfDays(username,date,7);
            System.out.println("Goal of last week :>\nTarget :\nCalorie: "+data[0]+"\nVitamin:"+data[1]+"\nProtein:"+data[2]);
            FoodEntryClass.DisplayFoodEntryForPowerUserByNumberOfDays(username,date,7);
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }


    }
    public void queryByMonthForPowerUser_4(){
        Goal.DisplayGoalDataForPowerUser(username);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();
        if(isValidDate(date)){
            double[] data=Goal.goalForPowerUserByNumberOfDays(username,date,30);
            System.out.println("Goal of last month :>\nTarget :\nCalorie: "+data[0]+"\nVitamin:"+data[1]+"\nProtein:"+data[2]);
            FoodEntryClass.DisplayFoodEntryForPowerUserByNumberOfDays(username,date,30);
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }


    }

    public void setGoalForPowerUser_5(){
        Goal.DisplayGoalDataForPowerUser(username);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();
        System.out.print("Enter Calorie (In  Calorie): ");
        double calorieAmount = scanner.nextDouble();

        System.out.print("Enter Vitamin (In Gram): ");
        double vitaminAmount = scanner.nextDouble();
        System.out.print("Enter Protein (In Gram): ");
        double proteinAmount = scanner.nextDouble();
        if(isValidDate(date)){
// save data to txt file by checking
            if(new Goal().checkGoalDataByUsernameAndDate(username,date))
            {
                System.out.println("Sorry,the date is wrong and the date has been already inputted ");
            }
            else {
                new Goal(username,date,calorieAmount,vitaminAmount,proteinAmount).saveGoal();
            }
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }



    }
    public void editGoalForPowerUser_6(){
        Goal.DisplayGoalDataForPowerUser(username);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();
        System.out.print("Enter Calorie (In  Calorie): ");
        double calorieAmount = scanner.nextDouble();
        System.out.print("Enter Vitamin (In Gram): ");
        double vitaminAmount = scanner.nextDouble();
        System.out.print("Enter Protein (In Gram): ");
        double proteinAmount = scanner.nextDouble();
        if(isValidDate(date)){
// save data to txt file by checking
            if(new Goal().checkGoalDataByUsernameAndDate(username,date))
            {

                new Goal(username,date,calorieAmount,vitaminAmount,proteinAmount).editGoal();
            }
            else {
                System.out.println("Sorry,this day goal have no in the list");
            }
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }



    }
    public void deleteGoalForPowerUser_7(){
        Goal.DisplayGoalDataForPowerUser(username);
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

    public void setCalorieVitaminProtein_8()
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
    public  void editCalorieVitaminProtein_9()
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
    public  void deleteCalorieVitaminProtein_10()
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
    public void import_11() {
        while (true) {
            System.out.println("1. import Goal");
            System.out.println("2. import Food Entry");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Code to handle exporting goal
                    System.out.println("importing Goal...");
                    new Goal().importGoal(username);
                    break;
                case 2:
                    // Code to handle exporting food entry
                    System.out.println("importing Food Entry...");
                    new FoodEntryClass().importFoodEntry(username);
                    break;
                case 3:
                    // Code to handle exporting food entry
                    System.out.println("Exiting..");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }

    }
    public void export_12() {
        while (true) {
            System.out.println("1. Export Goal");
            System.out.println("2. Export Food Entry");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Code to handle exporting goal
                    System.out.println("Exporting Goal...");
                    new Goal().exportGoal(username);
                    break;
                case 2:
                    // Code to handle exporting food entry
                    System.out.println("Exporting Food Entry...");
                    new FoodEntryClass().exportFoodEntry(username);
                    break;
                case 3:
                    // Code to handle exporting food entry
                    System.out.println("Exiting..");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }

    }
}
