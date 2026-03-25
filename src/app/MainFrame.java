package app;

import panels.AddStudentPanel;
import panels.ViewStudentsPanel;
import panels.SearchStudentPanel;
import panels.EditStudentPanel;
import panels.DashboardPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * Main application frame. Assembles all panels into a tabbed layout.
 * 
 * ASSIGNED TO: Student 2 (Main Frame / App Shell Owner)
 * 
 * TODO for Student 2:
 * - Customize the look and feel (colors, fonts, window size)
 * - Add a menu bar if desired (File > Exit, Help > About) - done
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
    tabbedPane.addTab("Edit / Delete", new EditStudentPanel());

    add(tabbedPane, BorderLayout.CENTER);
  }
    private static JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("Help");
        editMenu.add(helpItem);
        return editMenu;
    }

    private static JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        return fileMenu;
    }

    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        return menuBar;
    }
  public static void main(String[] args) {
    // Use the system look-and-feel for a native appearance
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignored) {
    }

    SwingUtilities.invokeLater(() -> {
      MainFrame frame = new MainFrame();
      frame.setJMenuBar(createMenuBar());
        ImageIcon image1 = new ImageIcon("C:\\Users\\1010036\\Downloads\\5850276.png");
        //Image image = image1.getImage().getScaledInstance(800, 500, Image.SCALE_DEFAULT);
        frame.add(new JLabel(image1));
      frame.setVisible(true);
    });


  }
}
