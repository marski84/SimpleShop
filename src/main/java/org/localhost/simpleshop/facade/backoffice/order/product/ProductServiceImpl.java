package org.localhost.simpleshop.facade.backoffice.order.product;

import org.localhost.simpleshop.facade.backoffice.order.cart.CartItem;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    private final Set<CartItem> availableProducts = new HashSet<>();

    @Override
    public void registerProduct(CartItem product) {
        Objects.requireNonNull(product);
        availableProducts.add(product);
    }

    public boolean removeProduct(String productId) {
        Objects.requireNonNull(productId);
        return availableProducts.stream()
                .filter(result -> result.getProduct().getId().equals(productId))
                        .findFirst()
                .map(availableProducts::remove)
                .orElseThrow(() ->  new ProductNotFoundException("Product not Found!"));
    }

    @Override
    public Set<CartItem> getAvailableProducts() {
        return Set.copyOf(availableProducts);
    }

    @Override
    public CartItem getProductById(String productId) {
        Objects.requireNonNull(productId);
        return availableProducts.stream()
                .filter(product -> product.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

//    public ProductServiceImpl() {
//        for (int i = 1; i <= 40; i++) {
//            availableProducts.add(
//                    new CartItem(new ProductImpl("Product " + i, "Category " + (i % 5)))
//                            .setPrice(Math.random() * 100.4)
//                            .setTax(Math.random() * 20.3)
//                            .setDiscount(Math.random() * 10)
//                            .build()
//            );)
//        }
//    }
}
