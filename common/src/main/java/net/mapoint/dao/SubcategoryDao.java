package net.mapoint.dao;

import net.mapoint.dao.entity.Subcategory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SubcategoryDao extends BasicDao<Subcategory> {

    @Autowired
    public SubcategoryDao(SessionFactory sessionFactory) {
        super(Subcategory.class, sessionFactory);
    }
}