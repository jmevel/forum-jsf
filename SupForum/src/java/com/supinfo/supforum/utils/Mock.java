package com.supinfo.supforum.utils;

import com.supinfo.supforum.entity.Answer;
import com.supinfo.supforum.entity.Board;
import com.supinfo.supforum.entity.Category;
import com.supinfo.supforum.entity.Topic;
import com.supinfo.supforum.entity.User;
import com.supinfo.supforum.entity.UserType;
import com.supinfo.supforum.service.AnswerService;
import com.supinfo.supforum.service.BoardService;
import com.supinfo.supforum.service.CategoryService;
import com.supinfo.supforum.service.TopicService;
import com.supinfo.supforum.service.UserService;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class Mock 
{
    @EJB
    CategoryService categoryService = new CategoryService();
    
    @EJB
    UserService userService = new UserService();
    
    @EJB
    TopicService topicService = new TopicService();
    
    @EJB
    BoardService boardService = new BoardService();
    
    @EJB
    AnswerService answerService = new AnswerService();
    
    public void createDataInDb()
    {
        try
        {
            
            User koenigs = new User();
            koenigs.setUsername("Koenigs");
            koenigs.setPassword("toto");
            koenigs.setType(UserType.User);
            userService.create(koenigs);

            User noob = new User();
            noob.setUsername("n00b");
            noob.setPassword("tata");
            noob.setType(UserType.User);
            userService.create(noob);



            Board board1_1 = new Board();
            board1_1.setName("Development");
            boardService.create(board1_1);

            Topic topic1_1_1 = new Topic();
            topic1_1_1.setTitle("Become hacker");
            topic1_1_1.setContent("Hello, I'm 12 yo and I would like to become a great hacker. Do you have some advise to me? thank you");
            board1_1.addTopic(topic1_1_1);
            noob.addTopic(topic1_1_1);
            topicService.create(topic1_1_1);


            Answer answer = new Answer();
            answer.setContent("You have to learn jQuery and PHP");
            topic1_1_1.addAnswer(answer);
            koenigs.addAnswer(answer);
            answerService.create(answer);
            

            Topic topic1_1_2 = new Topic();
            topic1_1_2.setTitle("Lachez-vous");
            topic1_1_2.setContent("Topic qui sert a rien, c'est quoi le mieux: ASP.NET ou JEE ?");
            board1_1.addTopic(topic1_1_2);
            koenigs.addTopic(topic1_1_2);
            topicService.create(topic1_1_2);

            Category category1 = new Category();
            category1.setName("Courses");
            category1.addBoard(board1_1);

     /*-------------------------------------------------------------------------*/

            Board board1_2 = new Board();
            board1_2.setName("Infrastructure");
            boardService.create(board1_2);

            Topic topic1_2_1 = new Topic();
            topic1_2_1.setTitle("Mail server");
            topic1_2_1.setContent("I've problems to install mail serveur on Debian.");
            board1_2.addTopic(topic1_2_1);
            noob.addTopic(topic1_2_1);
            topicService.create(topic1_2_1);

            Topic topic1_2_2 = new Topic();
            topic1_2_2.setTitle("Upgrade Windows server");
            topic1_2_2.setContent("I want to upgrade my Windows NT 3.1 directly to Windows Server 2012 but I don't know how to do. HELP ME PLEAAAAAAAASE !!!!");
            board1_2.addTopic(topic1_2_2);
            koenigs.addTopic(topic1_2_2);
            topicService.create(topic1_2_2);

            category1.addBoard(board1_2);

            categoryService.create(category1);

    /**************************************************************************/

            Category category2 = new Category();
            category2.setName("Labs");

            Board board2_1 = new Board();
            board2_1.setName(".NET");
            boardService.create(board2_1);

            Topic topic2_1_1 = new Topic();
            topic2_1_1.setTitle("Lambda expressions");
            topic2_1_1.setContent("Lambda expressions it's SO awesome!");
            board2_1.addTopic(topic2_1_1);
            koenigs.addTopic(topic2_1_1);
            topicService.create(topic2_1_1);
            

            Topic topic2_1_2 = new Topic();
            topic2_1_2.setTitle("How to configure TFS");
            topic2_1_2.setContent("If you want to configure TFS, just follow the steps: nothing to do it's magic!");
            board2_1.addTopic(topic2_1_2);
            noob.addTopic(topic2_1_2);
            topicService.create(topic2_1_2);

            category2.addBoard(board2_1);

     /*------------------------------------------------------------------------------*/       

            Board board2_2 = new Board();
            board2_2.setName("Java");
            boardService.create(board2_2);

            Topic topic2_2_1 = new Topic();
            topic2_2_1.setTitle("Lambda expressions");
            topic2_2_1.setContent("<troll>If you want to use lambda expressions in Java it's esay, just way Java 8 </troll>");
            board2_2.addTopic(topic2_2_1);
            koenigs.addTopic(topic2_2_1);
            topicService.create(topic2_2_1);

            Topic topic2_2_2 = new Topic();
            topic2_2_2.setTitle("JSF");
            topic2_2_2.setContent("JSF sucks!");
            board2_2.addTopic(topic2_2_2);
            noob.addTopic(topic2_2_2);
            topicService.create(topic2_2_2);

            category2.addBoard(board2_2);

            categoryService.create(category2);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
}
