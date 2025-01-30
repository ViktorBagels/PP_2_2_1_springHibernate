package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().persist(user);
   }

   @Override
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User", User.class);
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      String hql = "SELECT u FROM User u JOIN u.car c WHERE c.model = :model AND c.series = :series";
      TypedQuery<User> query = sessionFactory.getCurrentSession()
              .createQuery(hql, User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      try {
         return query.getSingleResult();
      } catch (NoResultException e) {
         System.out.println("Пользователь с машиной модели " + model + " и серии " + series + " не найден.");
         return null;
      } catch (NonUniqueResultException e) {
         System.out.println("Найдено более одного пользователя с машиной модели " + model + " и серии " + series + ".");
         return null;
      }
   }
}