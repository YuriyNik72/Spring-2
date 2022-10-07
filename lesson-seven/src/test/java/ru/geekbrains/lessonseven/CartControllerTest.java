package ru.geekbrains.lessonseven;



import org.hamcrest.core.StringContains;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.geekbrains.entites.Product;
import ru.geekbrains.services.ShoppingCartService;
import ru.geekbrains.utils.ShoppingCart;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    MockHttpSession mockHttpSession;

    @Mock
    private ShoppingCartService shoppingCartService;

    @Test
    public void cartPageTest() throws Exception{
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Смартфон");
        product.setPrice(Double.valueOf(50L));

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.add(product);

        when(mockHttpSession.getAttribute("cart")).thenReturn(shoppingCart);
        mockMvc.perform(get("/cart").session(mockHttpSession))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(StringContains.containsString("Смартфон")));

    }
}
