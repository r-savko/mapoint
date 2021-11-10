package net.mapoint.dao;

import java.util.List;
import net.mapoint.dao.entity.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDao extends BasicDao<Category> {

    @Autowired
    public CategoryDao(SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }


    public List<Category> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select distinct c from Category as c left join fetch c.subcategories as s order by c.name, s.name",
            Category.class)
            .list();
    }
}
