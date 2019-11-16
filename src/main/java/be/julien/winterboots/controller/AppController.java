package be.julien.winterboots.controller;

import be.julien.winterboots.entities.ProductEntity;
import be.julien.winterboots.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    ProductRepository productRepository;

    @RequestMapping("/")
    public String showHomePage(Model model) {
        List<ProductEntity> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "index";
    }
}