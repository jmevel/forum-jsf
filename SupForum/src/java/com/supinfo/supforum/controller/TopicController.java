package com.supinfo.supforum.controller;

import com.supinfo.supforum.entity.Answer;
import com.supinfo.supforum.entity.Board;
import com.supinfo.supforum.entity.Topic;
import com.supinfo.supforum.entity.User;
import com.supinfo.supforum.entity.UserType;
import com.supinfo.supforum.service.AnswerService;
import com.supinfo.supforum.service.BoardService;
import com.supinfo.supforum.service.TopicService;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@ManagedBean
@SessionScoped
public class TopicController 
{
    @EJB
    TopicService topicService = new TopicService();
    
    @EJB
    BoardService boardService = new BoardService();
    
    @EJB
    AnswerService answerService = new AnswerService();
    
    private Topic topic;
    
    private ArrayList<Answer> answers = getAnswerOrderByAsc();
    
    @NotNull(message = "You have to type a message before posting")
    @NotEmpty(message = "Your message cannot be empty")
    private String answerMessage;
    
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    @ManagedProperty(value="#{topic.id}")
    private Long id;
    
    @ManagedProperty(value="#{board.id}")
    private Long boardId;
    
    private Topic newTopic = new Topic();

    public TopicController()
    {
//        this.newTopic = new Topic();
    }
    
    

    public ArrayList<Topic> getTopics(Long id) 
    {
        Board board = boardService.getByid(id);
        ArrayList<Topic> topicsResult = new ArrayList<>(topicService.getAll(board));
        return topicsResult;
    }
    
    public ArrayList<Answer> getAnswerOrderByAsc()
    {
        if(topic != null)
        {
            return new ArrayList<Answer>(answerService.getAll(topic));
        }
        return null;
    }
    
    public String postAnswer()
    {
        Answer answer = new Answer();
        answer.setContent(this.answerMessage);
        topic.addAnswer(answer);
        topic.setUpdateDate(new Date());
        userController.getLoggedUser().addAnswer(answer);
        answerService.create(answer);
        topicService.update(topic);
        this.answerMessage = null;
        return null;
    }
    
    public String getTopic(Long topicId)
    {
        topic = topicService.getByid(topicId);
        User user = (User)userController.getLoggedUser();
        if(user == null)
        {
            return "/index.xhtml?faces-redirect=true";
        }
        else if(user.getType()==UserType.User)
        {
            return "/auth/topic_user.xhtml?faces-redirect=true";
        }
        else if(user.getType()==UserType.Moderator || user.getType()==UserType.Administrator)
        {
            return "/auth/topic_moderator.xhtml?faces-redirect=true";
        }
        else
        {
             return "/index.xhtml?faces-redirect=true";
        }
    }
    
    public void titleChanged(ValueChangeEvent e)
    {
        String newTitle = (String)e.getNewValue();
        topic.setTitle(newTitle);
        topicService.update(topic);
    }
    
    public void updateTopic()
    {
        topicService.update(topic);
    }
    
    public void updateAnswer(Answer answerResult)
    {
        answerService.update(answerResult);
    }
    
    public String deleteTopic()
    {
        User user = (User)userController.getLoggedUser();
        if(user.getType() == UserType.User)
        {
            return "/index.xhtml?faces-redirect=true";
        }
        topicService.delete(topic);
        if(user.getType()==UserType.Moderator)
        {
             return "/auth/home_moderator.xhtml?faces-redirect=true";
        }
        else if(user.getType()==UserType.Administrator)
        {
            return "/auth/home_administrator.xhtml?faces-redirect=true";
        }
        else
        {
            return "/index.xhtml?faces-redirect=true";
        }
    }
    
    public String deleteTopicById(Long topicId)
    {
        User user = (User)userController.getLoggedUser();
        if(user.getType() == UserType.User)
        {
            return "/index.xhtml?faces-redirect=true";
        }
        Topic topicResult = topicService.getByid(topicId);
        topicService.delete(topicResult);
        if(user.getType()==UserType.Moderator)
        {
             return "/auth/home_moderator.xhtml?faces-redirect=true";
        }
        else if(user.getType()==UserType.Administrator)
        {
            return "/auth/home_administrator.xhtml?faces-redirect=true";
        }
        else
        {
            return "/index.xhtml?faces-redirect=true";
        }
    }
    
    public void deleteAnswer(Long answerId)
    {
        Answer answerResult = answerService.getByid(answerId);
        for(int i=0; i<topic.getAnswers().size(); i++)
        {
            if(topic.getAnswers().get(i).getId().equals(answerId))
            {
                topic.getAnswers().remove(i);
                topicService.update(topic);
                answerService.delete(answerResult);
            }
        } 
    }
    
    public String createTopic()
    {
        User user = (User)userController.getLoggedUser();
        if(user == null)
        {
            return "/index.xhtml?faces-redirect=true";
        }
        String boardId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("newTopicBoardId");
        Board board = boardService.getByid(Long.parseLong(boardId));
        board.addTopic(newTopic);
        user.addTopic(newTopic);
        topicService.create(newTopic);
        this.topic = newTopic;
        newTopic = new Topic();
        if(user.getType()==UserType.User)
        {
            return "/auth/topic_user.xhtml?faces-redirect=true";
        }
        else if(user.getType()==UserType.Moderator || user.getType()==UserType.Administrator)
        {
            return "/auth/topic_moderator.xhtml?faces-redirect=true";
        }
        else
        {
             return "/index.xhtml?faces-redirect=true";
        }
    }
    
    public Long getTopicCount()
    {
        return topicService.getTopicCount();
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public String getAnswerMessage() {
        return answerMessage;
    }

    public void setAnswerMessage(String answerMessage) {
        this.answerMessage = answerMessage;
    }

    public Topic getNewTopic() {
        return newTopic;
    }

    public void setNewTopic(Topic newTopic) {
        this.newTopic = newTopic;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
    
    
}
