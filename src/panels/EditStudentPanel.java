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
  private JTextField idField, nameField, ageField, courseField,addressField, emailField;

  public EditStudentPanel() {
    setLayout(new BorderLayout());

    // Title
    JLabel title = new JLabel("Edit / Delete Student", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 24));
    title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    add(title, BorderLayout.NORTH);

    // Table
    String[] columns = { "Student ID", "Name", "Age", "Course", "Address" , "Email" };
    tableModel = new DefaultTableModel(columns, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    table = new JTable(tableModel);
    table.setRowHeight(25);
    table.getTableHeader().setReorderingAllowed(false);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        populateFields();
      }
    });

    table.setRowHeight(50);
    table.setIntercellSpacing(new Dimension(1, 1));

    table.getColumnModel().getColumn(0).setPreferredWidth(90);
    table.getColumnModel().getColumn(1).setPreferredWidth(150);
    table.getColumnModel().getColumn(2).setPreferredWidth(40);
    table.getColumnModel().getColumn(3).setPreferredWidth(120);
    table.getColumnModel().getColumn(4).setPreferredWidth(180);
    table.getColumnModel().getColumn(5).setPreferredWidth(160);

    table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
    table.setFont(new Font("Arial", Font.PLAIN, 13));
    table.setGridColor(new Color(220, 220, 220));
    table.setShowGrid(true);

    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

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

    formPanel.add(new JLabel("Course:"));
    courseField = new JTextField(5);
    formPanel.add(courseField);

    formPanel.add(new JLabel("Address:"));
    addressField = new JTextField(5);
    formPanel.add(addressField);

    formPanel.add(new JLabel("Email:"));
    emailField = new JTextField(5);
    formPanel.add(emailField);

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
      idField.setText(safe(tableModel.getValueAt(row, 0)));
      nameField.setText(safe(tableModel.getValueAt(row, 1)));
      ageField.setText(safe(tableModel.getValueAt(row, 2)));
      courseField.setText(safe(tableModel.getValueAt(row, 3)));
      addressField.setText(safe(tableModel.getValueAt(row, 4)));
      emailField.setText(safe(tableModel.getValueAt(row, 5)));
    }
  }

  private String safe(Object value) {
    return value != null ? value.toString() : "";
  }

  private void updateStudent() {
    int row = table.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(this, "Select a student to update.", "Info", JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    String name = nameField.getText().trim();
    String ageText = ageField.getText().trim();
    String courseText = courseField.getText().trim();
    String addressText = addressField.getText().trim();
    String emailText = emailField.getText().trim();

    if (name.isEmpty() || ageText.isEmpty() || courseText.isEmpty() ||addressText.isEmpty() || emailText.isEmpty() ) {
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
    student.setCourse(courseText);
    student.setAddress(addressText);
    student.setEmail(emailText);

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
    courseField.setText("");
    addressField.setText("");
    emailField.setText("");
  }
}