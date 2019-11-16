package be.julien.winterboots.controller;

import be.julien.winterboots.entities.Product;
import be.julien.winterboots.entities.User;
import be.julien.winterboots.repositories.ProductRepository;
import be.julien.winterboots.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/productPage")
    public String showProductPage(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping("/userPage")
    public String showUserPage(Model model) {
        List<User> products = userRepository.findAll();
        model.addAttribute("users", products);
        return "users";
    }
}