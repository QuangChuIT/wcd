package com.aptech.authen;

import com.aptech.user.User;
import com.aptech.user.UserDao;
import com.aptech.utils.CookieUtil;
import com.aptech.utils.MessageUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
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

        User user = UserDao.getInstance().login(userName, password);
        HttpSession session = req.getSession();
        if (user != null) {
            session.setAttribute("user", user);
            JSONObject object = new JSONObject();
            object.put("username", user.getUsername());
            object.put("name", user.getName());
            object.put("email", user.getEmail());
            object.put("lastLoginTime", DATE_FORMAT.format(new Date()));
            // Encode
            String asB64 = Base64.getEncoder().encodeToString(object.toString().getBytes(StandardCharsets.UTF_8));
            Cookie cookie = CookieUtil.createCookie("wcd-token", asB64, 3600, "localhost", false, false);
            resp.addCookie(cookie);
            resp.sendRedirect("/admin/index");
        } else {
            req.setAttribute("errorMessage", MessageUtil.getProperty("invalid_username_password"));
            this.doGet(req, resp);
        }
    }

    private final static Logger LOGGER = LogManager.getLogger(LoginServlet.class);
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
}
