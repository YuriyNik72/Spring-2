package ru.geekbrains.utils;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AppLoggingAspect {

    @After("execution(public void ru.geekbrains.services.ShoppingCartService.addToCart(..))") // pointcut expression
    public void aopSimpleMethod() {
        System.out.println("добален товар в корзину");
    }
}
