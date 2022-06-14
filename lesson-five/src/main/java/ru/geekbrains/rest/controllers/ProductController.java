package ru.geekbrains.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.entites.Product;
import ru.geekbrains.rest.entites.CustomPage;
import ru.geekbrains.repositories.specifications.ProductSpecs;
import ru.geekbrains.services.CategoryService;
import ru.geekbrains.services.ImageSaverService;
import ru.geekbrains.services.ProductService;

import java.util.List;

@RestController
public class ProductController implements ProductClient {
    private ProductService productService;
    private CategoryService categoryService;
    private ImageSaverService imageSaverService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setImageSaverService(ImageSaverService imageSaverService) {
        this.imageSaverService = imageSaverService;
    }
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    public CustomPage<Product> getProductsWithPagingAndFiltering(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize,
            @RequestParam(value = "word", required = false) String word,
            @RequestParam(value = "min", required = false) Double min,
            @RequestParam(value = "max", required = false) Double max) {

        Specification<Product> spec = Specification.where(null);
        StringBuilder filters = new StringBuilder();
        if (word != null) {
            spec = spec.and(ProductSpecs.titleContains(word));
            filters.append("&word=" + word);
        }
        if (min != null) {
            spec = spec.and(ProductSpecs.priceGreaterThanOrEq(min));
            filters.append("&min=" + min);
        }
        if (max != null) {
            spec = spec.and(ProductSpecs.priceLesserThanOrEq(max));
            filters.append("&max=" + max);
        }

        Page<Product> page = productService.getProductsWithPagingAndFiltering(pageNumber, pageSize, spec);
        return new CustomPage<>(page.getTotalElements(), page.getContent());
    }
}
