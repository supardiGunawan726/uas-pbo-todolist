package com.todolist;

import java.sql.Connection;

import com.todolist.frames.AddTaskFrame;
import com.todolist.frames.LoginFrame;
import com.todolist.frames.MenuFrame;
import com.todolist.frames.TaskFrame;
import com.todolist.listeners.LoginSuccessListener;
import com.todolist.listeners.MenuClickedListener;
import com.todolist.models.Team;
import com.todolist.models.User;
import com.todolist.services.TeamService;
import com.todolist.services.UserService;

public class Main {

    public static void main(String[] args) {
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.connect();

            LoginFrame loginFrame = new LoginFrame(connection);
            MenuFrame menuFrame = new MenuFrame();
            TaskFrame taskFrame = new TaskFrame(connection);
            AddTaskFrame addTaskFrame = new AddTaskFrame(connection);

            loginFrame.setVisible(true);

            AppListener appListener = new AppListener(connection, loginFrame, menuFrame, taskFrame, addTaskFrame);
            loginFrame.addLoginSuccessListeners(appListener);
            menuFrame.addMenuClickedListeners(appListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class AppListener implements LoginSuccessListener, MenuClickedListener {
    LoginFrame loginFrame;
    MenuFrame menuFrame;
    TaskFrame taskFrame;
    AddTaskFrame addTaskFrame;
    UserService userService;
    TeamService teamService;
    User user;
    Team team;

    public AppListener(Connection connection, LoginFrame loginFrame, MenuFrame menuFrame, TaskFrame taskFrame, AddTaskFrame addTaskFrame) {
        userService = new UserService(connection);
        teamService = new TeamService(connection);

        this.loginFrame = loginFrame;
        this.menuFrame = menuFrame;
        this.taskFrame = taskFrame;
        this.addTaskFrame = addTaskFrame;
    }

    @Override
    public void loginSuccess(int userId) {
        try {
            user = userService.getUser(userId);
            team = teamService.getTeam(user.team_id);

            menuFrame.open(user, team);
            loginFrame.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void menuClicked(MENU_ID menuId) {
        if (menuId == MENU_ID.LIHAT_TUGAS){
            taskFrame.open(user, team);
        }else if (menuId == MENU_ID.BUAT_TUGAS){
            addTaskFrame.open(user);
        }
    }
}