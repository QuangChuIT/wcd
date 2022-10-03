package com.aptech.category;

import com.aptech.user.User;
import com.aptech.user.UserDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", urlPatterns = {"/admin/categories"})
public class CategoryServlet extends HttpServlet {
    CategoryDao categoryDao = new CategoryDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = categoryDao.getAll();
        req.setAttribute("categories", categories);
        RequestDispatcher dispatcher = this.getServletContext()
                .getRequestDispatcher("/views/category/index.jsp");
        dispatcher.forward(req, resp);
    }
}
