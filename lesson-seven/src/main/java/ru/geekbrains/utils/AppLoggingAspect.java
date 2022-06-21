package ru.geekbrains.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.geekbrains.entites.Product;
import ru.geekbrains.services.ProductService;
import ru.geekbrains.services.ShoppingCartService;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Aspect
@Component
public class AppLoggingAspect {

    private ShoppingCart shoppingCart;
    private Map<String, Long> services = Collections.synchronizedMap(new HashMap<>());

    private final HttpSession httpSession;
    private SimpMessagingTemplate template;
    @Autowired
    public AppLoggingAspect(HttpSession httpSession, SimpMessagingTemplate template){
        this.httpSession = httpSession;
        this.template = template;
    }

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

    @Pointcut("execution(public * ru.geekbrains.services.ShoppingCartService.removeFromCart(..))")
    public void aopLoggingRemoveProductFromCart(){
        Logger logger = Logger.getLogger(String.valueOf(ShoppingCartService.class));
        logger.info("Товар удален из корзины");
    }
    @After(value="aopSimpleMethodAfterInCart() || aopLoggingRemoveProductFromCart()")
    public void aopRecalculateCart() throws InterruptedException{
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
