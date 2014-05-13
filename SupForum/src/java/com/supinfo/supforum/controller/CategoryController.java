package com.supinfo.supforum.controller;

import com.supinfo.supforum.entity.Category;
import com.supinfo.supforum.service.CategoryService;
import com.supinfo.supforum.utils.Mock;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CategoryController 
{
    @EJB
    Mock mock = new Mock();
    
    @EJB
    CategoryService categoryService = new CategoryService();
    
    private ArrayList<Category> categories;
    
    private boolean isFakeDataCreated=false;

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Category> getCategories() 
    {
        ArrayList<Category> categoriesResult = new ArrayList<>(categoryService.getAll());
        return categoriesResult;
    }
    
    public void create()
    {
        if(isFakeDataCreated == false)
        {
            mock.createDataInDb();
            isFakeDataCreated = true;
        }
    }
    
    
}
