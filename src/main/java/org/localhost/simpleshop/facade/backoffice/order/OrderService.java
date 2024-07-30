package org.localhost.simpleshop.facade.backoffice.order;

import org.localhost.simpleshop.facade.backoffice.order.cart.OrderImpl;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.OrderAlreadyInProgressException;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.OrderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderService {
    private final Set<OrderImpl> completedOrders = new HashSet<>();
    private final Set<OrderImpl> ordersInProgress = new HashSet<>();


    public Set<OrderImpl> getOrdersInProgress() {
        return Set.copyOf(ordersInProgress);
    }

    public OrderImpl getOrder(String id) {
        return ordersInProgress.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElseThrow(OrderNotFoundException::new);
    }

    public Set<OrderImpl> getCompletedOrders() {
        return Set.copyOf(completedOrders);
    }


    public void createOrder(OrderImpl order) {
        if (ordersInProgress.contains(order)) {
            throw new OrderAlreadyInProgressException("Order is already in progress");
        }
        ordersInProgress.add(order);
    }

    public void removeOrderInProgress(String orderId) {
        if (!ordersInProgress.removeIf(order -> order.getId().equals(orderId))) {
            throw new OrderNotFoundException();
        }
    }

    public OrderImpl completeOrder(String orderId) {
        OrderImpl order = getOrder(orderId);
        order.completeOrder();
        ordersInProgress.remove(order);
        completedOrders.add(order);
        return order;
    }

    public void removeProductFromOrder(String productId, String orderId) {
        OrderImpl order = getOrder(orderId);
        order.removeProductFromCart(productId);
    }

    public double getOrderTotalPrice(String orderId) {
        OrderImpl order = getOrder(orderId);
        return order.calculateTotalOrderPrice();

    }
}
