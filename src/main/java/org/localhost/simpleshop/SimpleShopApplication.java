package org.localhost.simpleshop;

import org.localhost.simpleshop.product.Product;
import org.localhost.simpleshop.product.ProductImpl;
import org.localhost.simpleshop.product.ProductServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SimpleShopApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(SimpleShopApplication.class, args);

        ProductImpl product = new ProductImpl("test", "test", 111.0, 12.0, 1.0);
        ProductImpl product1 = new ProductImpl("test", "test", 111.0, 12.0, 1.0);

        ProductServiceImpl productService = new ProductServiceImpl();
        productService.registerProduct(product);
        productService.registerProduct(product1);
        productService.getAvailableProducts().stream().forEach(e -> System.out.println(e.getName()));
    }

}
