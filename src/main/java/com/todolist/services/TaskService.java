package com.todolist.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.todolist.models.Task;

public class TaskService {
  Connection connection;

  public TaskService(Connection connection) {
    this.connection = connection;
  }

  public ArrayList<Task> getTasks(int teamId) throws Exception {
    String query = "SELECT * FROM Task where team_id = ?";

    PreparedStatement ps = connection.prepareStatement(query);
    ps.setInt(1, teamId);
    
    ResultSet rs = ps.executeQuery();

    ArrayList<Task> tasks = new ArrayList<>();

    while (rs.next()) {
      int task_id = rs.getInt("task_id");
      String judul = rs.getString("judul");
      String deskripsi = rs.getString("deskripsi");
      String status = rs.getString("status");
      int prioritas = rs.getInt("prioritas");
      int team_id = rs.getInt("team_id");
      int user_id = rs.getInt("user_id");
      Date tanggal_tugas = rs.getDate("tanggal_tugas");

      Task task = new Task(task_id, judul, deskripsi, status, prioritas, team_id, user_id, tanggal_tugas);
      tasks.add(task);
    }

    return tasks;
  }

  public boolean updateTask(int taskId, Task newTask) throws Exception {
      String query = "UPDATE Task\n" + //
            "SET judul = ?,\n" + //
            "    deskripsi = ?,\n" + //
            "    status = ?,\n" + //
            "    prioritas = ?,\n" + //
            "    team_id = ?,           -- misalnya ID tim Marketing\n" + //
            "    user_id = ?,           -- misalnya ID user Charlie\n" + //
            "    tanggal_tugas = ?\n" + //
            "WHERE task_id = ?;\n" + //
            "";

      PreparedStatement ps = connection.prepareStatement(query);
      ps.setString(1, newTask.judul);
      ps.setString(2, newTask.deskripsi);
      ps.setString(3, newTask.status);
      ps.setInt(4, newTask.prioritas);
      ps.setInt(5, newTask.team_id);
      ps.setInt(6, newTask.user_id);
      ps.setDate(7, newTask.tanggal_tugas);
      ps.setInt(8, taskId);
      
      return ps.executeUpdate() > 0;
  }

  public boolean addTask(String judul, String deskripsi, String status, int prioritas, int team_id, int user_id, Date tanggal_tugas) throws Exception {
    String query = "INSERT INTO Task (judul, deskripsi, status, prioritas, team_id, user_id, tanggal_tugas) VALUES (?, ?, ?, ?, ?, ?, ?)";

    PreparedStatement ps = connection.prepareStatement(query);

    ps.setString(1, judul);
    ps.setString(2, deskripsi);
    ps.setString(3, status);
    ps.setInt(4, prioritas);
    ps.setInt(5, team_id);
    ps.setInt(6, user_id);
    ps.setDate(7, tanggal_tugas);
    
    return ps.executeUpdate() > 0;
  }
}
