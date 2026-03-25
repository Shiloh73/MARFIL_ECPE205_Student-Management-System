package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Central in-memory data store. All panels read/write through this class.
 * Data is stored in an ArrayList of Student objects (no database).
 * 
 * ASSIGNED TO: Student 1 (Data Model Owner)
 * 
 * TODO for Student 1:
 * - Add methods as other teammates need them (e.g., updateStudent,
 * searchByName)
 * - Keep this class as the SINGLE source of truth for all data
 */
public class DataStore {
  private static final DataStore instance = new DataStore();
  private final List<Student> students;

  private DataStore() {
    students = new ArrayList<>();
    // Pre-load some sample data for testing
    students.add(new Student("2024-0001", "Juan Dela Cruz", 20));
    students.add(new Student("2024-0002", "Maria Santos", 19));
    students.add(new Student("2024-0003", "Jose Rizal", 21));
  }

  /** Get the singleton instance */
  public static DataStore getInstance() {
    return instance;
  }

  /** Get all students */
  public List<Student> getAllStudents() {
    return students;
  }

  /** Add a new student */
  public void addStudent(Student student) {
    students.add(student);
  }

  /** Remove a student by index */
  public void removeStudent(int index) {
    if (index >= 0 && index < students.size()) {
      students.remove(index);
    }
  }

  public void updateStudent(int index, Student student) {
      if (index >= 0 && index < students.size()) {
          students.remove(index);
      }
      students.add(student);
  }

  /** Find a student by ID */
  public Student findById(String id) {
    for (Student s : students) {
      if (s.getId().equals(id)) {
        return s;
      }
    }
    return null;
  }

  public Student searchByName(String name) {
      for (Student s : students) {
          if (s.getName().equals(name)) {
              return s;
          }
      }
      return null;
  }

  /** Get total number of students */
  public int getCount() {
    return students.size();
  }
}
