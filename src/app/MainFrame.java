package app;

import panels.AddStudentPanel;
import panels.ViewStudentsPanel;
import panels.SearchStudentPanel;
import panels.EditStudentPanel;
import panels.DashboardPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Main application frame. Assembles all panels into a tabbed layout.
 * 
 * ASSIGNED TO: Student 2 (Main Frame / App Shell Owner)
 * 
 * TODO for Student 2:
 * - Customize the look and feel (colors, fonts, window size)
 * - Add a menu bar if desired (File > Exit, Help > About)
 * - Add an application icon
 * - Improve the overall layout and styling
 */
public class MainFrame extends JFrame {

  public MainFrame() {
    setTitle("Student Management System");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setMinimumSize(new Dimension(700, 500));
    setLocationRelativeTo(null); // Center on screen

    // --- Tabbed Pane: each tab is owned by a different student ---
    JTabbedPane tabbedPane = new JTabbedPane();

    tabbedPane.addTab("Dashboard", new DashboardPanel());
    tabbedPane.addTab("Add Student", new AddStudentPanel());
    tabbedPane.addTab("View Students", new ViewStudentsPanel());
    tabbedPane.addTab("Search Student", new SearchStudentPanel());
    tabbedPane.addTab("Edit / Delete Students", new EditStudentPanel());

    add(tabbedPane, BorderLayout.CENTER);
  }

  public static void main(String[] args) {
    // Use the system look-and-feel for a native appearance
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignored) {
    }

    SwingUtilities.invokeLater(() -> {
      MainFrame frame = new MainFrame();
      frame.setVisible(true);
    });
  }
}
