package be.intecbrussel.schoolsout.services;

import be.intecbrussel.schoolsout.data.Course;
import be.intecbrussel.schoolsout.data.Grade;
import be.intecbrussel.schoolsout.data.User;
import be.intecbrussel.schoolsout.repositories.CourseRepository;
import org.hibernate.engine.jdbc.spi.SchemaNameResolver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CourseService {

    private CourseRepository courseRepository;

    public CourseService() {
        courseRepository = new CourseRepository();

    }

    //Maak een Course met de constructor
    public void createCourse(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Name of course? ");
        String name = scanner.nextLine();
        System.out.println("Course description? ");
        String description = scanner.nextLine();
        System.out.println("Maximum points for course?");
        BigDecimal maxValue = scanner.nextBigDecimal();

        courseRepository.createOne(new Course(name, description, maxValue));
    }

    //TODO: Delete een course, en delete alle Grades van die Course
    public void deleteCourse(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID of course you'd like to delete?");
        Course course = courseRepository.getOneById(scanner.nextLong());
        Long courseID = course.getId();
        System.out.println("The course you'd like to delete is: " + course + " Y/N");
        String answer = scanner.next();
        if (answer.toUpperCase(Locale.ROOT).equals("N")){
            System.out.println("Mission aborted.");
        } else{
        courseRepository.deleteOne(courseID);
        System.out.println("Course deleted.");
    }
    }

    //Update een Course. Je mag enkel de name, description en maxGradeYouCanGet editten
    public void updateCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID of course you'd like to update?");
        Course course = courseRepository.getOneById(scanner.nextLong());
        System.out.println("The course you'd like to update is: " + course);
        System.out.println("What would you like to edit? Please enter the number [1-3]");
        System.out.println("1 = Name");
        System.out.println("2 = Description");
        System.out.println("3 = Maximum grade possible");
        int number = scanner.nextInt();
        scanner.nextLine();
        if (number == 1) {
            System.out.println("Please enter new name: ");
            String name = scanner.nextLine();
            course.setName(name);
        }
        if (number == 2) {
            System.out.println("Please enter new description: ");
            String description = scanner.nextLine();
            course.setDescription(description);
        }
        if (number == 3) {
            System.out.println("Please enter new maximum grade points: ");
            BigDecimal maxGrade = scanner.nextBigDecimal();
            course.setMaxGradeYouCanGet(maxGrade);
        }

        courseRepository.updateOne(course);
        System.out.println("The updated course is: " + course);
    }

    //Toon een course op basis van Id
    public void findOneCourseById(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the course ID:");
        Course course = courseRepository.getOneById(scanner.nextLong());
        System.out.println(course);

    }

    // Toon alle Courses
    public void findAllCourses(){
        System.out.println("These are all the courses being given:");
        for(Course course : courseRepository.getAll()){
        System.out.println(course);
        }
    }

    // Print alle Grades van een Course (hint: gettermethode)
    public void findAllGradesFromCourse(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the course ID of the course you want the grades for:");
        Course course = courseRepository.getOneById(scanner.nextLong());
        List<Grade> allGradesFromCourse = course.getGradesOfCourse();
        for(Grade g : allGradesFromCourse){
            System.out.println(g);
        }
    }




}
