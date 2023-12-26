package com.ilya.restapiapp.conntectbd;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateStatementFactory {
    private static SessionFactory sessionFactory;
    private static Configuration configuration = new Configuration().configure();

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
                try {
                    configuration.configure("hibernate.cfg.xml");
                    sessionFactory =configuration.buildSessionFactory();
                } catch (Throwable ex) {
                    throw new ExceptionInInitializerError(ex);
                }
            return sessionFactory;
        }
        else {
            return sessionFactory;
        }
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }


}
