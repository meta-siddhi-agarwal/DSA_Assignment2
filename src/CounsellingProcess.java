package pac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class CounsellingProcess {
    // scanner obj. for taking input as int from user
    static Scanner scannerObject = new Scanner(System.in);
    // scanner obj. for taking input as string from user
    static Scanner scannerObjectForString = new Scanner(System.in);
    // will store no.of programs in college
    static int numberOfStudents;
    // will store list of studenst of college
    static Queue<Student> studentList = new LinkedList<>();
    // will store allocated programs to students
    static Map<String, String> allocatedProgram = new HashMap<>();
    // list for storing programs available in college with their resp. capacity
    static Map<String, Integer> listOfPrograms = new HashMap<>();
    // will store no. of programs available in college
    static int numberOfPrograms;

    /**
     * function to get no. of programs in college
     * 
     * @return no. of programs in college
     * @throws Exception in case no. of programs entered<5
     */
    static public int getNumberOfPrograms() throws Exception {
        System.out.println("Enter number of programs you want in college");

        if (scannerObject.hasNextInt()) {
            int programNumber = scannerObject.nextInt();
            if (programNumber < 5) {
                System.out.println("There should be at least 5 programs");
                return getNumberOfPrograms();
            } else
                return programNumber;
        } else {
            System.out.println("Enter valid input");
            scannerObject.next();
            return getNumberOfPrograms();
        }
    }

    public static void main(String[] args) {

        try {
            // get no. of programs in college
            numberOfPrograms = getNumberOfPrograms();
            // get programs in college
            listOfPrograms = getPrograms(numberOfPrograms);
            // get no. of students in college
            numberOfStudents = getNumberOfStudents();
            // get students of college
            getStudents(numberOfStudents);
            // allocate programs to students
            allocateProgram();
            // print allocated programs to students
            printAllocatedPrograms();

        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            scannerObject.nextLine();
        } catch (Exception e) {
            if (e.getMessage() == null)
                System.out.println(e);
            else
                System.out.println(e.getMessage());
            scannerObject.nextLine();
        }
    }

    /**
     * function will get programs of college
     * 
     * @param numberOfPrograms->which exists in college
     */
    static public Map<String, Integer> getPrograms(int numberOfPrograms) {
        int capacity = 0;
        Map<String, Integer> programsList = new HashMap<>();

        for (int programListIndex = 0; programListIndex < numberOfPrograms; programListIndex++) {
            System.out.println("Enter name of the program " + (programListIndex + 1));
            String programName = scannerObjectForString.nextLine();
            programName=programName.toUpperCase();
            if (programsList.containsKey(programName)) {
                System.out.println("Program already exists");
                programListIndex--;
            } else {
                System.out.println("Enter capacity of students for the program");
                if (scannerObject.hasNextInt()) {
                    capacity = scannerObject.nextInt();

                    if (capacity > 0) {
                        programsList.put(programName, capacity);
                    } else {
                        System.out.println("Capacity should be greater than 0");
                        programListIndex--;
                    }
                } else {
                    System.out.println("Please enter valid input");
                    programListIndex--;
                    scannerObject.next();
                }

            }

            // Programs programObject = new Programs(programName, capacity);
            // listOfPrograms.add(programObject);
        }

        return programsList;
    }

    /**
     * function to get no. of students in college
     * 
     * @return no. of student in college
     * @throws Exception in case no. of students<=0
     */
    static int getNumberOfStudents() throws Exception {
        System.out.println("Enter number of students that are in college");

        if (scannerObject.hasNextInt()) {
            int numberOfStudents = scannerObject.nextInt();
            if (numberOfStudents > 0)
                return numberOfStudents;
            else
                throw new Exception("No student exists");

        } else {
            System.out.println("Enter valid input");
            scannerObject.next();
            return getNumberOfStudents();
        }

    }

    /**
     * will get name of students with their preferred course
     * 
     * @param studentsNumber-> no. of students in college
     * @throws Exception
     */
    static void getStudents(int studentsNumber) throws Exception {
        boolean isValid = true;
        while (isValid) {
            try {
                for (int studentListIndex = 0; studentListIndex < studentsNumber; studentListIndex++) {
                    System.out.println("Enter name of student " + (studentListIndex + 1));
                    String studentName = scannerObjectForString.nextLine();
                    List<String> preferncesList = new ArrayList<>();
                    System.out.println("Available programs are ");
                    int index[] = { 1 };
                    listOfPrograms.forEach((String program, Integer capacity) -> {
                        System.out.println(index[0] + ". " + program);
                        index[0] += 1;
                    });
                    for (int preferencesListIndex = 1; preferencesListIndex <= 5; preferencesListIndex++) {

                        System.out.println("Enter preference " + preferencesListIndex);
                        String preferredCourse = scannerObjectForString.nextLine();
                        preferredCourse=preferredCourse.toUpperCase();
                        if (preferncesList.contains(preferredCourse)) {
                            System.out.println("You have already chosen this course\n" +
                                    "Choose different course");
                            preferencesListIndex--;
                        } else if (listOfPrograms.containsKey(preferredCourse))
                            preferncesList.add(preferredCourse);
                        else {
                            System.out.println("Program not available ");
                            preferencesListIndex--;
                        }
                    }
                    Student studentObject = new Student(studentName, preferncesList);
                    studentList.add(studentObject);
                }
                isValid = false;

            } catch (Exception e) {
                System.out.println(e);
                isValid = false;
            }
        }
    }

    /**
     * will allocate program to student
     */
    static public void allocateProgram() {
        while (!studentList.isEmpty()) {
            Student stuObject = studentList.remove();
            String name = stuObject.getName();
            List<String> preferencesList = stuObject.getPreferencesList();
            for (String course : preferencesList) {
                if (listOfPrograms.containsKey(course)) {
                    int capacity = listOfPrograms.get(course);
                    if (capacity > 0) {
                        allocatedProgram.put(name, course);
                        capacity--;
                        listOfPrograms.put(course, capacity);
                        break;
                    }
                }
            }

            if (!(allocatedProgram.containsKey(name))) {
                allocatedProgram.put(name, "Cannot allocate a program");
            }
        }
    }

    /**
     * will print allocated programs to student
     */
    static public void printAllocatedPrograms() {
        allocatedProgram.forEach((String name, String course) -> {
            System.out.print("Name of Student   ");
            System.out.println(name);
            System.err.print("Course allocated to the student   ");
            System.out.println(course);
        });
    }
}
