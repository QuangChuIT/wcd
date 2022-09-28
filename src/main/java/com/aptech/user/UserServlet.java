package com.aptech.user;


import com.aptech.utils.JSONConverter;
import com.aptech.utils.UserStatus;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "UserServlet", urlPatterns = {"/admin/users"})
public class UserServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            this.doGetList(req, resp);
        } else if (action.equals("edit")) {
            this.showFormEdit(req, resp);
        } else if (action.equals("update")) {
            this.doUpdate(req, resp);
        } else if (action.equals("delete")) {
            this.doDelete(req, resp);
        } else if (action.equals("list")) {
            this.doGetUsers(req, resp);
        }
    }

    protected void doGetList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = UserDao.getInstance().getAll();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/views/user/users.jsp").forward(request, response);
    }

    protected void doGetUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String userIds = JSONConverter.readJson(request);
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = JSONConverter.readJson(req);
        LOGGER.info("Request delete " + body);
        if (body != null && !body.equals("")) {
            JSONObject jsonObject = new JSONObject(body);
            LOGGER.info("Request delete " + jsonObject);
            resp.setContentType("application/json;charset=UTF-8");
            ServletOutputStream outputStream = resp.getOutputStream();
            outputStream.print(jsonObject.toString());
        }

    }

    public void doUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        long id = Long.parseLong(request.getParameter("id"));
        String email = request.getParameter("email");
        String[] status = request.getParameterValues("status");
        Optional<User> optionalUser = UserDao.getInstance().get(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(name);
            user.setEmail(email);
            user.setMobile(mobile);
            int newStatus = UserStatus.ACTIVE.getValue();
            int oldStatus = user.getStatus();
            if (status != null && oldStatus == UserStatus.ACTIVE.getValue()) {
                newStatus = UserStatus.LOCK.getValue();
            }
            user.setStatus(newStatus);
            try {
                UserDao.getInstance().update(user);
                response.sendRedirect("/admin/user/index");
            } catch (Exception e) {
                request.setAttribute("errorMessage", e.getMessage());
                request.setAttribute("user", user);
                request.getRequestDispatcher("/views/user/edit.jsp").forward(request, response);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private final static Logger LOGGER = LogManager.getLogger(JSONConverter.class);
}
