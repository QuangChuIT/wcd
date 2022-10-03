package com.aptech.home;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "HomeServlet", urlPatterns = {"/admin/index"})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String abc = req.getParameter("abc");
        LOGGER.info("Abc parameter {}", abc);
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/views/home/index.jsp");
        requestDispatcher.forward(req, resp);
    }

    private final static Logger LOGGER = LogManager.getLogger(HomeServlet.class);

}
