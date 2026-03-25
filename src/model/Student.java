package model;

/**
 * Data model representing a Student.
 * 
 * ASSIGNED TO: Student 1 (Data Model Owner)
 * 
 * TODO for Student 1:
 * - Add more fields as needed (e.g., email, course, yearLevel, contactNumber)
 * - Add validation logic (e.g., age must be positive, name not empty)
 * - Add a toString() method for display purposes
 * - Add a method to return data as an Object[] array for table display
 */
public class Student {
  private String id, email, course;
  private String name, address;
  private int age, yearLVL, contactNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getYearLVL() {
        return yearLVL;
    }

    public void setYearLVL(int yearLVL) {
        this.yearLVL = yearLVL;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Student(String id, String name, int age, String course, int yearLVL, String address, String email, int contactNumber) {
        this.id = id;
        this.email = email;
        this.course = course;
        this.name = name;
        this.address = address;
        this.age = age;
        this.yearLVL = yearLVL;
        this.contactNumber = contactNumber;
    }

    public Student(String id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  // --- Getters ---
  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  // --- Setters ---
  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
        if (name == null) {
            System.out.println("Name is empty!");
            return;
        }
    this.name = name;
  }

  public void setAge(int age) {
        if (age < 0) {
            System.out.println("Age must be positive!");
            return;
        }
    this.age = age;
  }

  @Override
  public String toString() {
    return id + " - " + name + " (Age: " + age + ") - " + course + yearLVL + " - " + email + " - " +
            contactNumber + " - " + address;
  }

  /**
   * Returns student data as an Object array, useful for JTable rows.
   */
  public Object[] toTableRow() {
    return new Object[] { id, name, age, course, yearLVL, email, contactNumber, address };
  }
}
