package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "Categories");

        return "category/index";
    }

    @RequestMapping(value="add", method= RequestMethod.GET)
    public String adds(Model model) {

        model.addAttribute("title", "Add Category");
        model.addAttribute("category", new Category());
        return "category/add";
    }

    @RequestMapping(value="add", method= RequestMethod.POST)
    public String adds(Model model, @ModelAttribute @Valid Category category, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Category");
            return "category/add";
        }
        categoryDao.save(category);
        return "redirect:/category";
    }
    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCategoryForm(Model model) {
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "Remove Category");
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "category/remove";

    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCategoryForm(@RequestParam int[] categoryIds) {

        for (int categoryId : categoryIds) {
            categoryDao.delete(categoryId);
        }

        return "redirect:/category";
    }
    @RequestMapping(value = "edit/{categoryId}", method = RequestMethod.GET)
    public String displayEditCategoryForm(Model model, @PathVariable int categoryId) {

        model.addAttribute("title", "Edit Category");
        model.addAttribute("category", categoryDao.findOne(categoryId));
        return "category/edit";
    }

    @RequestMapping(value = "edit/{categoryId}", method = RequestMethod.POST)
    public String processEditForm(Model model, @PathVariable int categoryId, @ModelAttribute @Valid Category newCategory,
                                  Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Category");
            return "category/edit";
        }

        Category editedCategory = categoryDao.findOne(categoryId);
        editedCategory.setName(newCategory.getName());
        categoryDao.save(editedCategory);

        return "redirect:/category";
    }
}