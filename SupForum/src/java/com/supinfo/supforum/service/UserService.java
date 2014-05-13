package com.supinfo.supforum.service;

import com.supinfo.supforum.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class UserService {
 
    @PersistenceContext(unitName = "SupForumPU")
    private EntityManager em;
    
    public User create(User user)
    {
        try
        {
            List<User> users = new ArrayList<User>();
            Query q = em.createQuery("SELECT u FROM User u WHERE u.username=:username");
            q.setParameter("username", user.getUsername());
        
            users = q.getResultList();
            if(users.size() != 0)
            {
                return null;
            }
            em.persist(user);
            return user;
        }
        catch(Exception ex)
        {
            return null;
        }
    }
    
    public User login(String username, String password){
        
        User user = null;
        
        try 
        {
            Query q = em.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:password");
            q.setParameter("username", username);
            q.setParameter("password", User.encryptPassword(password));
            
            user = (User) q.getSingleResult();
            
        } finally {
            return user;
        }
    }
    
    public Long getUserCount()
    {
        Long count = new Long(0);
        try 
        {
            Query q = em.createQuery("SELECT COUNT(u) FROM User u");
            count = (Long) q.getSingleResult();
        } 
        catch(Exception ex)
        {
            throw ex;
        }
        finally 
        {
            return count;
        }
    }
    
}
