package com.supinfo.supforum.controller;

/*import com.supinfo.supforum.entity.Administrator;
import com.supinfo.supforum.entity.Moderator;*/
import com.supinfo.supforum.entity.User;
import com.supinfo.supforum.entity.UserType;
import com.supinfo.supforum.service.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;


@ManagedBean
@SessionScoped
public class UserController {
    
    @EJB
    UserService userService = new UserService();
    
    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username cannot be empty")
    private String username;
    
    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    
    @NotNull(message = "Password confirmation is required")
    @NotEmpty(message = "Password confirmation cannot be empty")
    private String passwordConfim;
   
    private List<String> type;
    
    private String selectedType;

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public String getSelectedType() {
        return selectedType;
    }
    
    private User loggedUser;

    public List<String> getType() 
    {
        List<String> result = new ArrayList<String>();
        for(UserType userType: UserType.values()) 
        {
            result.add(userType.toString());
        }
        return result;
    }
    
    public String login() {
        loggedUser = userService.login(username, password);
        
        if(loggedUser == null) {
            return null;
        }
        
        username = null;
        password = null;
        
        if(loggedUser.getType().equals(UserType.Administrator)) 
        {
            return "/auth/home_administrator.xhtml?faces-redirect=true";
        }
        else if(loggedUser.getType().equals(UserType.Moderator)) 
        {
            return "/auth/home_moderator.xhtml?faces-redirect=true";
        }
        else
        {
             return "/auth/home_user.xhtml?faces-redirect=true";
        }
    }
    
    public String register()
    {
        if(username == null || password == null || passwordConfim == null)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You have to specify all the fields"));
            return "/index.xhtml?faces-redirect=true";
        }
        if(!password.equals(passwordConfim))
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Passwords don't match"));
            return "/index.xhtml?faces-redirect=true";
        }
        User user = new User();
        if(selectedType.equals("User"))
        {
            user.setType(UserType.User);
        }
        else if(selectedType.equals("Moderator"))
        {
           user.setType(UserType.Moderator);
        }
        else if(selectedType.equals("Administrator"))
        {
            user.setType(UserType.Administrator);
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You have to choose user type"));
            return "/index.xhtml?faces-redirect=true";
        }
        user.setUsername(username);
        user.setPassword(password);
        User newUser = userService.create(user);
        if(newUser==null)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Username already taken"));
            return "/index.xhtml?faces-redirect=true";
        }
        else
        {
            String returnResult = login();
            return returnResult;
        }
    }    
    
        
    public Long getUserCount()
    {
        return userService.getUserCount();
    }
    
    public String logout()
    {
        loggedUser=null;
        return "/index.xhtml?faces-redirect=true";
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPasswordConfim(String passwordConfim) {
        this.passwordConfim = passwordConfim;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public UserService getUserService() {
        return userService;
    }

    public String getPasswordConfim() {
        return passwordConfim;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}