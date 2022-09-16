package com.aptech.authen;

import com.aptech.user.User;
import com.aptech.user.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/views/login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userDao.findUser(userName, password);
        Cookie cookie = new Cookie("UserLogin", user.getUsername());
        cookie.setMaxAge(60);
        cookie.setDomain("localhost");
        resp.addCookie(cookie);
        resp.sendRedirect("/admin/index");
    }
}
