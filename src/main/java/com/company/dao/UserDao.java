package com.company.dao;

import com.company.model.User;
import com.company.model.UserList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static UserDao instance;
    private static List<User> users = new ArrayList<>();
    private static final String FILE_NAME = "C:" + File.separator + "save.txt";
    private static Gson gson = new GsonBuilder().create();

    static{
        File file = new File(FILE_NAME);
        if(file.exists()) {
            StringBuilder gsonString = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                gsonString.append(reader.readLine());
                UserList userList = gson.fromJson(gsonString.toString(), UserList.class);
                users = userList.getUsers();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private UserDao() {
    }

    public static synchronized UserDao getInstance() {
        if(instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
        saveToFile();
    }

    public UserList getUsers() {
        return new UserList(users);
    }

    public boolean remove(String secondName) {
        for (User user: users) {
            if(user.getSecondName().equalsIgnoreCase(secondName)) {
                users.remove(user);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean update(User testUser) {
        for (User user: users) {
            if(user.getSecondName().equalsIgnoreCase(testUser.getSecondName())) {
                user.setFirstName(testUser.getFirstName());
                user.setAge(testUser.getAge());
                saveToFile();
                return true;
            }
        }
        return false;
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(gson.toJson(getUsers()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}