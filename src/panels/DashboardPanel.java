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

  public DashboardPanel() {
    setLayout(new BorderLayout());

    // Title
    JLabel title = new JLabel("Dashboard", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 28));
    title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
    add(title, BorderLayout.NORTH);

    // Center content
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    //Summary Statistics
      JLabel summary_statistics = new JLabel("Summary Statistics:");
      summary_statistics.setFont(new Font("Arial", Font.BOLD, 20));
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
      double average_total =0;
      for (int i = 0; i < DataStore.getInstance().getCount(); i++ ){
          sum = DataStore.getInstance().getAllStudents().get(i).getAge() + sum;
      }
      average_total = sum/ DataStore.getInstance().getCount();
      JLabel average = new JLabel("Average Age: " + average_total);
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
  }
}
