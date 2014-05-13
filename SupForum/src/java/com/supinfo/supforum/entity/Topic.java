package com.supinfo.supforum.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Topic 
{

    public Topic()
    {
        this.setCreationDate(new Date());
        this.setUpdateDate(new Date());
    }
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String title;
    
    @Column
    private String content;
    
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    
    @ManyToOne
    private Board board;
    
    @OneToMany(cascade=CascadeType.PERSIST)
    @JoinColumn(name="TOPIC_ID")
    private List<Answer> answers;
    
    @ManyToOne
    private User user;

    public Date getCreationDate() 
    {
        return creationDate;
    }
    
    public String getFormatedCreationDate(String format) 
    {
        //String format = "dd/MM/yy H:mm"; 
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format); 
        return formater.format(creationDate);
    }

    public Date getUpdateDate() {
        return updateDate;
    }
    
     public String getFormatedUpdateDate(String format) 
    {
        //String format = "dd/MM/yy H:mm"; 
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format); 
        return formater.format(updateDate);
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    
   

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Board getBoard() {
        return board;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Answer> getAnswers()
    {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
    
    public void addAnswer(Answer answer)
    {
        if(this.answers == null)
        {
            this.answers = new ArrayList<Answer>();
        }
        this.answers.add(answer);
        answer.setTopic(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
