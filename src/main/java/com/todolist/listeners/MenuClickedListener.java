package com.todolist.listeners;

public interface MenuClickedListener {
  static enum MENU_ID { LIHAT_TUGAS, BUAT_TUGAS };

  public void menuClicked(MENU_ID menuId);
}
