
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Course {
    String code;
    String name;
    int credits;
    String day;
    String time;

    Course(String code, String name, int credits,
           String day, String time) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.day = day;
        this.time = time;
    }

    void displayCourse() {
        System.out.println(
            code + " - " + name
            + " | Credits: " + credits
            + " | Day: " + day
            + " | Time: " + time
        );
    }
}

class Student {
    String studentId;
    String studentName;
    List<Course> registeredCourses;

    Student(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        registeredCourses = new ArrayList<>();
    }

    boolean registerCourse(Course course) {
        // Check for duplicate registration
        for (Course c : registeredCourses) {
            if (c.code.equals(course.code)) {
                System.out.println(
                    "You have already registered for this course."
                );
                return false;
            }
        }

        // Check for timetable conflicts
        for (Course c : registeredCourses) {
            if (c.day.equalsIgnoreCase(course.day)
                    && c.time.equalsIgnoreCase(course.time)) {
                System.out.println(
                    "Timetable conflict! You already have a class "
                    + "at this time."
                );
                return false;
            }
        }

        registeredCourses.add(course);
        System.out.println(
            "Course registered successfully!"
        );
        return true;
    }

    void viewTimetable() {
        if (registeredCourses.isEmpty()) {
            System.out.println("No courses registered yet.");
            return;
        }

        List<Course> timetable =
            new ArrayList<>(registeredCourses);

        timetable.sort(
            Comparator.comparing((Course c) -> c.day)
                      .thenComparing(c -> c.time)
        );

        System.out.println("\n===== STUDENT TIMETABLE =====");
        for (Course c : timetable) {
            c.displayCourse();
        }
    }

    void viewSummary() {
        int totalCredits = 0;

        for (Course c : registeredCourses) {
            totalCredits += c.credits;
        }

        System.out.println("\n===== STUDENT SUMMARY =====");
        System.out.println("Student ID: " + studentId);
        System.out.println("Student Name: " + studentName);
        System.out.println(
            "Registered Courses: " + registeredCourses.size()
        );
        System.out.println("Total Credits: " + totalCredits);
    }
}

public class CourseRegistrationTimetableBuilder {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Create sample courses
        List<Course> courses = new ArrayList<>();

        courses.add(new Course(
            "CS101", "Java Programming", 4,
            "Monday", "09:00"
        ));

        courses.add(new Course(
            "MA102", "Discrete Mathematics", 3,
            "Monday", "11:00"
        ));

        courses.add(new Course(
            "EC103", "Digital Electronics", 3,
            "Tuesday", "10:00"
        ));

        courses.add(new Course(
            "CS104", "Data Structures", 4,
            "Wednesday", "09:00"
        ));

        courses.add(new Course(
            "HS105", "Communication Skills", 2,
            "Thursday", "11:00"
        ));

        courses.add(new Course(
            "CS106", "Database Systems", 4,
            "Friday", "10:00"
        ));

        // Create a student
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        Student student = new Student(id, name);

        int choice;

        do {
            System.out.println("\n===== COURSE REGISTRATION "
                               + "AND TIMETABLE BUILDER =====");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. View Timetable");
            System.out.println("4. View Student Summary");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                sc.next();
                System.out.print("Enter your choice: ");
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println(
                        "\n===== AVAILABLE COURSES ====="
                    );

                    for (Course c : courses) {
                        c.displayCourse();
                    }
                    break;

                case 2:
                    System.out.print(
                        "Enter course code to register: "
                    );
                    String courseCode = sc.nextLine();

                    Course selectedCourse = null;

                    for (Course c : courses) {
                        if (c.code.equalsIgnoreCase(courseCode)) {
                            selectedCourse = c;
                            break;
                        }
                    }

                    if (selectedCourse == null) {
                        System.out.println(
                            "Invalid course code. Please try again."
                        );
                    } else {
                        student.registerCourse(selectedCourse);
                    }
                    break;

                case 3:
                    student.viewTimetable();
                    break;

                case 4:
                    student.viewSummary();
                    break;

                case 5:
                    System.out.println(
                        "Thank you for using the system!"
                    );
                    break;

                default:
                    System.out.println(
                        "Invalid choice. Please select 1 to 5."
                    );
            }

        } while (choice != 5);

        sc.close();
    }
}

