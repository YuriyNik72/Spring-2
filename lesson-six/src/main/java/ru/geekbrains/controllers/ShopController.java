package ru.geekbrains.controllers;


import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.geekbrains.entites.CustomPage;
import ru.geekbrains.entites.Order;
import ru.geekbrains.entites.Product;
import ru.geekbrains.entites.User;
import ru.geekbrains.repositories.specifications.ProductSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.services.*;
import ru.geekbrains.utils.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private static final int INITIAL_PAGE = 0;
    private static final int PAGE_SIZE = 3;

    private MailService mailService;
    private UserService userService;
    private OrderService orderService;
    private ProductService productService;
    private ShoppingCartService shoppingCartService;
    private DeliveryAddressService deliverAddressService;
    private SimpMessagingTemplate template;

    private ProductClient productClient;

    private Logger logger = Logger.getLogger(String.valueOf(ShopController.class));

    @Autowired
    public void setProductClient(ProductClient productClient){
        this.productClient = productClient;
    }


    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setShoppingCartService(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setDeliverAddressService(DeliveryAddressService deliverAddressService) {
        this.deliverAddressService = deliverAddressService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setTemplate(SimpMessagingTemplate template){
        this.template = template;
    }

    @GetMapping
    public String shopPage(Model model,
                           @RequestParam(value = "page") Optional<Integer> page,
                           @RequestParam(value = "word", required = false) String word,
                           @RequestParam(value = "min", required = false) Double min,
                           @RequestParam(value = "max", required = false) Double max
    ) {
        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

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

//        Page<Product> products = productService.getProductsWithPagingAndFiltering(currentPage, PAGE_SIZE, spec);
        CustomPage<Product> productsList = productClient.getProductsWithPagingAndFiltering(currentPage, PAGE_SIZE, word, min, max);
        Page<Product> products = new PageImpl<>(productsList.getList(), PageRequest.of(currentPage,PAGE_SIZE), productsList.getTotalCount());

        model.addAttribute("products", products.getContent());
        model.addAttribute("page", currentPage);
        model.addAttribute("totalPage", products.getTotalPages());

        model.addAttribute("filters", filters.toString());

        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("word", word);

        return "shop-page";
    }

    private final static String QUEUE_NAME = "hello";

    @GetMapping("/cart/add/{id}")
    public String addProductToCart(Model model, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        shoppingCartService.addToCart(httpServletRequest.getSession(), id);
        String referrer = httpServletRequest.getHeader("referer");

//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        try (Connection connection = factory.newConnection();
//             Channel channel = connection.createChannel()){
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            String msg = "Hello World!";
//            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
//            System.out.println("sent " + msg);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return "redirect:" + referrer;
    }

    @PostMapping("/order/confirm")
    public String orderConfirm(Model model, HttpServletRequest httpServletRequest, @ModelAttribute(name = "order") Order orderFromFrontend, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUserName(principal.getName());
        Order order = orderService.makeOrder(shoppingCartService.getCurrentCart(httpServletRequest.getSession()), user);
        order.setDeliveryAddress(orderFromFrontend.getDeliveryAddress());
        order.setPhoneNumber(orderFromFrontend.getPhoneNumber());
        order.setDeliveryDate(LocalDateTime.now().plusDays(7));
        order.setDeliveryPrice(0.0);
        order = orderService.saveOrder(order);
        model.addAttribute("order", order);
        return "order-filler";
    }

    @GetMapping("/order/result/{id}")
    public String orderConfirm(Model model, @PathVariable(name = "id") Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        // todo ждем до оплаты, проверка безопасности и проблема с повторной отправкой письма сделать одноразовый вход
        User user = userService.findByUserName(principal.getName());
        Order confirmedOrder = orderService.findById(id);
        if (!user.getId().equals(confirmedOrder.getUser().getId())) {
            return "redirect:/";
        }
        mailService.sendOrderMail(confirmedOrder);
        model.addAttribute("order", confirmedOrder);
        return "order-result";
    }

    @GetMapping("/order/fill")
    public String orderFill(HttpSession httpSession, Model model, Principal principal){
        User user = userService.findByUserName(principal.getName());
        ShoppingCart cart = shoppingCartService.getCurrentCart(httpSession);
        model.addAttribute("cart", cart);
        model.addAttribute("order", new Order());
        model.addAttribute("addresses", deliverAddressService.getUserAddresses(user.getId()));
        return "order-form";
    }
}
