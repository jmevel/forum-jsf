package com.supinfo.supforum.service;

import com.supinfo.supforum.entity.Board;
import com.supinfo.supforum.entity.Category;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class BoardService 
{
    @PersistenceContext(unitName = "SupForumPU")
    private EntityManager em;
    
    public List<Board> getAll(Category category)
    {
         List<Board> boards = new ArrayList<Board>();
         Query q = em.createQuery("SELECT b FROM Board b WHERE b.category.id=:id");
         q.setParameter("id", category.getId());
         boards = q.getResultList();
         return boards;
    }
    
    public Board getByid(Long id)
    {
        Board boardResult = new Board();
        Query q = em.createQuery("SELECT b FROM Board b WHERE b.id=:id");
        q.setParameter("id", id);
        String query1 = q.toString();
        ArrayList<Board> results = new ArrayList<Board>(q.getResultList());
        if(results.isEmpty() == true)
        {
            return null;
        }
        boardResult = results.get(0);
        return boardResult;
    }
    
    public Board create(Board board)
    {
        try
        {
            List<Board> boards = new ArrayList<Board>();
            Query q = em.createQuery("SELECT b FROM Board b WHERE b.name=:name");
            q.setParameter("name", board.getName());
        
            boards = q.getResultList();
            if(boards.isEmpty() != true)
            {
                return null;
            }
            em.persist(board);
            return board;
        }
        catch(Exception ex)
        {
            return null;
        }
    }
}
