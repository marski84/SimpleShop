package org.localhost.simpleshop.backoffice;

import org.localhost.simpleshop.cart.Order;
import org.localhost.simpleshop.cart.OrderImpl;
import org.localhost.simpleshop.product.ProductImpl;

import java.util.List;

public interface OrderManager {
    Order completeOrder(String orderId);
    void createNewOrder(OrderImpl order);
    OrderImpl removeOrder(String orderId);
    void addProduct(ProductImpl product);
    boolean removeProduct(String productId);
    List<ProductImpl> getProductsWithCategory();
    List<ProductImpl> getProductsWithDiscount();
}
