package com.todolist.models;

public class User {
  public int user_id;
  public String nama;
  public String email;
  public int team_id;

  public User(int user_id, String nama, String email, int team_id) {
    this.user_id = user_id;
    this.nama = nama;
    this.email = email;
    this.team_id = team_id;
  }
}
