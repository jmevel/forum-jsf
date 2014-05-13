package com.supinfo.supforum.controller;

import com.supinfo.supforum.entity.User;
import com.supinfo.supforum.entity.UserType;
import com.supinfo.supforum.service.UserService;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class HomeController 
{
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    
    public String goHome()
    {
        User user = (User)userController.getLoggedUser();
        if(user == null)
        {
             return "/index.xhtml?faces-redirect=true";
        }
        else if(user.getType() == UserType.User)
        {
             return "/auth/home_user.xhtml?faces-redirect=true";
        }
        else if(user.getType() == UserType.Moderator)
        {
            return "/auth/home_moderator.xhtml?faces-redirect=true";
        }
        else if(user.getType() == UserType.Administrator)
        {
            return "/auth/home_administrator.xhtml?faces-redirect=true";
        }
        else
        {
            return "/index.xhtml?faces-redirect=true";
        }
            
    }
    
    public String goPublicForum()
    {
        return "/forum.xhtml?faces-redirect=true";
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
    
    
}
