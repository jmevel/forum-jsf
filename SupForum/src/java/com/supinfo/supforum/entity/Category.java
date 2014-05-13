package com.supinfo.supforum.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Category 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String name;
    
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="CATEGORY_ID")
    private Set<Board> boards;

    public Long getId() {
        return id;
    }

    public void setBoards(Set boards) {
        this.boards = boards;
    }

    public void addBoard(Board board)
    {
        if(this.boards==null)
        {
            this.boards = new HashSet<Board>();
        }
        this.boards.add(board);
    }
    
    public Set<Board> getBoards() {
        return boards;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
