package ru.geekbrains.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.entites.Cost;
import ru.geekbrains.entites.Greeting;
import ru.geekbrains.entites.Message;
import ru.geekbrains.services.ShoppingCartService;
import ru.geekbrains.utils.ShoppingCart;

import javax.servlet.http.HttpSession;

@Controller
public class ShopControllerWs {
    private final Cost cost = new Cost();

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private HttpSession httpSession;

    @ModelAttribute("cost")
    public Cost getCost(){
        return cost;
    }

    @RequestMapping({"/", "/shop-page.html"})
    public String get(){
        return "shop-page";
    }

    @MessageMapping("/cost")
    @SendTo("/topic/cost")
    public Greeting greeting(Message message){
        ShoppingCartService cart = (ShoppingCartService) httpSession.getAttribute("cart");
//        System.out.println(cart.getTotalCost(httpSession) + "Error");
    return new Greeting(cart.getTotalCost(httpSession)  + "Общая стоимость товара!");

    }

}


