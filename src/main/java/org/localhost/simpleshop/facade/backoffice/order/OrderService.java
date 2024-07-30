package org.localhost.simpleshop.facade.backoffice.order;

import org.localhost.simpleshop.facade.backoffice.order.cart.OrderImpl;

import java.util.Set;

public interface OrderService {
    Set<OrderImpl> getOrdersInProgress();
    OrderImpl getOrder(String orderId);
    Set<OrderImpl> getCompletedOrders();
    void createOrder(OrderImpl order);
    void removeOrderInProgress(String orderId);
    OrderImpl completeOrder(String orderId);
    void removeProductFromOrder(String productId, String orderId);
    double getOrderTotalPrice(String orderId);
}
