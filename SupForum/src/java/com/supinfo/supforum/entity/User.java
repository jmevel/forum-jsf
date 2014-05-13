package com.supinfo.supforum.entity;

import com.supinfo.supforum.constraint.CheckPassword;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username cannot be empty")
    @Size(min=3, max=15, message = "Username must have between 3 and 15 characters")
    @Column
    private String username;
    
    @NotNull(message = "Password is required")
    @CheckPassword
    @Column
    private String password;
    
    @OneToMany(cascade=CascadeType.PERSIST)
    @JoinColumn(name="USER_ID")
    private Set<Topic> topics;
    
    @Column
    private UserType type;

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
    
    
    
    @OneToMany(cascade=CascadeType.PERSIST)
    @JoinColumn(name="USER_ID")
    private Set<Answer> answers;

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) 
    {
        this.password = encryptPassword(password);
    }
    
    public static String encryptPassword(String password)
     {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new Error("No MD5 support in this VM");
        }
        md.update(password.getBytes(), 0, password.length());
        String encryptedPassword = new BigInteger(1, md.digest()).toString(16);
        return encryptedPassword;
    }

    public String getUsername() {
        return username;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supinfo.supforum.entity.User[ id=" + id + " ]";
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }
    
    public void addTopic(Topic topic)
    {
        if(this.topics == null)
        {
            this.topics = new HashSet<>();
        }
        this.topics.add(topic);
        topic.setUser(this);
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }
    
    public void addAnswer(Answer answer)
    {
        if(this.answers == null)
        {
            this.answers = new HashSet<>();
        }
        this.answers.add(answer);
        answer.setUser(this);
    }
}
