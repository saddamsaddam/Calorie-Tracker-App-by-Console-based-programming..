package org.calorie;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.calorie.CalorieTrackerApp.fileExists;

public class User {
    private  String username;
    private  String password;
    private  String userType;
    // Other user attributes
public User()
{

}

    public User(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    // Getter methods
    // Getter methods

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }

    public static boolean authenticateUser(String username, String password,String type) {
        try (Scanner fileScanner = new Scanner(new File("registration.txt"))) {
            // Skip header line
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine(); // Skip header
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if(parts.length==3)
                {
                    String storedUsername = parts[0];
                    String storedPassword = parts[1];
                    String storedType = parts[2];

                    // Check if the entered username and password match any stored credentials
                    if (storedUsername.equals(username) && storedPassword.equals(password)&& storedType.equals(type)) {
                        return true; // Authentication successful
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Registration file not found.");
        }
        return false; // Authentication failed
    }

    public static List<User> retrieveUser() {
        List<User> userList = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File("registration.txt"))) {
            // Skip header line
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine(); // Skip header
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if(parts.length==3){
                    String username = parts[0];
                    String password = parts[1];
                    String userType = parts[2];

                    User user = new User(username, password, userType);
                   // System.out.println(username+"  "+password+" "+userType);
                    userList.add(user);
                  //  System.out.println(userList.size());
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Registration file not found.");
        }
        return userList;
    }
 public  void ShowUserList(String userType)
 {
     List<User> users=retrieveUser();
     System.out.println(userType+" user name list  :>");
     users.forEach(e->{
         if(e.getUserType().equals(userType)){
             System.out.println(e.toString());
         }
     });
 }
    public  void ShowUserList()
    {
        List<User> users=retrieveUser();
       // System.out.println(Arrays.deepToString(users.toArray()));
        System.out.println("user name list Snvn:>");
        users.forEach(e->{
            System.out.println(e.getUsername());
        });
    }

    public  void addUser()
    {
        // Write user information to registration file
        try {
            FileWriter writer = new FileWriter("registration.txt", true); // Append mode
            if (!fileExists("registration.txt")) {
                writer.write("username,password,userType\n"); // Header
            }
            writer.write(username + "," + password + "," + userType + "\n");
            writer.close();
            System.out.println("Registration successful!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public  void editUser()
    {
        List<User> users = retrieveUser();
        removeAllDataExceptHeader();
        users.forEach(e->{
            try {
                FileWriter writer = new FileWriter("registration.txt", true); // Append mode
                if (!fileExists("registration.txt")) {
                    writer.write("username,password,userType\n"); // Header
                }
                if(username.equals(e.getUsername())){
                    writer.write(username + "," + password + "," + userType + "\n");
                }
                else{
                    writer.write(e.getUsername() + "," + e.getPassword() + "," + e.getUserType() + "\n");
                }

                writer.close();

            } catch (IOException f) {
                System.out.println("An error occurred while writing to the file: " + f.getMessage());
            }
        });
        // Write user information to registration file
        System.out.println("User updated successfully!");
    }

    public  void deleteUser()
    {
        List<User> users = retrieveUser();
        removeAllDataExceptHeader();
        users.forEach(e->{
            try {
                FileWriter writer = new FileWriter("registration.txt", true); // Append mode
                if (!fileExists("registration.txt")) {
                    writer.write("username,password,userType\n"); // Header
                }
                if(username.equals(e.getUsername())){
                    // nothing do
                }
                else{
                    writer.write(e.getUsername() + "," + e.getPassword() + "," + e.getUserType() + "\n");
                }

                writer.close();

            } catch (IOException f) {
                System.out.println("An error occurred while writing to the file: " + f.getMessage());
            }
        });
        System.out.println("User deleted successfully!");
        // Write user information to registration file

    }

    // Assuming User class exists with getUsername() method
    public boolean checkUsername(String username) {
        // Retrieve list of users
        List<User> users = retrieveUser();
        users.forEach(e->{
            System.out.println(e.toString());
        });
        // Iterate through the list of users
        for (User user : users) {
            // Check if the username matches
            if (user.getUsername().equals(username)) {
                // Username found, return true
                return true;
            }
        }

        // Username not found, return false
        return false;
    }

    public void removeAllDataExceptHeader() {
        try {
            File inputFile = new File("registration.txt");
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
