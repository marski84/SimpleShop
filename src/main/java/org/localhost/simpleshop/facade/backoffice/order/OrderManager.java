package org.localhost.simpleshop.facade.backoffice.order;

import org.localhost.simpleshop.facade.backoffice.order.cart.CartItem;
import org.localhost.simpleshop.facade.backoffice.order.cart.OrderImpl;
import org.localhost.simpleshop.facade.backoffice.order.cart.Order;
import java.util.Set;

public interface OrderManager {
    Order completeOrder(String orderId);
    void createNewOrder(OrderImpl order);
    OrderImpl removeOrder(String orderId);
    void registerNewProduct(CartItem product);
    boolean removeProduct(String productId);
    Set<CartItem> getProductsWithCategory();
    Set<CartItem> getProductsWithDiscount();
}
