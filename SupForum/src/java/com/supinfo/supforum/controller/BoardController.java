package com.supinfo.supforum.controller;

import com.supinfo.supforum.entity.Board;
import com.supinfo.supforum.entity.Category;
import com.supinfo.supforum.service.BoardService;
import com.supinfo.supforum.service.CategoryService;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class BoardController 
{
    @EJB
    BoardService boardService = new BoardService();
    
    @EJB
    CategoryService categoryService = new CategoryService();
    
    private ArrayList<Board> boards;

    public ArrayList<Board> getBoards(Long id) 
    {
        Category category = categoryService.getByid(id);
        ArrayList<Board> boardsResult = new ArrayList<>(boardService.getAll(category));
        return boardsResult;
    }

    public void setBoards(ArrayList<Board> boards) 
    {
        this.boards = boards;
    }   
}
