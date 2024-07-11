package org.localhost.simpleshop.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    ProductServiceImpl objectUnderTest = new ProductServiceImpl();
    private final int INIT_PRODUCT_SET_SIZE = 40;



    @Test
    @DisplayName("Should register new product")
    void registerProduct() {
//        when
        int productSetSize = INIT_PRODUCT_SET_SIZE + 1;
        ProductImpl product = new ProductImpl("test", "test", 111.0, 12.0, 1.0);
        objectUnderTest.registerProduct(product);
//        then
        assertEquals(productSetSize, objectUnderTest.getAvailableProducts().size());

    }

    @Test
    @DisplayName("Should return a list of products")
    void getAvailableProducts() {
//        when
        List<ProductImpl> result = objectUnderTest.getAvailableProducts();
//        then
        assertEquals(result.size(), INIT_PRODUCT_SET_SIZE);
    }
}