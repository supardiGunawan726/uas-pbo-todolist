package com.todolist.models;

public class Team {
  public int team_id;
  public String nama_team;
  public String deskripsi;

  public Team(int team_id, String nama_team, String deskripsi) {
    this.team_id = team_id;
    this.nama_team = nama_team;
    this.deskripsi = deskripsi;
  }
}
