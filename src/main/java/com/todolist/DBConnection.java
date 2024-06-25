package com.todolist;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
      final String jdbc = "com.mysql.jdbc.Driver";
      final String url = "jdbc:mysql://localhost:3306/todolist";
      final String username = "root";
      final String password = "12345678";

      public Connection connect() throws Exception {
        Class.forName(jdbc);

        Connection connection = DriverManager.getConnection(url, username, password);
        
        return connection;
      }
}
