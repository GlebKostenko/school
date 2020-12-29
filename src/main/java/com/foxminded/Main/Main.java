package com.foxminded.Main;

import com.foxminded.dao.*;
import com.foxminded.formatter.FormatterData;
import com.foxminded.service.ServiceDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        DaoLayer daoImpl = new DaoImpl();
        ServiceDao serviceDao = new ServiceDao(daoImpl);
        FormatterData formatterData = new FormatterData(serviceDao);
        String query = "Enter your query(select number):\n"+
                "1.Add new student\n"+
                "2.Add student to course\n" +
                "3.Delete student by id\n" +
                "4.Remove student from course\n" +
                "5.Save all students\n" +
                "6.Search groups with less or equals student count\n"+
                "7.Find students related to course\n";
        while(true){
            System.out.print(query);
            Scanner number_query = new Scanner(System.in);
            String action = number_query.nextLine();
            if(action.equals("1")){
                System.out.println("Enter first:");
                String firstName = number_query.nextLine();
                System.out.println("Enter last name:");
                String lastName = number_query.nextLine();
                daoImpl.addNewStudent(firstName,lastName);
                System.out.println("Student was successfully added to course");
            }else if(action.equals("2")){
                System.out.println("Would you like to see a list of students?(Print Y if you want and N if you don't)");
                if (number_query.nextLine().equals("Y")){
                    DaoStudent daoStudent = new DaoStudent();
                    System.out.println(daoStudent.showAllStudents());
                }
                System.out.println("Would you like to see a list of courses?(Print Y if you want and N if you don't)");
                if (number_query.nextLine().equals('Y')){
                    DaoCourses daoCourses = new DaoCourses();
                    System.out.println(daoCourses.showAllCourses());
                }
                System.out.println("Enter student id:");
                int student_id = Integer.parseInt(number_query.nextLine());
                System.out.println("Enter course id:");
                int course_id = Integer.parseInt(number_query.nextLine());
                daoImpl.addStudentToCourse(student_id,course_id);
                System.out.println("Student was successfully added to course");
            }else if(action.equals("3")){
                System.out.println("Would you like to see a list of students?(Print Y if you want and N if you don't)");
                if (number_query.nextLine().equals("Y")){
                    DaoStudent daoStudent = new DaoStudent();
                    System.out.println(daoStudent.showAllStudents());
                }
                System.out.println("Enter student id:");
                daoImpl.deleteStudentById(Integer.parseInt(number_query.nextLine()));
                System.out.println("Student was successfully deleted");
            }else if(action.equals("4")){
                System.out.println("Would you like to see a list of students?(Print Y if you want and N if you don't)");
                if (number_query.nextLine().equals("Y")){
                    DaoStudent daoStudent = new DaoStudent();
                    System.out.println(daoStudent.showAllStudents());
                }
                System.out.println("Enter student id:");
                System.out.println("Student was successfully removed from "
                        + daoImpl.removeStudentFromCourse(Integer.parseInt(number_query.nextLine()))
                        + "course\n");
            }else if(action.equals("5")){
                daoImpl.save();
                System.out.println("It was saved");
            }else if(action.equals("6")){
                System.out.println("Enter count:");
                System.out.println(formatterData.GroupsWithLessOrEqualsStudentCount(Integer.parseInt(number_query.nextLine())));
            }else if(action.equals("7")){
                System.out.println("Would you like to see a list of courses?(Print Y if you want and N if you don't)");
                if (number_query.nextLine().equals("Y")){
                    DaoCourses daoCourses = new DaoCourses();
                    System.out.println(daoCourses.showAllCourses());
                }
                System.out.println("Enter course name:");
                System.out.println(formatterData.findStudentsRelatedToCourse(number_query.nextLine()));
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
