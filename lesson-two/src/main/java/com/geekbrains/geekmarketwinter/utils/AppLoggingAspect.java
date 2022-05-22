package com.geekbrains.geekmarketwinter.utils;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AppLoggingAspect {

    @After("execution(public void com.geekbrains.geekmarketwinter.services.ShoppingCartService.addToCart(..))") // pointcut expression
    public void aopSimpleMethod() {
        System.out.println("добален товар в корзину");
    }
}
