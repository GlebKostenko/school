package com.foxminded;

import com.foxminded.controller.CoursesController;
import com.foxminded.controller.GroupsController;
import com.foxminded.controller.StudentController;
import com.foxminded.controller.StudentCoursesController;
import com.foxminded.dao.CoursesDao;
import com.foxminded.dao.GroupsDao;
import com.foxminded.dao.StudentDao;
import com.foxminded.dao.StudentCoursesDao;
import com.foxminded.service.CoursesService;
import com.foxminded.service.GroupsService;
import com.foxminded.service.StudentCoursesService;
import com.foxminded.service.StudentService;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        StudentDao studentDao = new StudentDao();
        GroupsDao groupsDao = new GroupsDao();
        CoursesDao coursesDao = new CoursesDao();
        StudentCoursesDao studentCoursesDao = new StudentCoursesDao();
        GroupsService groupsService = new GroupsService(groupsDao);
        CoursesService coursesService = new CoursesService(coursesDao);
        StudentService studentService = new StudentService(studentDao);
        StudentCoursesService studentCoursesService = new StudentCoursesService(studentCoursesDao);
        GroupsController groupsController = new GroupsController(groupsService);
        CoursesController coursesController = new CoursesController(coursesService);
        StudentController studentController = new StudentController(studentService);
        StudentCoursesController studentCoursesController = new StudentCoursesController(studentCoursesService);
//        groupsController.saveGroupsTable();
//        studentController.saveStudentsTable();
//        coursesController.saveCoursesTable();
//        studentCoursesController.saveStudentCoursesTable();
        String query = "Enter your query(select number):\n"+
                "1.Add new student\n"+
                "2.Add student to course\n" +
                "3.Delete student by id\n" +
                "4.Remove student from course\n" +
                "5.Search groups with less or equals student count\n"+
                "6.Find students related to course\n";
        while(true){
            System.out.print(query);
            Scanner number_query = new Scanner(System.in);
            String action = number_query.nextLine();
            if(action.equals("1")){
                System.out.println("Enter first name:");
                String firstName = number_query.nextLine();
                System.out.println("Enter last name:");
                String lastName = number_query.nextLine();
                studentController.addNewStudent(firstName,lastName);
                System.out.println("Student was successfully added");
            }else if(action.equals("2")){
                System.out.println("Would you like to see a list of students?(Print Y if you want and N if you don't)");
                if (number_query.nextLine().equals("Y")){
                    System.out.println(studentController.showAllStudents());
                }
                System.out.println("Would you like to see a list of courses?(Print Y if you want and N if you don't)");
                if (number_query.nextLine().equals("Y")){
                    System.out.println(coursesController.showAllCourses());
                }
                System.out.println("Enter student id:");
                int student_id = Integer.parseInt(number_query.nextLine());
                System.out.println("Enter course id:");
                int course_id = Integer.parseInt(number_query.nextLine());
                studentController.addStudentToCourse(student_id,course_id);
                System.out.println("Student was successfully added to course");
            }else if(action.equals("3")){
                System.out.println("Would you like to see a list of students?(Print Y if you want and N if you don't)");
                if (number_query.nextLine().equals("Y")){
                    System.out.println(studentController.showAllStudents());
                }
                System.out.println("Enter student id:");
                studentController.deleteStudentById(Integer.parseInt(number_query.nextLine()));
                System.out.println("Student was successfully deleted");
            }else if(action.equals("4")){
                System.out.println("Would you like to see a list of students?(Print Y if you want and N if you don't)");
                if (number_query.nextLine().equals("Y")){
                    System.out.println(studentController.showAllStudents());
                }
                System.out.println("Enter student id:");
                int studentId = Integer.parseInt(number_query.nextLine());
                System.out.println("Enter course id:");
                int courseId = Integer.parseInt(number_query.nextLine());
                studentController.removeStudentFromCourse(studentId,courseId);
                System.out.println("Student was successfully removed from course\n");
            }else if(action.equals("5")){
                System.out.println("Enter count:");
                System.out.println(groupsController.GroupsWithLessOrEqualsStudentCount(Integer.parseInt(number_query.nextLine())));
            }else if(action.equals("6")){
                System.out.println("Would you like to see a list of courses?(Print Y if you want and N if you don't)");
                if (number_query.nextLine().equals("Y")){
                    System.out.println(coursesController.showAllCourses());
                }
                System.out.println("Enter course id:");
                System.out.println(coursesController.findStudentsRelatedToCourse(Integer.parseInt(number_query.nextLine())));
            }
            System.out.println("If you would like to continue print Y,in other case print N");
            String continueOrNot = number_query.nextLine();
            if(continueOrNot.equals("Y")){
                continue;
            }else {
                break;
            }
        }
    }
}
