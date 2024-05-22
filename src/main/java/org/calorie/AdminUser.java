package org.calorie;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.calorie.CalorieTrackerApp.fileExists;
import static org.calorie.CalorieTrackerApp.isValidDate;

public class AdminUser {
    private String username;
    private boolean isAuthenticated = false;
    private Scanner scanner;
public AdminUser()
{
    scanner = new Scanner(System.in);
    System.out.print("Enter username: ");
    username = scanner.next();
    System.out.print("Enter password: ");
    String password = scanner.next();

    // Perform login authentication
    isAuthenticated = new User().authenticateUser(username, password, "admin");
    if (isAuthenticated) {
        System.out.println("Password Match");
        showMenu();
    } else {
        System.out.println("Password Not Match");
    }
}
    private void showMenu() {
        while (true) {
            System.out.println("1. Add user");
            System.out.println("2. Edit user");
            System.out.println("3. Delete user");
            System.out.println("4. View Normal User Food entry According to Date");
            System.out.println("5. View Normal User Food entry According to Week");
            System.out.println("6. View Normal User Food entry According to Monthly");
            System.out.println("7. Edit Normal User Food entry");
            System.out.println("8. Delete Normal User Food entry");
            System.out.println("9. Set normal user Goal");
            System.out.println("10. Edit normal user Goal");
            System.out.println("11. Delete normal user Goal");
            System.out.println("12. Set power user Goal");
            System.out.println("13. Edit power user Goal");
            System.out.println("14. Delete power user Goal");
            System.out.println("15. Set  Calorie Quantity");
            System.out.println("16. Edit Calorie Quantity");
            System.out.println("17. Delete Calorie Quantity");
            System.out.println("18. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                     addUser_1();
                    break;
                case 2:
                     editUser_2();
                    break;
                case 3:
                    deleteUser_3();
                    break;
                case 4:
                    queryByDateForNormalUser_4();
                    break;
                case 5:
                    queryByWeekForNormalUser_5();
                    break;
                case 6:
                    queryByMonthForNormalUser_6();
                    break;
                case 7:
                    editUserFoodEating_7();
                    break;
                case 8:
                    deleteUserFoodEating_8();
                    break;
                case 9:
                    setGoalForNormalUser_9();
                    break;
                case 10:
                    editGoalForNormalUser_10();
                    break;
                case 11:
                    deleteGoalForNormalUser_11();
                    break;
                case 12:
                    setGoalForPowerUser_12();
                    break;
                case 13:
                    editGoalForPowerUser_13();
                    break;
                case 14:
                    deleteGoalForPowerUser_14();
                    break;
                case 15:
                    setCalorieVitaminProtein_15();
                    break;
                case 16:
                    editCalorieVitaminProtein_16();
                    break;
                case 17:
                    deleteCalorieVitaminProtein_17();
                    break;
                case 18:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void addUser_1()
    {
        new User().ShowUserList();
        System.out.print("Enter new username: ");
        String newUsername = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        System.out.print("Enter user type (normal/power/admin): ");
        String userType = scanner.next();

        // Create a new User object
         if(new User().checkUsername(newUsername))
         {
             System.out.print("Sorry , this username already added");
         }
         else{
             new  User(newUsername,password,userType).addUser();
         }



    }
    public void editUser_2()
    {
        new User().ShowUserList();
        System.out.print("Enter new username: ");
        String newUsername = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        System.out.print("Enter user type (normal/power/admin): ");
        String userType = scanner.next();

        // Create a new User object
        if(new User().checkUsername(newUsername))
        {
            new  User(newUsername,password,userType).editUser();

        }
        else{
            System.out.print("Sorry , this username has no in the list");
        }


    }
    public void deleteUser_3()
    {
        new User().ShowUserList();
        System.out.print("Enter new username: ");
        String newUsername = scanner.next();


        // Create a new User object
        if(new User().checkUsername(newUsername))
        {
            new  User(newUsername,null,null).deleteUser();

        }
        else{
            System.out.print("Sorry , this username has no in the list");
        }


    }

    public void queryByDateForNormalUser_4(){
       new User().ShowUserList("normal");
        System.out.print("Enter normal username: ");
        String normalUsername = scanner.next();
        FoodEntryClass.DisplayFoodEntryList(normalUsername);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();
        if(isValidDate(date)){
            if(FoodEntryClass.checkFoodEntryByDate(normalUsername,date)){
                System.out.println("Goal of "+date+" :>  Target Calorie: "+Goal.goalForNormalUserByDate(normalUsername,date));
                FoodEntryClass.DisplayFoodEntryForNormalUserByDate(normalUsername,date);
            }
            else{
                System.out.println("Sorry, There is no food entry  for this date");
            }
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }

    }
    public void queryByWeekForNormalUser_5(){
        new User().ShowUserList("normal");
        System.out.print("Enter normal username: ");
        String normalUsername = scanner.next();

        Goal.DisplayGoalDataForNormalUser(normalUsername);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();

        if(isValidDate(date)){

            System.out.println("Goal of last week:>  Target Calorie: "+Goal.goalForNormalUserByNumberOfDays(normalUsername,date,7));
            FoodEntryClass.DisplayFoodEntryForNormalUserByNumberOfDays(normalUsername,date,7);
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }


    }
    public void queryByMonthForNormalUser_6(){
        new User().ShowUserList("normal");
        System.out.print("Enter normal username: ");
        String normalUsername = scanner.next();

        Goal.DisplayGoalDataForNormalUser(normalUsername);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();

        if(isValidDate(date)){
            System.out.println("Goal of last month:>  Target Calorie: "+Goal.goalForNormalUserByNumberOfDays(normalUsername,date,30));
            FoodEntryClass.DisplayFoodEntryForNormalUserByNumberOfDays(normalUsername,date,30);
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }



    }
    private void editUserFoodEating_7() {
       new User().ShowUserList();
        System.out.print("Enter normal username: ");
        String normalUsername = scanner.next();

        FoodEntryClass.DisplayFoodEntryList(normalUsername );
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
                if(new Goal().checkGoalDataByUsernameAndDate(normalUsername ,date)){ // check goal table
                    new FoodEntryClass(normalUsername ,foodName,foodAmount,date).editFoodEntry(sn-1);
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
    private void deleteUserFoodEating_8() {
      new  User().ShowUserList();
        System.out.print("Enter normal username: ");
        String normalUsername = scanner.next();

        FoodEntryClass.DisplayFoodEntryList(normalUsername);
        System.out.print("Enter Serial Number(SN): ");
        int sn=scanner.nextInt();
        // Save to file
        new FoodEntryClass(normalUsername,null,0,null).deleteFoodEntry(sn-1);

    }

    public void setGoalForNormalUser_9(){
        new User().ShowUserList("normal");
        System.out.print("Enter normal username: ");
        String normalUsername = scanner.next();

        Goal.DisplayGoalDataForNormalUser(normalUsername);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();
        System.out.print("Enter Calorie (In  Calorie): ");
        double calorieAmount = scanner.nextDouble();
        if(isValidDate(date)){
            // save data to txt file by checking
            if(new Goal().checkGoalDataByUsernameAndDate(normalUsername,date))
            {
                System.out.println("Sorry,the date is wrong and the date has been already inputted ");
            }
            else {
                new Goal(normalUsername,date,calorieAmount,0,0).saveGoal();
            }

        }
        else{
            System.out.println("Sorry, The date is invalid");
        }


    }

    public void editGoalForNormalUser_10(){
        new User().ShowUserList("normal");
        System.out.print("Enter normal username: ");
        String normalUsername = scanner.next();
        Goal.DisplayGoalDataForNormalUser(normalUsername);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();
        System.out.print("Enter Calorie (In  Calorie): ");
        double calorieAmount = scanner.nextDouble();
        if(isValidDate(date)){
// save data to txt file by checking
            if(new Goal().checkGoalDataByUsernameAndDate(normalUsername,date))
            {

                new Goal(normalUsername,date,calorieAmount,0,0).editGoal();
            }
            else {
                System.out.println("Sorry,this day goal have no in the list");
            }
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }



    }
    public void deleteGoalForNormalUser_11(){
        new User().ShowUserList("normal");
        System.out.print("Enter normal username: ");
        String normalUsername = scanner.next();

        Goal.DisplayGoalDataForNormalUser(normalUsername);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();
        if(isValidDate(date)){
// save data to txt file by checking
            if(new Goal().checkGoalDataByUsernameAndDate(normalUsername,date))
            {

                new Goal(normalUsername,date,0,0,0).deleteGoal();
            }
            else {
                System.out.println("Sorry,this day goal have no in the list");
            }
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }




    }
    public void setGoalForPowerUser_12(){
        new User().ShowUserList("power");
        System.out.print("Enter normal username: ");
        String powerUsername = scanner.next();

        Goal.DisplayGoalDataForPowerUser(powerUsername);
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
            if(new Goal().checkGoalDataByUsernameAndDate(powerUsername,date))
            {
                System.out.println("Sorry,the date is wrong and the date has been already inputted ");
            }
            else {
                new Goal(powerUsername,date,calorieAmount,vitaminAmount,proteinAmount).saveGoal();
            }
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }



    }
    public void editGoalForPowerUser_13(){
        new User().ShowUserList("power");
        System.out.print("Enter normal username: ");
        String powerUsername = scanner.next();
        Goal.DisplayGoalDataForPowerUser(powerUsername);
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
            if(new Goal().checkGoalDataByUsernameAndDate(powerUsername,date))
            {

                new Goal(powerUsername,date,calorieAmount,vitaminAmount,proteinAmount).editGoal();
            }
            else {
                System.out.println("Sorry,this day goal have no in the list");
            }
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }



    }
    public void deleteGoalForPowerUser_14(){
        new User().ShowUserList("power");
        System.out.print("Enter normal username: ");
        String powerUsername = scanner.next();
        Goal.DisplayGoalDataForPowerUser(powerUsername);
        System.out.print("Enter date (Format: YYYY-MM-DD): ");
        String date = scanner.next();
        if(isValidDate(date)){
// save data to txt file by checking
            if(new Goal().checkGoalDataByUsernameAndDate(powerUsername,date))
            {

                new Goal(powerUsername,date,0,0,0).deleteGoal();
            }
            else {
                System.out.println("Sorry,this day goal have no in the list");
            }
        }
        else{
            System.out.println("Sorry, The date is invalid");
        }



    }
    public void setCalorieVitaminProtein_15()
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
    public  void editCalorieVitaminProtein_16()
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
    public  void deleteCalorieVitaminProtein_17()
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
}
