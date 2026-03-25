package panels;

import model.DataStore;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for viewing all students in a table.
 * 
 * ASSIGNED TO: Student 4 (View / List Feature Owner)
 * 
 * TODO for Student 4:
 * - Add more columns to match all Student fields /
 * - Add a refresh button to reload data ⟳ /
 * - Add sorting functionality (click column headers)
 * - Improve table styling (row colors, column widths)
 * - Show "No records found" when the list is empty
 * - Optionally add pagination if the list is long
 */
public class ViewStudentsPanel extends JPanel {
  private DefaultTableModel tableModel;
  private JTable table;

  public ViewStudentsPanel() {
    setLayout(new BorderLayout());

    // Title
    JLabel title = new JLabel("All Students", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 24));
    title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    add(title, BorderLayout.NORTH);

    // Table
    String[] columns = { "Student ID", "Name", "Age", "Course", "Address" , "Email"}; // adding all student fields
    tableModel = new DefaultTableModel(columns, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false; // Read-only table
      }
    };
    table = new JTable(tableModel);
    table.setRowHeight(25);
    table.getTableHeader().setReorderingAllowed(false);

    table.setRowHeight(50);
    table.setShowHorizontalLines(true);
    table.setShowVerticalLines(true);
    table.setIntercellSpacing(new Dimension(1, 1));


    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    add(scrollPane, BorderLayout.CENTER);

    // Refresh button
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
    JButton refreshBtn = new JButton("Refresh List");
    refreshBtn.addActionListener(e -> loadData());
    buttonPanel.add(refreshBtn);
    add(buttonPanel, BorderLayout.SOUTH);

    //Add a refresh button to reload data
    JButton refreshbtn1 = new JButton("⟳");
    refreshbtn1.addActionListener(e -> loadData());
    buttonPanel.add(refreshbtn1);
    add(buttonPanel, BorderLayout.SOUTH);

    // Load initial data
    loadData();
  }

  private void loadData() {
    tableModel.setRowCount(0); // Clear table
    List<Student> students = DataStore.getInstance().getAllStudents();
    for (Student s : students) {
      tableModel.addRow(s.toTableRow());

    }
  }
}
