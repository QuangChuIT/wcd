package com.aptech.authen;

import com.aptech.user.User;
import com.aptech.user.UserDao;
import com.aptech.utils.AESUtil;
import com.aptech.utils.MessageUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
        if (user != null) {
            String cookieValue = userName + ";" + user.getMobile() + ";" + user.getMobile() + ";" + DATE_FORMAT.format(new Date());
            Cookie cookie = new Cookie("wcd-token", AESUtil.encrypt(cookieValue));
            cookie.setMaxAge(3600);
            cookie.setDomain("localhost");
            resp.addCookie(cookie);
            resp.sendRedirect("/admin/index");
        } else {
            LOGGER.info("haiz {}", MessageUtil.getProperty("invalid_username_password"));
            req.setAttribute("errorMessage", MessageUtil.getProperty("invalid_username_password"));
            this.doGet(req, resp);
        }
    }
    private final static Logger LOGGER = LogManager.getLogger(LoginServlet.class);
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
}
