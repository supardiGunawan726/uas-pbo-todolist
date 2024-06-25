package com.todolist.models;

import java.sql.Date;

public class Task {
  public int task_id;
  public String judul;
  public String deskripsi;
  public String status;
  public int prioritas;
  public int team_id;
  public int user_id;
  public Date tanggal_tugas;
  
  public Task(int task_id, String judul, String deskripsi, String status, int prioritas, int team_id, int user_id,
      Date tanggal_tugas) {
    this.task_id = task_id;
    this.judul = judul;
    this.deskripsi = deskripsi;
    this.status = status;
    this.prioritas = prioritas;
    this.team_id = team_id;
    this.user_id = user_id;
    this.tanggal_tugas = tanggal_tugas;
  }
}
