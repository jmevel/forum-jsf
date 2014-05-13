package com.supinfo.supforum.service;

import com.supinfo.supforum.entity.Category;
import com.supinfo.supforum.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CategoryService 
{
    @PersistenceContext(unitName = "SupForumPU")
    private EntityManager em;
    
    public Category create(Category category)
    {
        try
        {
            List<Category> categories = new ArrayList<Category>();
            Query q = em.createQuery("SELECT c FROM Category c WHERE c.name=:name");
            q.setParameter("name", category.getName());
        
            categories = q.getResultList();
            if(categories.isEmpty() != true)
            {
                return null;
            }
            em.persist(category);
            return category;
        }
        catch(Exception ex)
        {
            return null;
        }
    }
    
    public List<Category> getAll()
    {
         List<Category> categories = new ArrayList<Category>();
         Query q = em.createQuery("SELECT c FROM Category c");
         categories = q.getResultList();
         return categories;
    }
    
    public Category getByid(Long id)
    {
        Category categoryResult = new Category();
        Query q = em.createQuery("SELECT c FROM Category c WHERE c.id=:id");
        q.setParameter("id", id);
        String query1 = q.toString();
        ArrayList<Category> results = new ArrayList<Category>(q.getResultList());
        if(results.isEmpty() == true)
        {
            return null;
        }
        categoryResult = results.get(0);
        return categoryResult;
    }
}
