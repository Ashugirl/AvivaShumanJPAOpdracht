package be.intecbrussel.schoolsout.repositories;

import be.intecbrussel.schoolsout.data.Course;
import be.intecbrussel.schoolsout.data.Grade;
import be.intecbrussel.schoolsout.data.Person;
import be.intecbrussel.schoolsout.data.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {

    public User getOneById(String login) {
        EntityManager em = EMFactory.getEmf().createEntityManager();
        return em.find(User.class, login);


    }

    public List<User> getAll() {
        EntityManager em = EMFactory.getEmf().createEntityManager();
        Query query = em.createQuery("Select v from User v");
        return query.getResultList();
    }


    public void createOne(User user) {
        EntityManager em = EMFactory.getEmf().createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

    }

    public void updateOne(User user) {
        EntityManager em = EMFactory.getEmf().createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();

    }

    public void deleteOne(String login) {
        EntityManager em = EMFactory.getEmf().createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(User.class, login));
        em.getTransaction().commit();


    }

    public List<User> getUsersByCourse(Course course) {
        EntityManager em = EMFactory.getEmf().createEntityManager();
        User user = new User();
        Query query = em.createQuery("SELECT Person FROM User WHERE person =" + user.getPerson());
        return query.getResultList();




    }

}