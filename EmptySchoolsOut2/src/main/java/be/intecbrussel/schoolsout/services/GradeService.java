package be.intecbrussel.schoolsout.services;

import be.intecbrussel.schoolsout.data.Course;
import be.intecbrussel.schoolsout.data.Grade;
import be.intecbrussel.schoolsout.data.Person;
import be.intecbrussel.schoolsout.data.User;
import be.intecbrussel.schoolsout.repositories.CourseRepository;
import be.intecbrussel.schoolsout.repositories.GradeRepository;
import be.intecbrussel.schoolsout.repositories.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class GradeService {

    private CourseRepository courseRepository;
    private GradeRepository gradeRepository;
    private UserRepository userRepository;

    public GradeService(){
        courseRepository = new CourseRepository();
        gradeRepository = new GradeRepository();
        userRepository = new UserRepository();
    }


    //Je maakt een nieuwe Grade. Je MOET een bestaande Person en Course meegeven.Jij geeft enkel de Date en gradeValue mee.
    // Zorg ervoor dat de Grade.gradeValue niet groter is dan de Course.maxGradeYouCanGet
    //Als de User al een bestaande Grade heeft voor dit Examen, dan wordt dit examen niet toegevoegd.
    // De datum van de Grade zit standaard by default op vandaag.
    public void createGradeForUserOnCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the ID number of the course: ");
        Long courseID = scanner.nextLong();
        System.out.println("Please enter the user name of the person you'd like to create a grade for:");
        String login = scanner.next();
        User user = userRepository.getOneById(login);
        Course course = courseRepository.getOneById(courseID);
        boolean courseContainsGrade = false;
        for(Grade grade : course.getGradesOfCourse()){
            if(grade.getPerson().getId().equals(user.getPerson().getId())){
                courseContainsGrade=true;
            }
        }
        if(!courseContainsGrade){
        Person person = user.getPerson();
        System.out.println("Course:" + course);
        System.out.println("Student: " + person);
            System.out.println("What grade are you giving the student? ");
            BigDecimal gradeForUserOnCourse = scanner.nextBigDecimal();
            LocalDate date = LocalDate.now();
            if (gradeForUserOnCourse.doubleValue() <= course.getMaxGradeYouCanGet().doubleValue()) {
                Grade grade = new Grade();
                grade.setGradeValue(gradeForUserOnCourse);
                grade.setPerson(person);
                gradeRepository.createOne(new Grade(person, course, grade.getGradeValue(), date));
                System.out.println("Grade " + gradeForUserOnCourse + " added for " + user.getLogin() + " in " + course.getName() + ".");
            } else {
                System.out.println("That's not a valid grade for this course. The highest grade possible is: " + course.getMaxGradeYouCanGet());
                System.out.println("Please enter a new grade: ");
                gradeForUserOnCourse = scanner.nextBigDecimal();
                if (gradeForUserOnCourse.doubleValue() <= course.getMaxGradeYouCanGet().doubleValue()) {
                    Grade grade = new Grade();
                    grade.setGradeValue(gradeForUserOnCourse);
                    grade.setPerson(person);
                    gradeRepository.createOne(new Grade(person, course, grade.getGradeValue(), date));
                    System.out.println("Grade " + gradeForUserOnCourse + " added for " + user.getLogin() + " in " + course.getName() + ".");
                }
            }
        }else {
            System.out.println("The user already has a grade in this course.");
        }
    }

    // Delete een Grade
    public void deleteGradeForUserOnCourse(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the username of the student?");
        String login = scanner.nextLine();
        System.out.println("What is the course id?");
        Long input = scanner.nextLong();
        Course course = courseRepository.getOneById(input);
        User user = userRepository.getOneById(login);
        List<Grade>grades = gradeRepository.findAllGradesForUser(user);
        for(Grade grade : grades){
            if(grade.getCourse().getName().equals(course.getName())){
                System.out.println("The grade you'd like to delete is: " + grade + " Y/N");
                String answer = scanner.next();
                    if (answer.toUpperCase(Locale.ROOT).equals("N")){
                    System.out.println("Mission aborted.");
            } else{
                gradeRepository.deleteOne(grade.getId());
                System.out.println("Grade deleted.");
                    }
            }
        }
    }

    //Je mag enkel de gradeValue veranderen. Zorg ervoor dat de Grade.gradeValue niet groter is dan de Course.maxGradeYouCanGet
    public void updateGradeForUserOnCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the username of the student?");
        String login = scanner.nextLine();
        System.out.println("What is the course id?");
        Long input = scanner.nextLong();
        Course course = courseRepository.getOneById(input);
        User user = userRepository.getOneById(login);
        List<Grade> grades = gradeRepository.findAllGradesForUser(user);
        for (Grade grade : grades) {
            if (grade.getCourse().getName().equals(course.getName())) {
                System.out.println("The current grade is: " + grade);
                System.out.println("What would you like to change the grade to?");
                BigDecimal newGrade = scanner.nextBigDecimal();
                if (newGrade.doubleValue() <= course.getMaxGradeYouCanGet().doubleValue()) {
                    grade.setGradeValue(newGrade);
                    gradeRepository.updateOne(grade);
                    System.out.println("The new grade for " + user.getLogin() + " in " + course.getName() + " is " +newGrade+ ".");
                } else {
                    System.out.println("That's not a valid grade for this course. The highest grade possible is: " + course.getMaxGradeYouCanGet());
                    System.out.println("Please enter a new grade: ");
                    newGrade = scanner.nextBigDecimal();
                    if (newGrade.doubleValue() <= course.getMaxGradeYouCanGet().doubleValue()) {
                        grade.setGradeValue(newGrade);
                        gradeRepository.updateOne(grade);
                        System.out.println("The new grade for " + user.getLogin() + " in " + course.getName() + " is " +newGrade+ ".");
                    }
                    }
                }
        }
    }

    //Geef een UserName. Gebruik de Person van de User om alle Grades terug te tonen.
    public void findAllGradesForUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the user name for the student whose grade you'd like to see: ");
        User user = userRepository.getOneById(scanner.next());
        List<Grade> allGradesForUser = gradeRepository.findAllGradesForUser(user);
        for(Grade grades : allGradesForUser){
            System.out.println(grades);
        }
    }
    }




