package panels;

import model.DataStore;
import model.Student;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for adding a new student.
 *
 * ASSIGNED TO: Student 3 (Add Student Feature Owner)
 *
 * TODO for Student 3:
 * - Add more input fields matching the Student model (email, course, etc.)
 * - Add input validation (e.g., ID not empty, age is a number, no duplicate
 * IDs)
 * - Show success/error messages using JOptionPane
 * - Clear fields after successful add
 * - Improve form layout and styling
 * - Optionally add a "Reset" button to clear the form
 */
public class AddStudentPanel extends JPanel {
    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;

    public AddStudentPanel() {
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Add New Student", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Student ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        idField = new JTextField(20);
        formPanel.add(idField, gbc);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Age
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        ageField = new JTextField(20);
        formPanel.add(ageField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JButton addBtn = new JButton("Add Student");
        addBtn.addActionListener(e -> addStudent());
        buttonPanel.add(addBtn);

        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> clearFields());
        buttonPanel.add(clearBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addStudent() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();

        // Basic validation
        if (id.isEmpty() || name.isEmpty() || ageText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Age must be a valid number.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Check for duplicate ID
        if (DataStore.getInstance().findById(id) != null) {
            JOptionPane.showMessageDialog(this,
                    "A student with this ID already exists.", "Duplicate ID", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DataStore.getInstance().addStudent(new Student(id, name, age));
        JOptionPane.showMessageDialog(this,
                "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
    }
}
