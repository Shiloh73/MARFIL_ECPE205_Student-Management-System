package panels;

import model.DataStore;
import model.Student;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
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
  private JTable resultTable;
  private StudentTableModel tableModel;

  public SearchStudentPanel() {
    setLayout(new BorderLayout());

    // Title
    JPanel topPanel = new JPanel(new BorderLayout());
    JLabel title = new JLabel("Search Student", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 24));
    title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    topPanel.add(title, BorderLayout.NORTH);

    // Search Bar
    JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
    searchBar.add(new JLabel("Search: "));
    searchField = new JTextField(20);
    searchBar.add(searchField);

    //Search Filter
    JButton searchBtn = new JButton("Search");
    searchBtn.addActionListener(e -> performSearch());
    searchBar.add(searchBtn);

    JButton clearBtn = new JButton("Clear");
    clearBtn.addActionListener(e -> {
      searchField.setText("");
      tableModel.clearTable();
    });
    searchBar.add(clearBtn);
    topPanel.add(searchBar, BorderLayout.SOUTH);
    add(topPanel, BorderLayout.NORTH);

    // Results area
    tableModel = new StudentTableModel();
    resultTable = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(resultTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
    add(scrollPane, BorderLayout.CENTER);
  }

  private void performSearch() {
    String query = searchField.getText().trim().toLowerCase();

    if (query.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please enter a search term.", "Info", JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    List<Student> allStudents = DataStore.getInstance().getAllStudents();
    List<Student> results = new ArrayList<>();

    for (Student s : allStudents) {
      if (s.getId().toLowerCase().contains(query)
              || s.getName().toLowerCase().contains(query)
              || String.valueOf(s.getAge()).contains(query)
              || s.getCourse().toLowerCase().contains(query)
              || s.getEmail().toLowerCase().contains(query)
              || s.getAddress().toLowerCase().contains(query)) {
        results.add(s);
      }
    }

    if (results.isEmpty()) {
      tableModel.clearTable();
      // Show popup instead of writing in the table area
      JOptionPane.showMessageDialog(this, "No students found matching: \"" + query + "\"", "No Results", JOptionPane.WARNING_MESSAGE);
    } else {
      tableModel.setStudentData(results);
    }
  }

  //TABLE MODEL
  private static class StudentTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Name", "Age", "Course", "Email", "Address"};
    private List<Student> studentList = new ArrayList<>();

    public void setStudentData(List<Student> students) {
      this.studentList = students;
      fireTableDataChanged();
    }

    public void clearTable() {
      this.studentList.clear();
      fireTableDataChanged();
    }

    @Override public int getRowCount() { return studentList.size(); }
    @Override public int getColumnCount() { return columnNames.length; }
    @Override public String getColumnName(int col) { return columnNames[col]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      Student s = studentList.get(rowIndex);
      switch (columnIndex) {
        case 0: return s.getId();
        case 1: return s.getName();
        case 2: return s.getAge();
        case 3: return s.getCourse();
        case 4: return s.getEmail();
        case 5: return s.getAddress();
        default: return null;
      }
    }
  }
}