package org.localhost.simpleshop.backoffice;

import org.localhost.simpleshop.cart.Order;
import org.localhost.simpleshop.product.Product;
import org.localhost.simpleshop.product.ProductImpl;

import java.util.List;

public interface OrderManager {
    Order completeOrder(String orderId);
    void addProduct(ProductImpl product);
    void removeProduct(String productId);
    List<Product> getProductsWithByCategory(String category);
    List<Product> getProductsWithDiscount();
    Order createOrder(Order order);
}
