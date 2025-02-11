package org.se.lab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/controller")
public class ShoppingCartController
{

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @GetMapping
    public String showCart(HttpSession session, Model model)
    {
        // Retrieve or initialize the shopping cart in the session.
        @SuppressWarnings("unchecked")
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart == null)
        {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cart", cart);
        model.addAttribute("message", "");
        model.addAttribute("now", new Date());
        return "shoppingCart"; // This returns the Thymeleaf template shoppingCart.html
    }

    @PostMapping
    public String addProduct(@RequestParam(required = false) String name,
                             @RequestParam(required = false) String quantity,
                             @RequestParam(required = false) String action,
                             HttpSession session,
                             Model model)
    {
        @SuppressWarnings("unchecked")
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart == null)
        {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        String message = "";
        if ("Add".equals(action))
        {
            Product product = new Product(name, quantity);
            logger.debug("Adding product: {}", product);
            cart.add(product);
            message = "Added " + product.getQuantity() + " x " + product.getName() + " to the cart";
        }
        model.addAttribute("cart", cart);
        model.addAttribute("message", message);
        model.addAttribute("now", new Date());
        return "shoppingCart";
    }
}
