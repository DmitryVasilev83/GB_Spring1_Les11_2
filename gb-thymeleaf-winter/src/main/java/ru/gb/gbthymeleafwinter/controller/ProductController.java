package ru.gb.gbthymeleafwinter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbthymeleafwinter.entity.Product;
import ru.gb.gbthymeleafwinter.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public String getProductList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product-list";
    }

    @GetMapping("/{productId}")
    public String info(Model model, @PathVariable(name = "productId") Long id) {
        Product product;
        if (id != null) {
            product = productService.findById(id);
        } else {
            return "redirect:/product/all";
        }
        model.addAttribute("product", product);
        return "product-info";
    }

    @GetMapping
    public String showForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        Product product;

        if (id != null) {
            product = productService.findById(id);
        } else {
            product = new Product();
        }
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping
    public String saveProduct(Product product) {
        productService.save(product);
        return "redirect:/product/all";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam(name = "id") Long id) {
        productService.deleteById(id);
        return "redirect:/product/all";
    }

    // DZ_11
    @GetMapping("/cart")
    public String getProductListFromCart(Model model){
        model.addAttribute("cartProducts", productService.findAllInCart());
        return "cart-list";
    }

    @GetMapping("/cart/add/{productId}")
    public String addProductToCart(@PathVariable("productId") Long id){
        productService.addProductToCart(id);
        return "redirect:/product/all";
    }

    @GetMapping("/cart/delete/{productId}")
    public String deleteProductFromCartById(@PathVariable("productId") Long id){
        productService.deleteProductFromCart(id);
        return "redirect:/product/cart";
    }

    @GetMapping("/loginMy")
    public String login(){

        return "loginMy";
    }
}
