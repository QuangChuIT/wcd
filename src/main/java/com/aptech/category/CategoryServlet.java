package com.aptech.category;

import com.aptech.user.User;
import com.aptech.user.UserDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;


import java.io.IOException;
import java.util.List;

public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = UserDao.getInstance().getAll();
        req.setAttribute("users", users);
        RequestDispatcher dispatcher = this.getServletContext()
                .getRequestDispatcher("/views/category/index.jsp");
        dispatcher.forward(req, resp);
    }

}
