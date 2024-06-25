package com.todolist.frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.todolist.listeners.TaskMovedListener;
import com.todolist.models.Task;
import com.todolist.services.TaskService;

public class TaskPanel extends JPanel implements ActionListener {
  Task task;
  TaskService taskService;
  JPanel[] tasksPanels; 
  ArrayList<TaskMovedListener> taskMovedListeners = new ArrayList<>();

  public TaskPanel(Task task, TaskService taskService, JPanel[] tasksPanels){
    super();

    this.task = task;
    this.taskService = taskService;
    this.tasksPanels = tasksPanels;
    setLayout(new BorderLayout());
    setBorder(new EmptyBorder(4, 4,4, 4));

    JPanel containerPanel = new JPanel();
    containerPanel.setBackground(Color.WHITE);
    containerPanel.setLayout(new GridLayout(3, 1, 10, 10));
    containerPanel.setBorder(new EmptyBorder(6, 6, 6, 6));

    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(containerPanel.getBackground());
    headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.PAGE_AXIS));

    JLabel dateLabel = new JLabel(formatDate(task.tanggal_tugas), JLabel.CENTER);
    dateLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 8));

    JLabel titleLabel = new JLabel(task.judul, JLabel.CENTER);

    headerPanel.add(dateLabel);
    headerPanel.add(titleLabel);
    containerPanel.add(headerPanel);

    JLabel descriptionLabel = new JLabel("<html>" + task.deskripsi + "</html>");
    descriptionLabel.setMaximumSize(new Dimension(containerPanel.getWidth(), 50));
    containerPanel.add(descriptionLabel);

    JPanel footerPanel = new JPanel();
    JButton moveLeft = new JButton("<");
    moveLeft.setName("moveLeft");
    moveLeft.addActionListener(this);

    JButton moveRight = new JButton(">");
    moveRight.setName("moveRight");
    moveRight.addActionListener(this);

    footerPanel.add(moveLeft);
    footerPanel.add(moveRight);
    containerPanel.add(footerPanel);

    add(containerPanel, BorderLayout.CENTER);
  }

  String formatDate(Date date){
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
    return sdf.format(date);
  }

  void addTaskMovedListeners(TaskMovedListener taskMovedListener){
    taskMovedListeners.add(taskMovedListener);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String name = ((JButton) e.getSource()).getName();

    for (TaskMovedListener taskMovedListener : taskMovedListeners){
      taskMovedListener.onMoved(name, this);
    }

  }
}
