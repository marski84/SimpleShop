package org.localhost.simpleshop.facade.backoffice.order.product;

import org.localhost.simpleshop.facade.backoffice.order.cart.CartItem;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ProductService {
    void registerProduct(CartItem product);
    Set<CartItem> getAvailableProducts();
    CartItem getProductById(String productId);
}
