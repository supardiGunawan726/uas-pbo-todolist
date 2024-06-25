package com.todolist.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.todolist.models.Team;

public class TeamService {
    Connection connection;

    public TeamService(Connection connection) {
      this.connection = connection;
    }

    public Team getTeam(int teamId) throws Exception {
      String query = "SELECT * FROM Team where team_id = ?";

      PreparedStatement ps = connection.prepareStatement(query);
      ps.setInt(1, teamId);
      
      ResultSet rs = ps.executeQuery();

      Team team = null;
      while (rs.next()) {
        int team_id = rs.getInt("team_id");
        String nama_team = rs.getString("nama_team");
        String deskripsi = rs.getString("deskripsi");

        team = new Team(team_id, nama_team, deskripsi);
      }

      return team;
    }
}
