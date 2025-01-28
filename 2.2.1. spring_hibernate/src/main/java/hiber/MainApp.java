package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      Car car1 = new Car("BMW", 456);
      User user1 = new User("User1", "Lastname1", "user1@mail.ru");
      user1.setCar(car1);
      userService.add(user1);

      Car car2 = new Car("Audi", 789);
      User user2 = new User("User2", "Lastname2", "user2@mail.ru");
      user2.setCar(car2);
      userService.add(user2);

      List<User> users = userService.listUsers();
      for (User user : users) {
         printUserInfo(user);
      }

      try {
         User userWithCar = userService.getUserByCar("BMW", 456);
         System.out.println("User with car BMW (456):");
         printUserInfo(userWithCar);
      } catch (NoResultException e) {
         System.out.println("User with car BMW (456) not found.");
      }

      context.close();
   }

   private static void printUserInfo(User user) {
      System.out.println("Id = " + user.getId());
      System.out.println("First Name = " + user.getFirstName());
      System.out.println("Last Name = " + user.getLastName());
      System.out.println("Email = " + user.getEmail());
      System.out.println("Car Id = " + (user.getCar() != null ? user.getCar().getId() : "null"));
      System.out.println();
   }
}