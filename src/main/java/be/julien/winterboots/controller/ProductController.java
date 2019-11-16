package be.julien.winterboots.controller;

import be.julien.winterboots.entities.ProductEntity;
import be.julien.winterboots.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/createProduct")
    public String showCreateProductForm(ProductEntity product) {
        return "add-product";
    }

    @PostMapping("/addProduct")
    public String addProduct(@Valid ProductEntity product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-product";
        }

        productRepository.save(product);
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return "update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") long id, @Valid ProductEntity product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            return "update-product";
        }

        productRepository.save(product);
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id, Model model) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        productRepository.delete(product);
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

}