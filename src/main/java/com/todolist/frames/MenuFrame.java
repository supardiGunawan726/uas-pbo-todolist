package com.todolist.frames;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.todolist.listeners.MenuClickedListener;
import com.todolist.models.Team;
import com.todolist.models.User;

public class MenuFrame extends JFrame {
  ArrayList<MenuClickedListener> menuClickedListeners = new ArrayList<MenuClickedListener>();
  JPanel panel;

  public MenuFrame(){
    super("Menu");

    setSize(500, 400);
    // Set the default close operation
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Center the frame on the screen
    setLocationRelativeTo(null);

    // Create a panel to hold the components
    panel = new JPanel(new GridLayout(3, 1));
    panel.setBorder(new EmptyBorder(0, 24, 0, 24));
  }

  public void open(User user, Team team){
    panel.removeAll();
    revalidate();
    repaint();
    
    // Create a label for the title
    JLabel titleLabel = new JLabel("Selamat datang " + user.nama + " (" + team.nama_team + ")", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Serif", Font.BOLD, 20));

    panel.add(titleLabel);

    // Create a panel for the buttons
    JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));

     // Create the "Lihat Tugas" button
    JButton lihatTugasButton = new JButton("Lihat Tugas");

    lihatTugasButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (MenuClickedListener listener : menuClickedListeners){
              listener.menuClicked(MenuClickedListener.MENU_ID.LIHAT_TUGAS);
            }
        }
    });
        
    // Create the "Buat Tugas" button
    JButton buatTugasButton = new JButton("Buat Tugas");
    buatTugasButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          for (MenuClickedListener listener : menuClickedListeners){
            listener.menuClicked(MenuClickedListener.MENU_ID.BUAT_TUGAS);
          }
        }
    });
        
    // Add the buttons to the button panel
    buttonPanel.add(lihatTugasButton);
    buttonPanel.add(buatTugasButton);
    
    // Add the button panel to the main panel
    panel.add(buttonPanel);
    
    // Add the main panel to the frame
    add(panel);

    setVisible(true);
  }

  public void addMenuClickedListeners(MenuClickedListener menuClickedListener){
    menuClickedListeners.add(menuClickedListener);
  }
}
