package lk.ijse.orm_final_coursework.config;

import lk.ijse.orm_final_coursework.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

public class FactoryConfiguration {
    private static  FactoryConfiguration factoryConfiguration;
    private  final SessionFactory sessionFactory;

    private FactoryConfiguration(){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("hibernate.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }
        Configuration configuration = new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(Lessons.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(StudentCourseDetail.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();

    }

    public static  FactoryConfiguration getInstance(){
        return factoryConfiguration == null ?
                factoryConfiguration = new FactoryConfiguration()
                :
                factoryConfiguration;
    }

    public Session getSession(){
        Session session = sessionFactory.openSession();
        return session;
    }

    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
