package panels;

import model.DataStore;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
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
 * - Improve table styling (row colors, column widths) /
 * - Show "No records found" when the list is empty
 * - Optionally add pagination if the list is long
 */
public class ViewStudentsPanel extends JPanel {
  private DefaultTableModel tableModel;
  private JTable table;
  private JLabel emptyLabel, pageLabel;
  private int currentPage = 1;
  private final int ROWS_PER_PAGE = 10;
  private List<Student> allStudents;
  private JButton prevBtn, nextBtn;

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
    table.setIntercellSpacing(new Dimension(1, 1));

    // Sorting functionality (click column headers)
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
    table.setRowSorter(sorter);

    table.getColumnModel().getColumn(0).setPreferredWidth(90);
    table.getColumnModel().getColumn(1).setPreferredWidth(150);
    table.getColumnModel().getColumn(2).setPreferredWidth(40);
    table.getColumnModel().getColumn(3).setPreferredWidth(120);
    table.getColumnModel().getColumn(4).setPreferredWidth(180);
    table.getColumnModel().getColumn(5).setPreferredWidth(160);

    DefaultTableCellRenderer rowColorRenderer  = new DefaultTableCellRenderer(){
      @Override
      public Component getTableCellRendererComponent(
              JTable table, Object value, boolean isSelected,
              boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
        setHorizontalAlignment(JLabel.CENTER);
        if (!isSelected) {
          c.setBackground(row % 2 == 0
                  ? new Color(0, 85, 255)  // light blue-white
                  : Color.WHITE);
        }
        return c;
      }
    };

    for(int i = 0; i<table.getColumnCount();i++){
      table.getColumnModel().getColumn(i).setCellRenderer(rowColorRenderer);
    }

    table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
    table.setFont(new Font("Arial", Font.PLAIN, 13));
    table.setGridColor(new Color(220, 220, 220));
    table.setShowGrid(true);

    // when the data is no records "No records found" label
    emptyLabel = new JLabel("No records found", SwingConstants.CENTER);
    emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
    emptyLabel.setForeground(Color.GRAY);
    emptyLabel.setVisible(false);


    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    add(scrollPane, BorderLayout.CENTER);

    JLayeredPane layeredPane = new JLayeredPane();
    layeredPane.setLayout(new OverlayLayout(layeredPane));
    layeredPane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);
    layeredPane.add(emptyLabel, JLayeredPane.PALETTE_LAYER);

    add(layeredPane, BorderLayout.CENTER);

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

    // For Pagination Controls
    prevBtn = new JButton("Prev");
    pageLabel = new JLabel("Page 1" , SwingConstants.CENTER);
    pageLabel.setFont(new Font("Arial", Font.PLAIN, 13));
    pageLabel.setPreferredSize(new Dimension(100,25));
    nextBtn = new JButton("Next");

    prevBtn.addActionListener(e -> {
      if(currentPage > 1){
        currentPage--;
        renderPage();
      }
    });

    nextBtn.addActionListener(e -> {
      int totalPages = getTotalPages();
      if(currentPage < totalPages){
        currentPage++;
        renderPage();
      }
    });

    buttonPanel.add(prevBtn);
    buttonPanel.add(pageLabel);
    buttonPanel.add(nextBtn);

    add(buttonPanel, BorderLayout.SOUTH);
    // Load initial data
    loadData();
  }
  private int getTotalPages(){
    if(allStudents == null || allStudents.isEmpty())
      return 1;
    return (int) Math.ceil((double) allStudents.size() / ROWS_PER_PAGE);
  }

  private void renderPage(){
    tableModel.setRowCount(0);

    if(allStudents == null || allStudents.isEmpty()){
      emptyLabel.setVisible(true);
      table.setVisible(false);
      pageLabel.setText("Page 0 / 0");
      prevBtn.setEnabled(false);
      nextBtn.setEnabled(false);
      return;
    }

    int totalPages = getTotalPages();
    int fromIndex = (currentPage - 1)*ROWS_PER_PAGE;
    int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, allStudents.size());

    for(int i = fromIndex; i<toIndex;i++){
      tableModel.addRow(allStudents.get(i).toTableRow());
    }

    emptyLabel.setVisible(false);
    table.setVisible(true);
    pageLabel.setText("Page " + currentPage + " / " + totalPages);
    prevBtn.setEnabled(currentPage > 1);
    nextBtn.setEnabled(currentPage < totalPages);
  }


  private void loadData() {
    tableModel.setRowCount(0); // Clear table
    allStudents = DataStore.getInstance().getAllStudents();
    currentPage = 1;
    renderPage();

    // Show "No records found" when the list is empty
    boolean isEmpty = tableModel.getRowCount() == 0;
    emptyLabel.setVisible(isEmpty);
    table.setVisible(!isEmpty);
  }
}
