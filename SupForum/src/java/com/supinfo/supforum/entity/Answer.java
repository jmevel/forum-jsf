package com.supinfo.supforum.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Answer
{

    public Answer() 
    {
        this.setDate(new Date());
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
    private Date date;
    
    @ManyToOne
    private Topic topic;
    
    @ManyToOne
    private User user;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
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

    public Date getDate() {
        return date;
    }
    
    public String getFormatedDate(String format) 
    {
        //String format = "dd/MM/yy H:mm"; 
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format); 
        return formater.format(date);
    }

    public Topic getTopic() {
        return topic;
    }

   public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
}
