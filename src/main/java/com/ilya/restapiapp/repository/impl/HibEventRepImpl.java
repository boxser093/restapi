package com.ilya.restapiapp.repository.impl;

import com.ilya.restapiapp.conntectbd.HibernateStatementFactory;
import com.ilya.restapiapp.model.Event;
import com.ilya.restapiapp.repository.EventRepository;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


public class HibEventRepImpl implements EventRepository {
//    private final Logger logger = LoggerFactory.getLogger(HibEventRepImpl.class);

    @Override
    public Event getById(Integer idInt) {
        try (Session session = HibernateStatementFactory.getSession()) {
//            logger.info("### Hibernate Event Repository ### Get By Id () - successful");
        Event event = session.get(Event.class, idInt);
        return event;
        } catch (HibernateError e) {
//            logger.info("### Hibernate Event Repository ### Get By Id () - not successful");
            throw new HibernateError(e.getMessage());
        }
    }

    @Override
    public List<Event> getAll() {
        try (Session session = HibernateStatementFactory.getSession()) {
//            logger.info("### Hibernate Event Repository ### Get All () - successful");
            return session.createQuery("FROM Event p",Event.class).list();
        } catch (HibernateError e) {
//            logger.info("### Hibernate Event Repository ### Get All () - not successful");
            throw new HibernateError(e.getMessage());
        }
    }

    @Override
    public boolean deleteById(Integer idInt) {
        try (Session session = HibernateStatementFactory.getSession()) {
            session.beginTransaction();
            session.remove(getById(idInt));
            session.getTransaction().commit();
//            logger.info("### Hibernate Event Repository ### deleted by id () - successful");
            return true;
        } catch (HibernateError e) {
//            logger.info("### Hibernate Event Repository ### deleted by id () - not successful");
            throw new HibernateError(e.getMessage());
        }
    }

    @Override
    public Event update(Event event) {
        try (Session session = HibernateStatementFactory.getSession()) {
            session.beginTransaction();
            session.update(event);
            session.getTransaction().commit();
//            logger.info("### Hibernate Event Repository ### update by id () - successful");
            return getById(event.getId());
        } catch (HibernateError e) {
//            logger.info("### Hibernate Event Repository ### update by id () - not successful");
            throw new HibernateError(e.getMessage());
        }
    }

    @Override
    public Event save(Event event) {
        Event eventSave;
        try  {
            Session session = HibernateStatementFactory.getSession();
            session.beginTransaction();
            Integer id = (Integer) session.save(event);
            session.getTransaction().commit();
//            logger.info("### Hibernate Event Repository ### create () - successful");
            session.close();
            eventSave = getById(id);
            return eventSave;
        } catch (HibernateError e) {
//            logger.info("### Hibernate Event Repository ### update by id () - not successful");
            throw new HibernateError(e.getMessage());
        }
    }
}
