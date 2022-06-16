package ru.geekbrains.lessonseven;

import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.entites.Product;
import ru.geekbrains.services.ProductService;
import ru.geekbrains.services.ShoppingCartService;
import ru.geekbrains.utils.ShoppingCart;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTest {

//    @Autowired
//    private MockMvc mockMvc;

    @Mock
    MockHttpSession mockHttpSession;

    @Mock
    HttpSession httpSession;

    @Mock
    ProductService productService;

    ShoppingCartService shoppingCartService;
    Product product;
    ShoppingCart currentCart;

    @Before
    public void init(){
        product = new Product();
        product.setId(1L);
        product.setPrice(Double.valueOf(50L));
        product.setTitle("Смартфон");

        shoppingCartService = new ShoppingCartService();
        shoppingCartService.setProductService(productService);
        when(productService.getProductById(product.getId())).thenReturn(product);

        currentCart = new ShoppingCart();
        when(httpSession.getAttribute("cart")).thenReturn(currentCart);
    }

//        @Test
//    public void getCurrentCartTest() throws Exception {
//        ShoppingCart shoppingCart = shoppingCartService.getCurrentCart(httpSession);
//
//
////       ShoppingCart shoppingCart = new ShoppingCart();
//        shoppingCart.add(product);
//
//    when(mockHttpSession.getAttribute("cart")).thenReturn(currentCart);
//        mockMvc.perform(g.session(mockHttpSession))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(content().string(StringContains.containsString("cart")));
//    }
    @Test
    public void getCurrentCartTest() {
        ShoppingCart currentCart = shoppingCartService.getCurrentCart(mockHttpSession);
        assertEquals(currentCart.getTotalCost(), Double.valueOf(0));
    }

    @Test
    public void addToCartTest(){
        shoppingCartService.addToCart(httpSession, product.getId());
        assertEquals(Optional.of(product.getPrice()), Optional.ofNullable(currentCart.getTotalCost()));
    }

    @Test
    public void removeFromCartTest(){
        currentCart.add(product);
        shoppingCartService.removeFromCart(httpSession, product.getId());
        assertEquals(Double.valueOf(0), currentCart.getTotalCost());
    }

    @Test
    public void setProductCountTest(){
        currentCart.add(product);
        shoppingCartService.setProductCount(httpSession, product.getId(), 2l);
        assertEquals((Long)2L, currentCart.getItems().get(0).getQuantity());
    }

}
