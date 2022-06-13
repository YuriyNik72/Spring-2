package ru.geek.cloud.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
    public class CategoryClient {

        //geek-spring-cloud-eureka-feign-client
    @FeignClieng("geek-spring-cloud-summer-market")
    public interface CategoryClient {
        @RequestMapping("/market/get-category/{id}")
        Category getCategory(@PathVariable Integer id);

        @RequestMapping("/market/get-categories")
        List<Category> getAllCategories();
    }

}
