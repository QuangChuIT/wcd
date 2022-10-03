package com.aptech.cart;

import com.aptech.product.Product;
import com.aptech.product.ProductDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "CartServlet", urlPatterns = "/admin/carts")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("addToCart")) {
            this.doAddToCart(req, resp);
        }
    }

    private void doAddToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        long productId = Long.parseLong(req.getParameter("productId"));
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        Optional<Product> productOptional = ProductDao.getInstance().getById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            cartItem.setProductName(product.getTitle());
            cartItem.setPrice(product.getPrice());
        }
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("carts");
        if (cart == null) {
            cart = new Cart();
            List<CartItem> cartItems = new ArrayList<>();
            cartItems.add(cartItem);
            cart.setItems(cartItems);
        } else {
            cart.add(cartItem);
        }
        session.setAttribute("carts", cart);
        List<Product> products = ProductDao.getInstance().getAll();
        req.setAttribute("products", products);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/views/product/productList.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
