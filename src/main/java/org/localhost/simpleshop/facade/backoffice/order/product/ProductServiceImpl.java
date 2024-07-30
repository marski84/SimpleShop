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

    public void removeProduct(String productId) {
        Objects.requireNonNull(productId);
        CartItem product = getProductById(productId);
        if (!availableProducts.contains(product)) {
            throw new ProductNotFoundException(productId);
        }
        availableProducts.remove(product);
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
}
