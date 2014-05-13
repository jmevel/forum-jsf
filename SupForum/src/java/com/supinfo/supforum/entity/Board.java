package com.supinfo.supforum.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Board 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String name;
    
    @ManyToOne
    private Category category;
    
     
    @OneToMany(cascade=CascadeType.PERSIST)
    @JoinColumn(name="BOARD_ID")
    private Set<Topic> topics;
    
    public void addTopic(Topic topic)
    {
        if(this.topics==null)
        {
            this.topics = new HashSet<Topic>();
        }
        this.topics.add(topic);
        topic.setBoard(this);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }
}
