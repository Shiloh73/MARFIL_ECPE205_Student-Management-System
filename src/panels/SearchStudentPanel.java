package panels;

import model.DataStore;
import model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
/**
 * Panel for searching students by ID or name.
 * 
 * ASSIGNED TO: Student 5 (Search Feature Owner)
 * 
 * TODO for Student 5:
 * - Implement search by name (partial match / contains)
 * - Add search filter options (search by ID, by name, by course, etc.)
 *  -Display results in a table or formatted list
 * - Handle case-insensitive search
 * - Show "No results found" message when appropriate
 * - Add a "Clear Search" button
 */
public class SearchStudentPanel extends JPanel {
  private JTextField searchField;
  private JTextArea resultArea;

  public SearchStudentPanel() {
    setLayout(new BorderLayout());

    // Title
    JLabel title = new JLabel("Search Student", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 24));
    title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    add(title, BorderLayout.NORTH);

    // Search bar
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

    searchPanel.add(new JLabel("Search by ID, Name, Age, Class: "));
    searchField = new JTextField(20);
    searchPanel.add(searchField);

    //Search Filter
    JButton searchBtn = new JButton("Search");
    searchBtn.addActionListener(e -> performSearch());
    searchPanel.add(searchBtn);

    JButton clearBtn = new JButton("Clear");
    clearBtn.addActionListener(e -> {
      searchField.setText("");
      resultArea.setText("");
    });
    searchPanel.add(clearBtn);

    add(searchPanel, BorderLayout.NORTH);

    // Results area
    JPanel resultsWrapper = new JPanel(new BorderLayout());
    resultsWrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

    JLabel resultsLabel = new JLabel("Results:");
    resultsLabel.setFont(new Font("Arial", Font.BOLD, 14));
    resultsWrapper.add(resultsLabel, BorderLayout.NORTH);

    resultArea = new JTextArea();
    resultArea.setEditable(false);
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
    JScrollPane scrollPane = new JScrollPane(resultArea);
    resultsWrapper.add(scrollPane, BorderLayout.CENTER);

    add(resultsWrapper, BorderLayout.CENTER);
  }

  private void performSearch() {
    String query = searchField.getText().trim().toLowerCase();

    if (query.isEmpty()) {
      JOptionPane.showMessageDialog(this,
          "Please enter a search term.", "Info", JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    List<Student> allStudents = DataStore.getInstance().getAllStudents();
    List<Student> results = new ArrayList<>();

    for (Student s : allStudents) {
      if (s.getId().toLowerCase().contains(query)
          || s.getName().toLowerCase().contains(query) || String.valueOf(s.getAge()).contains(query) || s.getCourse().toLowerCase().contains(query)
              || s.getEmail().toLowerCase().contains(query) || s.getAddress().toLowerCase().contains(query)) {
        results.add(s);

      }
    }

    if (results.isEmpty()) {
      resultArea.setText("No students found matching: \"" + searchField.getText().trim() + "\"");
    } else {
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("%-15s %-25s %-5s%n", "ID", "Name", "Age", "Course", "Email", "Address"));
      sb.append("-".repeat(45)).append("\n");
      for (Student s : results) {
        sb.append(String.format("%-15s %-25s %-5d%n", s.getId(), s.getName(), s.getAge()), s.getCourse(), s.getEmail(), s.getAddress());
      }
      sb.append("\nFound ").append(results.size()).append(" result(s).");
      resultArea.setText(sb.toString());
    }
  }
}
