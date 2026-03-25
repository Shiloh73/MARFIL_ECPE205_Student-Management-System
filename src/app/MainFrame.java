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
import java.net.MalformedURLException;

/**
 * Main application frame. Assembles all panels into a tabbed layout.
 * 
 * ASSIGNED TO: Student 2 (Main Frame / App Shell Owner)
 * 
 * TODO for Student 2:
 * - Customize the look and feel (colors, fonts, window size)
 * - Add a menu bar if desired (File > Exit, Help > About) - done
 * - Add an application icon - done
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
    tabbedPane.addTab("Edit / Delete Student", new EditStudentPanel());

    add(tabbedPane, BorderLayout.CENTER);
  }
    private static JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("About");
        helpMenu.add(helpItem);
        helpItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Submitted by Bautista, Juesna, Marfil, Milos, and Roquero");
        });
        return helpMenu;
    }

    private static JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        exitItem.addActionListener(e -> System.exit(0));
        return fileMenu;
    }

    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createHelpMenu());
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
        ImageIcon image = null;
        try {
         image = new ImageIcon(new java.net.URL("https://cdn-icons-png.flaticon.com/512/5850/5850276.png"));
         Image img = image.getImage();
         Image size = img.getScaledInstance(50,50, Image.SCALE_SMOOTH); //size configuration
         ImageIcon sizedImage = new ImageIcon(size); //para mag display

         JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
         JLabel label = new JLabel(sizedImage);
         right.add(label); //add label sa right panel
         frame.add(right, BorderLayout.SOUTH); //add it to frame para mabutang sa dalom
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
      frame.setVisible(true);
    });


  }
}
