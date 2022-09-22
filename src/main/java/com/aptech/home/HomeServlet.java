package com.aptech.home;


import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/views/home/index.jsp");
        requestDispatcher.forward(req, resp);
    }

}
