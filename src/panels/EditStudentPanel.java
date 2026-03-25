package panels;

import model.DataStore;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for editing and deleting students.
 * 
 * ASSIGNED TO: Student 4 (Edit / Delete Feature Owner)
 * or split between Student 4 & Student 5 if preferred
 * 
 * TODO:
 * - Allow selecting a row and editing the student's fields
 * - Add "Update" button to save changes to the DataStore
 * - Add "Delete" button with a confirmation dialog
 * - Add input validation on edit
 * - Refresh the table after any modification
 */
public class EditStudentPanel extends JPanel {
  private DefaultTableModel tableModel;
  private JTable table;
  private JTextField idField, nameField, ageField;

  public EditStudentPanel() {
    setLayout(new BorderLayout());

    // Title
    JLabel title = new JLabel("Edit / Delete Student", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 24));
    title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    add(title, BorderLayout.NORTH);

    // Table
    String[] columns = { "Student ID", "Name", "Age" };
    tableModel = new DefaultTableModel(columns, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    table = new JTable(tableModel);
    table.setRowHeight(25);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        populateFields();
      }
    });

    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    // Edit form + buttons at bottom
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

    JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
    formPanel.add(new JLabel("ID:"));
    idField = new JTextField(10);
    idField.setEditable(false); // ID should not be changed
    formPanel.add(idField);

    formPanel.add(new JLabel("Name:"));
    nameField = new JTextField(15);
    formPanel.add(nameField);

    formPanel.add(new JLabel("Age:"));
    ageField = new JTextField(5);
    formPanel.add(ageField);

    bottomPanel.add(formPanel, BorderLayout.CENTER);

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

    JButton updateBtn = new JButton("Update");
    updateBtn.addActionListener(e -> updateStudent());
    btnPanel.add(updateBtn);

    JButton deleteBtn = new JButton("Delete");
    deleteBtn.addActionListener(e -> deleteStudent());
    btnPanel.add(deleteBtn);

    JButton refreshBtn = new JButton("Refresh");
    refreshBtn.addActionListener(e -> loadData());
    btnPanel.add(refreshBtn);

    bottomPanel.add(btnPanel, BorderLayout.SOUTH);
    add(bottomPanel, BorderLayout.SOUTH);

    loadData();
  }

  private void loadData() {
    tableModel.setRowCount(0);
    List<Student> students = DataStore.getInstance().getAllStudents();
    for (Student s : students) {
      tableModel.addRow(s.toTableRow());
    }
    clearFields();
  }

  private void populateFields() {
    int row = table.getSelectedRow();
    if (row >= 0) {
      idField.setText(tableModel.getValueAt(row, 0).toString());
      nameField.setText(tableModel.getValueAt(row, 1).toString());
      ageField.setText(tableModel.getValueAt(row, 2).toString());
    }
  }

  private void updateStudent() {
    int row = table.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(this, "Select a student to update.", "Info", JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    String name = nameField.getText().trim();
    String ageText = ageField.getText().trim();

    if (name.isEmpty() || ageText.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Fields cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
      return;
    }

    int age;
    try {
      age = Integer.parseInt(ageText);
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Age must be a valid number.", "Validation Error",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    Student student = DataStore.getInstance().getAllStudents().get(row);
    student.setName(name);
    student.setAge(age);
    DataStore.getInstance().updateStudent(row, student);



      JOptionPane.showMessageDialog(this, "Student updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
    loadData();
  }

  private void deleteStudent() {
    int row = table.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(this, "Select a student to delete.", "Info", JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    int confirm = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to delete this student?",
        "Confirm Delete", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
      DataStore.getInstance().removeStudent(row);
      JOptionPane.showMessageDialog(this, "Student deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
      loadData();
    }
  }

  private void clearFields() {
    idField.setText("");
    nameField.setText("");
    ageField.setText("");
  }
}
