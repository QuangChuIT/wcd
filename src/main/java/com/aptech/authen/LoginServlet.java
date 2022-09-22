package com.aptech.authen;

import com.aptech.user.User;
import com.aptech.user.UserDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/views/login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");

        User user = UserDao.getInstance().findUser(userName, password);
        Cookie cookie = new Cookie("UserLogin", user.getUsername());
        cookie.setMaxAge(60);
        cookie.setDomain("localhost");
        resp.addCookie(cookie);
        resp.sendRedirect("/admin/index");
    }
}
