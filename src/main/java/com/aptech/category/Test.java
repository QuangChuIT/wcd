package com.aptech.category;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.getAll();
        for (Category category : categoryList) {
            System.out.println(category.getTitle());
            System.out.println(category.getCreatedDate());
        }

    }
}
