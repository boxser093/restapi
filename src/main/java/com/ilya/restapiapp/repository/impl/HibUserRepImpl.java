package com.ilya.restapiapp.repository.impl;

import com.ilya.restapiapp.conntectbd.HibernateStatementFactory;
import com.ilya.restapiapp.model.Event;
import com.ilya.restapiapp.model.User;
import com.ilya.restapiapp.repository.UserRepository;
import lombok.*;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Data
@NoArgsConstructor
public class HibUserRepImpl implements UserRepository {

    private final Logger logger = LoggerFactory.getLogger(HibUserRepImpl.class);

    @Override
    public User getById(Integer idint) {
        try (Session session = HibernateStatementFactory.getSession()) {
            logger.info("## Hibernate User Repository ## get By id() ### - successful");
            session.beginTransaction();
            User user = session.get(User.class, idint);
            List<Event> events = user.getEvents();
            user.setEvents(events);
            return user;
        } catch (Exception e) {
            logger.info("## Hibernate User Repository ## get By id() ### - not successful");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = HibernateStatementFactory.getSession()) {
            logger.info("## Hibernate User Repository ## get all user() ### - successful");
            return session.createQuery("FROM User p ", User.class)
                    .list();
        } catch (HibernateError e) {
            logger.info("## Hibernate User Repository ## get all user() ### - not successful");
            throw new HibernateError(e.getMessage());
        }
    }

    @Override
    public boolean deleteById(Integer idint) {
        try (Session session = HibernateStatementFactory.getSession()) {
            session.beginTransaction();
            session.remove(getById(idint));
            session.getTransaction().commit();
            logger.info("## Hibernate User Repository ## deleted By id() - successful");
            return true;
        } catch (HibernateError e) {
            logger.debug("%%% Hibernate error %%% service(%s), method name(%s)");
            throw new HibernateError(e.getMessage());
        }
    }

    @Override
    public User save(User user) {
        try (Session session = HibernateStatementFactory.getSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(user);
            session.getTransaction().commit();
            logger.debug("%%% Hibernate error %%% user save - successful");
            return getById(id);
        } catch (HibernateError e) {
            logger.debug("%%% Hibernate error %%% user save - error");
            throw new HibernateError(e.getMessage());
        }
    }

    @Override
    public User update(User user) {
        try (Session session = HibernateStatementFactory.getSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
            logger.info("## Hibernate User Repository ## update user() ### - successful");
            return getById(user.getId());
        } catch (HibernateError e) {
            logger.info("## Hibernate User Repository ## update user() ### - not successful");
            throw new HibernateError(e.getMessage());
        }
    }
}
