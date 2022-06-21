package ru.geekbrains.rest.controllers;

import ru.geekbrains.entites.Product;
import ru.geekbrains.rest.entites.CustomPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/products")
public interface ProductClient {

    @GetMapping("/product")
    List<ru.geekbrains.entites.Product> getAllProducts();

    @GetMapping("/product/{id}")
    ru.geekbrains.entites.Product getProductById(@PathVariable("id") Long id);

    @GetMapping(value = "/product/page")
    CustomPage<Product> getProductsWithPagingAndFiltering(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize,
            @RequestParam(value = "word", required = false) String word,
            @RequestParam(value = "min", required = false) Double min,
            @RequestParam(value = "max", required = false) Double max);
}
