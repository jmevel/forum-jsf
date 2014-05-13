package com.supinfo.supforum.service;

import com.supinfo.supforum.entity.Answer;
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
public class AnswerService 
{
    @PersistenceContext(unitName = "SupForumPU")
    private EntityManager em;
    
    public Answer create(Answer answer)
    {
        try
        {
            em.persist(answer);
            return answer;
        }
        catch(Exception ex)
        {
            return null;
        }
    }
    
    public List<Answer> getAll(Topic topic)
    {
         List<Answer> answers = new ArrayList<Answer>();
         Query q = em.createQuery("SELECT a FROM Answer a WHERE a.topic.id=:id ORDER BY a.date");
         q.setParameter("id", topic.getId());
         answers = q.getResultList();
         return answers;
    }
    
    public Answer update(Answer answer)
    {
        em.merge(answer);
        return answer;
    }
    
    public Answer getByid(Long id)
    {
        Answer answerResult = new Answer();
        Query q = em.createQuery("SELECT a FROM Answer a WHERE a.id=:id");
        q.setParameter("id", id);
        ArrayList<Answer> results = new ArrayList<Answer>(q.getResultList());
        if(results.isEmpty() == true)
        {
            return null;
        }
        answerResult = results.get(0);
        return answerResult;
    }
    
    public void delete(Answer answer)
    {
        Answer answerToRemove = em.merge(answer);
        em.remove(answerToRemove);
    }
}
