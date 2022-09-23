package com.aptech.user;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            this.doGetList(req, resp);
        } else if (action.equals("edit")) {
            this.showFormEdit(req, resp);
        } else if (action.equals("update")) {
            this.doUpdate(req, resp);
        }
    }

    protected void doGetList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = UserDao.getInstance().getAll();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/views/user/users.jsp").forward(request, response);
    }

    protected void showFormEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        long userId = Long.parseLong(id);
        Optional<User> user = UserDao.getInstance().get(userId);
        if (user.isPresent()) {
            request.setAttribute("user", user.get());
            request.getRequestDispatcher("/views/user/edit.jsp").forward(request, response);
        } else {
            this.doGetList(request, response);
        }
    }

    public void doUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String password = request.getParameter("password");
            String mobile = request.getParameter("mobile");
            Long id = Long.valueOf(request.getParameter("userId"));
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setAddress(address);
            user.setPassword(password);
            user.setMobile(mobile);
            UserDao.getInstance().update(user);
            this.doGetList(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
