package net.mapoint.dao;

import java.util.List;
import net.mapoint.dao.entity.Location;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LocationDao extends BasicDao<Location> {

    @Autowired
    public LocationDao(SessionFactory sessionFactory) {
        super(Location.class, sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Location> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
            "select distinct location from Location as location left join fetch location.offers as offer left join fetch offer.dates as date left join fetch date.sessions order by location.address")
            .list();
    }

    public List<Location> getLocations(List<Integer> ids) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
            "select distinct location from Location as location left join fetch location.offers as offer left join fetch offer.dates as date left join fetch date.sessions where location.id in (:ids)",
            Location.class)
            .setParameterList("ids", ids)
            .list();
    }
}
