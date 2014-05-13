package com.supinfo.supforum.service;

import com.supinfo.supforum.entity.Board;
import com.supinfo.supforum.entity.Category;
import com.supinfo.supforum.entity.Topic;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class TopicService 
{
    @PersistenceContext(unitName = "SupForumPU")
    private EntityManager em;
    
    public List<Topic> getAll(Board board)
    {
         List<Topic> boards = new ArrayList<Topic>();
         Query q = em.createQuery("SELECT t FROM Topic t WHERE t.board.id=:id");
         q.setParameter("id", board.getId());
         boards = q.getResultList();
         return boards;
    }
     
    public Topic getByid(Long id)
    {
        Topic topicResult = new Topic();
        Query q = em.createQuery("SELECT t FROM Topic t WHERE t.id=:id");
        q.setParameter("id", id);
        ArrayList<Topic> results = new ArrayList<Topic>(q.getResultList());
        if(results.isEmpty() == true)
        {
            return null;
        }
        topicResult = results.get(0);
        return topicResult;
    }
    
    public Topic create(Topic topic)
    {
        try
        {
            em.persist(topic);
            return topic;
        }
        catch(Exception ex)
        {
            return null;
        }
    }
    
    public Topic update(Topic topic)
    {
        em.merge(topic);
        return topic;
    }
    
    public void delete(Topic topic)
    {
        Topic topicToRemove = em.merge(topic);
        em.remove(topicToRemove);
    }
    
     public Long getTopicCount()
    {
        Long count = new Long(0);
        try 
        {
            Query q = em.createQuery("SELECT COUNT(t) FROM Topic t");
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
