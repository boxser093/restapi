package com.ilya.restapiapp.repository.impl;

import com.ilya.restapiapp.conntectbd.HibernateStatementFactory;
import com.ilya.restapiapp.model.File;
import com.ilya.restapiapp.repository.FileRepository;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class HibFileRepImpl implements FileRepository {

    private final Logger logger = LoggerFactory.getLogger(HibFileRepImpl.class);

    @Override
    public File getById(Integer idInt) {
        try (Session session = HibernateStatementFactory.getSession()) {
            logger.info("### Hibernate File Repository ### Get By Id - successful");
            return session.createQuery("FROM File f where f.id =: id ", File.class)
                    .setParameter("id", idInt).getSingleResult();
        } catch (HibernateError error) {
            logger.debug("%%% Hibernate error %%% Get By Id");
            throw new HibernateError(error.getMessage());
        }

    }

    @Override
    public List<File> getAll() {
        try (Session session = HibernateStatementFactory.getSession()) {
            logger.info("### Hibernate File Repository ### get All - successful");
            return session.createQuery("FROM File f", File.class).list();
        } catch (HibernateError error) {
            logger.debug("%%% Hibernate error %%% get All");
            throw new HibernateError(error.getMessage());
        }

    }

    @Override
    public boolean deleteById(Integer idInt) {
        try (Session session = HibernateStatementFactory.getSession()) {
            session.beginTransaction();
            session.remove(getById(idInt));
            session.getTransaction().commit();
            logger.info("### Hibernate File Repository ### deleted - successful");
            return true;
        } catch (HibernateError error) {
            logger.debug("%%% Hibernate error %%% deleted - not successful");
            throw new HibernateError(error.getMessage());
        }
    }

    @Override
    public File save(File file) {
        try (Session session = HibernateStatementFactory.getSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(file);
            session.getTransaction().commit();
            logger.info("### Hibernate File Repository ### save - successful");
            return getById(id);
        } catch (HibernateError e) {
            logger.debug("%%% Hibernate error %%% save - not successful");
            throw new HibernateError(e.getMessage());
        }

    }

    @Override
    public File update(File file) {
        try (Session session = HibernateStatementFactory.getSession()) {
            session.beginTransaction();
            session.update(file);
            session.getTransaction().commit();
            logger.info("### Hibernate File Repository ### update - successful");
            return getById(file.getId());
        } catch (HibernateError e) {
            logger.debug("%%% Hibernate error %%% update - not successful");
            throw new HibernateError(e.getMessage());
        }

    }
}
