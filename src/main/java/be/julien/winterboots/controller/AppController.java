package be.julien.winterboots.controller;

import be.julien.winterboots.repositories.OrderRepository;
import be.julien.winterboots.repositories.ProductRepository;
import be.julien.winterboots.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @RequestMapping("/productPage")
    public String showProductPage(Model model) {
        model.addAttribute("products", productRepository.findAllDistinctByName());
        return "products";
    }

    @RequestMapping("/userPage")
    public String showUserPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }


    @RequestMapping("/orderPage")
    public String showOrderPage(Model model) {
        showProductPage(model);
        showUserPage(model);
        return "orders";
    }
}