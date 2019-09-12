package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "Categories");

        return "category/index";
    }

    @RequestMapping(value="add", method= RequestMethod.GET)
    public String adds(Model model) {

        model.addAttribute("title", "Category");
        model.addAttribute("category", new Category());
        return "category/add";
    }

    @RequestMapping(value="add", method= RequestMethod.POST)
    public String adds(Model model, @ModelAttribute @Valid Category category, Errors errors) {

        if (errors.hasErrors()) {
            return "category/add";
        }
        categoryDao.save(category);
        return "redirect:/category";
    }
    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCategoryForm(Model model) {
        model.addAttribute("category", categoryDao.findAll());
        model.addAttribute("title", "Remove Category");
        return "category/remove";

    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCategoryForm(@RequestParam int[] categoryIds) {

        for (int categoryId : categoryIds) {
            categoryDao.delete(categoryId);
        }

        return "redirect:/category";
    }
}