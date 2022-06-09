package be.intecbrussel.schoolsout.services;


import be.intecbrussel.schoolsout.data.*;
import be.intecbrussel.schoolsout.repositories.CourseRepository;
import be.intecbrussel.schoolsout.repositories.GradeRepository;
import be.intecbrussel.schoolsout.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class UserService {

    private UserRepository userRepository;
    private GradeRepository gradeRepository;
    private CourseRepository courseRepository;
    private CourseService courseService;

    public UserService() {
        userRepository = new UserRepository();
        gradeRepository= new GradeRepository();
        courseRepository = new CourseRepository();
        courseService = new CourseService();
    }

    //Maak een user. Iedere User die je maakt MOET ook een Person hebben
    public void createUser(){
        Scanner scanner = new Scanner(System.in);
        Person person = new Person();
        User user = new User();

        System.out.println("Give me your userName:");
        String input = scanner.next();
        user.setLogin(input);
        System.out.println("Give me your passWord:");
        input = scanner.next();
        scanner.nextLine();
        user.setPasswordHash(input);
        System.out.println("Give me your firstName:");
        person.setFirstName(scanner.next());
        System.out.println("Give me your lastName:");
        person.setFamilyName(scanner.next());
        System.out.println("Give me your Gender. 0 = MALE, 1 = FEMALE, 2= NON-BINAIRY, 3 = OTHER:");
        int number = scanner.nextInt();
       for (Gender gender: Gender.values()){
           if (gender.ordinal()==number){
               person.setGender(gender);
           }
       }
       user.setPerson(person);
       userRepository.createOne(user);

    }
    // Delete een user, en delete ook de Person EN de Grades van die Person
    public void deleteUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("User name of user you'd like to delete?");
        String login = scanner.next();
        User user = userRepository.getOneById(login);
        Queue<Grade> gradeQueue = new LinkedList<>();
        gradeQueue.addAll(gradeRepository.findAllGradesForUser(user));
        while(gradeQueue.size() >0){
            Grade grade= gradeQueue.poll();
            gradeRepository.deleteOne(grade.getId());
        }
        userRepository.deleteOne(login);
//        User user = userRepository.getOneById(scanner.next());
//        String userID = user.getLogin();
//        System.out.println("The user you'd like to delete is: " + user + " Y/N");
//        String answer = scanner.next();
//        if (answer.toUpperCase(Locale.ROOT).equals("N")){
//            System.out.println("Mission aborted.");
//        } else{
//            userRepository.deleteOne(userID);
//            System.out.println("User deleted.");
//    }
    }

    //Update de User. Je mag enkel vragen om het volgende te updaten: User.active, Person.firstName en Person.lastName
    public void updateUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the user name of the profile to be edited:");
        User user = userRepository.getOneById(scanner.next());
        Person person = user.getPerson();
        System.out.println("What would you like to edit? Please enter the number [1-3]");
        System.out.println("1 = Active");
        System.out.println("2 = First name");
        System.out.println("3 = Last name");
        int number = scanner.nextInt();
        if (number == 1) {
            System.out.println("Is the user active? Y/N");
            String answer = scanner.next();
            if (answer.toUpperCase(Locale.ROOT).equals("Y")){
                user.setActive(true);
          }
        }

        if (number == 2) {
            System.out.println("Please enter new first name: ");
            String firstName = scanner.next();
            scanner.nextLine();
            person.setFirstName(firstName);
        }
        if (number == 3) {
            System.out.println("Please enter new last name: ");
            String lastName = scanner.next();
            person.setFamilyName(lastName);
        }
        userRepository.updateOne(user);
        System.out.println("The updated user is: " + user);
    }



    // Print een User + Person van de database af door een username in te geven
    public void findOneUserById(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter user name:");
        User user = userRepository.getOneById(scanner.nextLine());
        System.out.println(user);


    }

    //Print alle users af van de database
    public void findAllUsers(){
        System.out.println("Here are all users:");
        for (User user : userRepository.getAll()){
            System.out.println(user);
        }

    }

    //TODO: Toon eerst alle courses. Op basis van de relatie tussen Course, Grade en Person toon je dan alle Persons die die Course hebben gedaan
    //TODO: Ik kan dit met gewone method, maar het lukt mij niet om dit met JPA te doen. :( Gewone method beneden.
    public void showAllPeoplePerCourse(){
        Scanner scanner = new Scanner(System.in);
        List<Course> courseList = courseRepository.getAll();
        for (Course c : courseList) {
            System.out.println(c);
          }
        System.out.println("Which course would you like the student list for? Please enter the course id number:");
        Long courseId = scanner.nextLong();
        Course course = courseRepository.getOneById(courseId);
        List<Grade> allGradesFromCourse = course.getGradesOfCourse();
        List<Person> personList = allGradesFromCourse.stream().map(Grade::getPerson).collect(Collectors.toList());
        for(Person peopleInCourse : personList){
            System.out.println(peopleInCourse);

        }

//            //TODO: find the right query for VVV
//            userRepository.getUsersByCourse(c);
    }





}
