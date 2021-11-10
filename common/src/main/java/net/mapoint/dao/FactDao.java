package net.mapoint.dao;

import java.util.List;
import net.mapoint.dao.entity.Fact;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FactDao extends BasicDao<Fact> {

    @Autowired
    public FactDao(SessionFactory sessionFactory) {
        super(Fact.class, sessionFactory);
    }


    public List<Fact> getAllFactsOrderByApproved() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select fact from Fact as fact join fetch fact.location order by fact.location.address", Fact.class)
            .list();
    }

    public List<Fact> getApprovedFacts() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select fact from Fact as fact join fetch fact.location where fact.approved = :approved", Fact.class)
            .setParameter("approved", true)
            .list();
    }

    public void like(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("UPDATE Fact fact set fact.likes = fact.likes + 1 WHERE fact.id = :id").setParameter("id", id).executeUpdate();
    }
}
