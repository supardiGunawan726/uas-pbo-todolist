package com.todolist.frames;

import javax.swing.*;

import com.todolist.models.User;
import com.todolist.services.TaskService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;

public class AddTaskFrame extends JFrame {
    private JTextField judulField;
    private JTextField deskripsiField;
    private JComboBox<String> statusField;
    private JComboBox<String> prioritasField;
    private JTextField tanggalTugasField;
    private JButton addButton;
    private TaskService taskService;

    public AddTaskFrame(Connection connection) {
        super("Tambah Task Baru");

        this.taskService = new TaskService(connection);

        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel judulLabel = new JLabel("Judul:");
        judulLabel.setBounds(20, 20, 100, 25);
        add(judulLabel);

        judulField = new JTextField();
        judulField.setBounds(130, 20, 200, 25);
        add(judulField);

        JLabel deskripsiLabel = new JLabel("Deskripsi:");
        deskripsiLabel.setBounds(20, 60, 100, 25);
        add(deskripsiLabel);

        deskripsiField = new JTextField();
        deskripsiField.setBounds(130, 60, 200, 25);
        add(deskripsiField);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(20, 100, 100, 25);
        add(statusLabel);

        String[] statuses = new String[TaskStatus.values().length];
        for (int i = 0; i < statuses.length; i++){
          statuses[i] = TaskStatus.values()[i].getValue();
        }
        statusField = new JComboBox<>(statuses);
        statusField.setBounds(130, 100, 200, 25);
        add(statusField);

        JLabel prioritasLabel = new JLabel("Prioritas:");
        prioritasLabel.setBounds(20, 140, 100, 25);
        add(prioritasLabel);

        String[] priorieties = {"Rendah", "Sedang", "Tinggi"};
        prioritasField = new JComboBox<>(priorieties);
        prioritasField.setBounds(130, 140, 200, 25);
        add(prioritasField);

        JLabel tanggalTugasLabel = new JLabel("Tanggal (YYYY-MM-DD):");
        tanggalTugasLabel.setBounds(20, 180, 150, 25);
        add(tanggalTugasLabel);

        tanggalTugasField = new JTextField();
        tanggalTugasField.setBounds(180, 180, 150, 25);
        add(tanggalTugasField);

        addButton = new JButton("Tambah Task");
        addButton.setBounds(130, 220, 200, 30);
        add(addButton);
    }

    public void open(User user){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

              try {
                String judul = judulField.getText();
                String deskripsi = deskripsiField.getText();
                String status = (String) statusField.getSelectedItem();
                int prioritas = prioritasField.getSelectedIndex() + 1;
                String tanggalTugas = tanggalTugasField.getText();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date tanggalTugasDate = simpleDateFormat.parse(tanggalTugas);
                java.sql.Date tanggalTugasSqlDate = new java.sql.Date(tanggalTugasDate.getTime());

                boolean success = taskService.addTask(judul, deskripsi, status, prioritas, user.team_id, user.user_id, tanggalTugasSqlDate);

                if (success){
                  JOptionPane.showMessageDialog(null, "Task berhasil ditambahkan!");
                  judulField.setText("");
                  deskripsiField.setText("");
                  statusField.setSelectedIndex(0);
                  prioritasField.setSelectedIndex(0);
                  tanggalTugasField.setText("");
                }else{
                  JOptionPane.showMessageDialog(null, "Task gagal ditambahkan!");
                }
              
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
        });
        setVisible(true);
    }
}

