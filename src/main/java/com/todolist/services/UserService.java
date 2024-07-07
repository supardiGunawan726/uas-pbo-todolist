package com.todolist.services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

import com.todolist.models.User;

public class UserService {
  Connection connection;

  public UserService(Connection connection) {
    this.connection = connection;
  }

  public int login(String email, String password) throws Exception {
    String query = "{? = call login(?, ?)}";
    
    CallableStatement cs = connection.prepareCall(query);
    cs.registerOutParameter(1, Types.VARCHAR);
    cs.setString(2, email);
    cs.setString(3, password);
    cs.execute();

    return cs.getInt(1);
  }

  public User getUser(int userId) throws Exception {
    String query = "SELECT * FROM User where user_id = ?";

    PreparedStatement ps = connection.prepareStatement(query);
    ps.setInt(1, userId);
    
    ResultSet rs = ps.executeQuery();

    User user = null;
    while (rs.next()) {
      String nama = rs.getString("nama");
      String email = rs.getString("email");
      int teamId = rs.getInt("team_id");

      user = new User(userId, nama, email, teamId);
    }

    return user;
  }

  public ArrayList<User> getTasks(int teamId) throws Exception {
    String query = "SELECT * FROM User where team_id = ?";

    PreparedStatement ps = connection.prepareStatement(query);
    ps.setInt(1, teamId);
    
    ResultSet rs = ps.executeQuery();

    ArrayList<User> users = new ArrayList<>();

    while (rs.next()) {
      int userId = rs.getInt("user_id");
      String nama = rs.getString("nama");
      String email = rs.getString("email");

      User user = new User(userId, nama, email, teamId);

      users.add(user);
    }

    return users;
  }
}
