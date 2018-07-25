package com.company.api;

import com.company.dao.UserDao;
import com.company.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("/user")
public class UserApi {

    private Gson gson = new GsonBuilder().create();
    private final String FILE_NAME = "C:" + File.separator + "save.txt";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers() {
        return readFile();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addNewUser(@FormParam("firstName") String firstName,
                           @FormParam("secondName") String secondName,
                           @FormParam("age") int age) {
        User user = new User(firstName, secondName, age);
        UserDao.getInstance().addUser(user);
        saveToFile();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(String jsonUser) {
        try {
            User user = gson.fromJson(jsonUser, User.class);
            if (UserDao.getInstance().update(user)) {
                saveToFile();
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @DELETE
    @Path("/{secondName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUser(@PathParam("secondName") String secondName) {
        if (UserDao.getInstance().remove(secondName)) {
            saveToFile();
            String json = "{\"result\" : \"Removed user with second name: " + secondName + "\"}";
            return Response.status(Response.Status.OK).entity(json).build();
        } else {
            String json = "{\"result\" : \"User not found: " + secondName + "\"}";
            return Response.status(Response.Status.NOT_FOUND).entity(json).build();
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(gson.toJson(UserDao.getInstance().getUsers()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile() {
        StringBuilder gsonString = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            gsonString.append(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gsonString.toString();
    }
}