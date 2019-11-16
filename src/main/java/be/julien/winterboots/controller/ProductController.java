package be.julien.winterboots.controller;

import be.julien.winterboots.Logger;
import be.julien.winterboots.entities.Product;
import be.julien.winterboots.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Controller
public class ProductController implements Logger {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/createProduct")
    public String showCreateProductForm(Product product) {
        return "add-product";
    }

    @PostMapping("/addProduct")
    public String addProduct(@Valid Product product, BindingResult result, Model model) {
        return changeOnProduct(product, result, model, "add-product", "index", productRepository::save);
    }

    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable("id") long id, @Valid Product product, BindingResult result, Model model) {
        return changeOnProduct(product, result, model, "update-product", "index", productRepository::save);
    }

    private String changeOnProduct(@Valid Product product, BindingResult result, Model model, String errorBinding, String successBinding, UnaryOperator<Product> productOperation) {
        if (result.hasErrors()) {
            logError(result.getAllErrors());
            return errorBinding;
        }
        logInfo("Product processed: " + product.getName() + " and binding: " + successBinding);
        productOperation.apply(product);
        model.addAttribute("products", productRepository.findAll());
        return successBinding;
    }

    @GetMapping("/editProduct/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return "update-product";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        productRepository.delete(product);
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

}