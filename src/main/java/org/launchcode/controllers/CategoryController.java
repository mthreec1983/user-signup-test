package org.launchcode.controllers;

import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "category")

}
