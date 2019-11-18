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
        final String failedBinding = "update-product";
        Product updatedProduct = new Product();
        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setPreviousId(product.getId());

        Product oldProduct = getProductById(product.getId());

        String firstUpdateResult = changeOnProduct(updatedProduct, result, model, failedBinding, "index", productRepository::save);
        oldProduct.setNextId(updatedProduct.getId());
        String secondUpdateResult =  changeOnProduct(oldProduct, result, model, failedBinding, "index", productRepository::save);
        return firstUpdateResult.equals(secondUpdateResult) ? firstUpdateResult : failedBinding;
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
        Product product = getProductById(id);
        model.addAttribute("product", product);
        return "update-product";
    }

    private Product getProductById(@PathVariable("id") long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") long id, Model model) {
        Product product = getProductById(id);
        productRepository.delete(product);
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

}