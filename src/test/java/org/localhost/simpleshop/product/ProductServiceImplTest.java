package org.localhost.simpleshop.product;

import org.junit.jupiter.api.*;
import org.localhost.simpleshop.facade.backoffice.order.cart.CartItem;
import org.localhost.simpleshop.facade.backoffice.order.product.ProductImpl;
import org.localhost.simpleshop.facade.backoffice.order.product.ProductServiceImpl;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceImplTest {

    ProductServiceImpl objectUnderTest = new ProductServiceImpl();
    private final int INIT_PRODUCT_SET_SIZE = 40;

    @BeforeAll
    void init() {
        for (int i = 1; i <= INIT_PRODUCT_SET_SIZE; i++) {
            objectUnderTest.registerProduct(
                    new CartItem(new ProductImpl("Product " + i, "Category " + (i % 5)))
                            .setPrice(Math.random() * 100.4)
                            .setTax(Math.random() * 20.3)
                            .setDiscount(Math.random() * 10)
                            .build()
            );
        }
    }

    @Test
    @Order(1)
    @DisplayName("Should return a list of products")
    void getAvailableProducts() {
        Set<CartItem> result = objectUnderTest.getAvailableProducts();
        assertEquals(INIT_PRODUCT_SET_SIZE, result.size());
    }

    @Test
    @Order(2)
    @DisplayName("Should register new product")
    void registerProduct() {
        int productSetSize = INIT_PRODUCT_SET_SIZE + 1;
        CartItem product =
                new CartItem(new ProductImpl("test", "test"))
                        .setPrice(111.0)
                        .setTax(12.0)
                        .setDiscount(1.0)
                        .build();
        objectUnderTest.registerProduct(product);
        assertEquals(productSetSize, objectUnderTest.getAvailableProducts().size());
    }
}