package com.todolist.frames;

import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.sql.Connection;

import com.todolist.listeners.TaskMovedListener;
import com.todolist.models.Task;
import com.todolist.models.Team;
import com.todolist.models.User;
import com.todolist.services.TaskService;

public class TaskFrame extends JFrame implements TaskMovedListener {
  TaskService taskService;
  JPanel[] tasksPanels = new JPanel[TaskStatus.values().length]; // [todo, pending, in progress, completed]

  public TaskFrame(Connection connection){
    super("Tasks");

    taskService = new TaskService(connection);

    setSize(1080, 720);
    // Set the default close operation
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    // Center the frame on the screen
    setLocationRelativeTo(null);
    setResizable(false);

    JPanel panel = new JPanel(new GridLayout(1, TaskStatus.values().length, 10, 10));
    panel.setBorder(new EmptyBorder(20, 20, 20, 20));
    add(panel);

    int i = 0;
    for (TaskStatus status : TaskStatus.values()){
      JPanel columnPanel = new JPanel();
      BoxLayout boxLayout = new BoxLayout(columnPanel, BoxLayout.Y_AXIS);
      columnPanel.setLayout(boxLayout);
      
      JLabel statusLabel = new JLabel(status.getValue());
      statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      
      JPanel tasksPanel = new JPanel();
      BoxLayout boxLayout2 = new BoxLayout(tasksPanel, BoxLayout.Y_AXIS);
      tasksPanel.setLayout(boxLayout2);
      tasksPanel.setBorder(new EmptyBorder(4, 4, 4, 4));

      columnPanel.add(statusLabel);
      columnPanel.add(tasksPanel);

      tasksPanels[i++] = tasksPanel;
      panel.add(columnPanel);
    }
  }

  public void open(User user, Team team){
    setVisible(true);

    try {
      ArrayList<Task> tasks = taskService.getTasks(team.team_id);
      renderTask(tasks);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void renderTask(ArrayList<Task> tasks){
    for (JPanel tasksPanel : tasksPanels){
      tasksPanel.removeAll();
    }

    for (Task task : tasks){
      int i = 0;
      for (TaskStatus taskStatus : TaskStatus.values()){
        if (task.status.equals(taskStatus.getValue())){

          TaskPanel taskPanel = new TaskPanel(task, taskService, tasksPanels);
          taskPanel.addTaskMovedListeners(this);

          tasksPanels[i].add(taskPanel);
        };
        i++;
      }
    }

    for (JPanel tasksPanel : tasksPanels){
      tasksPanel.add(
        new Box.Filler(new Dimension(0, 0), 
        new Dimension(0, Short.MAX_VALUE), new Dimension(0, Short.MAX_VALUE))
      );
    }

    revalidate();
    repaint();
  }

  @Override
  public void onMoved(String moveDirection, TaskPanel taskPanel) {
    try {
      Task task = taskPanel.task;
      int length = TaskStatus.values().length;

      for (int i = 0; i < length; i++){
        if (TaskStatus.values()[i].getValue().equals(task.status)){

          if (i == 0 && moveDirection.equals("moveLeft")){
            return;
          }

          if (i == length - 1  && moveDirection.equals("moveRight")){
            return;
          }

          int newStatusIndex = moveDirection.equals("moveLeft") ? i - 1 : i + 1;
          String newStatus = TaskStatus.values()[newStatusIndex].getValue();

          Task newTask = new Task(task.task_id, task.judul, task.deskripsi, newStatus, task.prioritas, task.team_id, task.user_id, task.tanggal_tugas);
          taskService.updateTask(task.task_id, newTask);
          ArrayList<Task> tasks = taskService.getTasks(taskPanel.task.team_id);
    
          renderTask(tasks);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

enum TaskStatus {

  TODO("todo"), PENDING("pending"), IN_PROGRESS("in progress"), COMPLETED("completed");

  private String value;
  private TaskStatus(String value){
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
