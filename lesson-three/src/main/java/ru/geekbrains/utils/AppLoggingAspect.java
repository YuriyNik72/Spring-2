package ru.geekbrains.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.geekbrains.entites.OrderItem;
import ru.geekbrains.entites.Product;
import ru.geekbrains.services.ProductService;
import ru.geekbrains.services.ShoppingCartService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Aspect
@Component
public class AppLoggingAspect {

    private ShoppingCart shoppingCart;
    private Map<String, Long> services = Collections.synchronizedMap(new HashMap<>());
    @Autowired
    private HttpSession httpSession;

        @After("execution(* ru.geekbrains..*(..,ru.geekbrains.entites.Product+,..))")    // pointcut expression
    public void aopSimpleMethod(JoinPoint joinPoint) {
        Logger logger = Logger.getLogger(String.valueOf(Product.class));
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Product) {
                logger.info(joinPoint.getSignature() + " работает с товаром: " + ((Product) arg).getTitle());
            }
        }
    }
    @Pointcut("execution(public void ru.geekbrains.services.ShoppingCartService.addToCart(..))") // pointcut expression
    public void aopSimpleMethodAfterInCart() {
        Logger logger = Logger.getLogger(String.valueOf(ShoppingCartService.class));
        logger.info("Товар добален в корзину");
    }

//    @Before("execution(public void ru.geekbrains.services.ShoppingCartService.addToCart(..))") // pointcut expression
//    public void aopSimpleMethodBeforeInCart() {
//        Logger logger = Logger.getLogger(String.valueOf(ShoppingCartService.class));
//        logger.info("Добаляем товар в корзину");
//
//    }

    @Pointcut("execution(public * ru.geekbrains.services.ShoppingCartService.removeFromCart(..))")
    public void aopLoggingRemoveProductFromCart(){
        Logger logger = Logger.getLogger(String.valueOf(ShoppingCartService.class));
        logger.info("Товар удален из корзины");
    }
    @After(value="aopSimpleMethodAfterInCart() || aopLoggingRemoveProductFromCart()")
    public void recalculateCart(){
        Logger logger = Logger.getLogger(String.valueOf(ShoppingCartService.class));
        ShoppingCart cart = (ShoppingCart) httpSession.getAttribute("cart");
        logger.info("Cart total cost: " + cart.getTotalCost());
    }

    @After("execution(public * ru.geekbrains.services.ProductService.saveProduct(..))")
    public void aopLoggingSaveProductAfter (){
        Logger logger = Logger.getLogger(String.valueOf(ProductService.class));
        logger.info("Товар добавлен в каталог");
    }

    @Before("execution(public * ru.geekbrains.services.ProductService.saveProduct(..))")
    public void aopLoggingSaveProductBefore (){
        Logger logger = Logger.getLogger(String.valueOf(ProductService.class));
        logger.info("Товар добавляется в каталог");
    }

    @Before("execution(public * ru.geekbrains.services.ShoppingCartService.addToCart(..)) && args(*, product,..)")
    public void aopRecalculateAdd(Product product){
//       shoppingCart.recalculate();
        Logger logger = Logger.getLogger(String.valueOf(ShoppingCartService.class));
        logger.info("Товар пересчитан");
        System.out.println("товар пересчитан_" + product.getTitle());
//         totalCost = 0.0;
//                for (OrderItem o : shoppingCart.getItems()) {
//                    o.setTotalPrice(o.getQuantity() * o.getProduct().getPrice());
//                    totalCost += o.getTotalPrice();
//                }

    }
    @Around("execution(public * ru.geekbrains.services.*Service.*(..))")
    public Object aopMethodTimeMeter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Logger logger = Logger.getLogger(String.valueOf(Service.class));

        System.out.println("Start Service");
        long begin = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        String[] tokens = proceedingJoinPoint.getSignature().getDeclaringTypeName().split("\\.");
        String serviceName = tokens[tokens.length - 1];
        long duration = end - begin;
        if (services.containsKey(serviceName)) {
            services.put(serviceName, services.get(serviceName) + duration);
        } else {
            services.put(serviceName, duration);
            for (String key : services.keySet()) {
                logger.info( key + ": Время работы " + services.get(key) + "ms");
            }
            System.out.println("End Service");
        }
        return result;
    }
}
