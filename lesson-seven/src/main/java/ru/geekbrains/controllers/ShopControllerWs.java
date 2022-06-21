package ru.geekbrains.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import ru.geekbrains.services.ShoppingCartService;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Controller
public class ShopControllerWs {

    private Logger logger = Logger.getLogger(String.valueOf(ShopController.class));
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShopControllerWs(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }


    @MessageMapping("/message")
    @SendToUser("/queue/cost")
    public Double getMessages(Message message) throws InterruptedException{
        HttpSession session = (HttpSession) SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders()).get("HTTP_SESSION");
        Double cartCost = shoppingCartService.getTotalCost(session);
        logger.info(String.valueOf(cartCost));
    return cartCost;

    }

}


