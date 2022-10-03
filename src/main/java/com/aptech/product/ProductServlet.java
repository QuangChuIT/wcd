package com.aptech.product;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(name = "ProductServlet", urlPatterns = {"/admin/products"})
public class ProductServlet extends HttpServlet {
}
