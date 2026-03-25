package panels;

import model.DataStore;
import model.Student;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;

/**
 * Dashboard panel showing summary/statistics.
 * 
 * ASSIGNED TO: Student 2 (Main Frame / Dashboard Owner)
 * 
 * TODO for Student 2:
 * - Display total number of students
 * - Add a welcome message or app logo
 * - Show summary statistics (e.g., average age, total count)
 * - Add a refresh button to update the stats
 * - Make it visually appealing (use colors, larger fonts, icons)
 */
public class DashboardPanel extends JPanel {
  private JLabel countLabel;
  private JLabel average;

  public DashboardPanel() {
    setLayout(new BorderLayout());

    // Welcome Message
    JLabel title = new JLabel("Welcome to the Student Management System!", SwingConstants.CENTER);
    title.setFont(new Font("Times New Roman", Font.BOLD, 28));
    title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    add(title, BorderLayout.NORTH);

    // Center content
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

    //Dashboard
      JLabel welcome_message = new JLabel("Dashboard");
      welcome_message.setFont(new Font("Arial", Font.BOLD, 20));
      welcome_message.setAlignmentX(Component.CENTER_ALIGNMENT);
      centerPanel.add(welcome_message);
      centerPanel.add(Box.createVerticalStrut(40));

    //Summary Statistics
      JLabel summary_statistics = new JLabel("Summary Statistics:");
      summary_statistics.setFont(new Font("Arial", Font.BOLD, 18));
      summary_statistics.setAlignmentX(Component.CENTER_ALIGNMENT);
      centerPanel.add(summary_statistics);
      centerPanel.add(Box.createVerticalStrut(20));

    countLabel = new JLabel("Total Students: " + DataStore.getInstance().getCount());
    countLabel.setFont(new Font("Arial", Font.PLAIN, 18));
    countLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    centerPanel.add(countLabel);

    centerPanel.add(Box.createVerticalStrut(20));

    //Average

          double sum = 0;
          double average_total = 0;
          for (int i = 0; i < DataStore.getInstance().getCount(); i++) {
              sum = DataStore.getInstance().getAllStudents().get(i).getAge() + sum;
          }

          average_total = sum / DataStore.getInstance().getCount();
          average = new JLabel("Average Age: " + average_total);

          average.setFont(new Font("Arial", Font.PLAIN, 18));
          average.setAlignmentX(Component.CENTER_ALIGNMENT);
          centerPanel.add(average);

          centerPanel.add(Box.createVerticalStrut(40));

      //Button
    JButton refreshBtn = new JButton("Refresh");
    refreshBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    refreshBtn.addActionListener(e -> refreshData());
    centerPanel.add(refreshBtn);



    add(centerPanel, BorderLayout.CENTER);
  }

  private void refreshData() {
    countLabel.setText("Total Students: " + DataStore.getInstance().getCount());
      double sum = 0;
      double average_total = 0;
      for (int i = 0; i < DataStore.getInstance().getCount(); i++) {
          sum = DataStore.getInstance().getAllStudents().get(i).getAge() + sum;
      }
      average_total = sum / DataStore.getInstance().getCount();
    average.setText("Average Age: " + average_total);
  }
}
