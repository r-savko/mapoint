package net.mapoint.dao;

import java.util.List;
import net.mapoint.dao.entity.Offer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OfferDao extends BasicDao<Offer> {

    private static final String PARAMETER_RELAX_IDS = "relaxIds";
    private static final String HQL_DELETE_OFFERS_BY_IDS = "delete from Offer o where o.relaxId in (:relaxIds)";

    @Autowired
    public OfferDao(SessionFactory sessionFactory) {
        super(Offer.class, sessionFactory);
    }

    public List<Offer> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session
            .createQuery("select offer from Offer as offer join fetch offer.location", Offer.class)
            .list();

    }

    public List<Offer> getApprovedOffers() {
        Session session = sessionFactory.getCurrentSession();
        return session
            .createQuery("select offer from Offer as offer join fetch offer.location where offer.approved = :approved", Offer.class)
            .setParameter("approved", true)
            .list();
    }

    public void deleteOffers(List<Long> relaxIds) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL_DELETE_OFFERS_BY_IDS);
        query.setParameterList(PARAMETER_RELAX_IDS, relaxIds);
        query.executeUpdate();
    }

    public void like(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("UPDATE Offer offer set offer.likes = offer.likes + 1 WHERE offer.id = :id").setParameter("id", id)
            .executeUpdate();
    }
}
