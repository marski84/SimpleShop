package org.localhost.simpleshop.facade.backoffice.order;

import org.localhost.simpleshop.facade.backoffice.order.cart.CartItem;
import org.localhost.simpleshop.facade.backoffice.order.cart.Order;
import org.localhost.simpleshop.facade.backoffice.order.cart.OrderImpl;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.OrderAlreadyInProgressException;
import org.localhost.simpleshop.facade.backoffice.order.product.ProductImpl;
import org.localhost.simpleshop.facade.backoffice.order.product.ProductServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {
//    private final ProductServiceImpl productService;
    private final Set<OrderImpl> completedOrders = new HashSet<>();
    private final Set<OrderImpl> ordersInProgress = new HashSet<>();


    public Set<OrderImpl> getOrdersInProgress() {
        return Set.copyOf(ordersInProgress);
    }

    public Set<OrderImpl> getCompletedOrders() {
        return Set.copyOf(ordersInProgress);
    }

//    public OrderService(ProductServiceImpl productService) {
//        this.productService = productService;
//    }

    public void createOrder(OrderImpl order) {
        if (ordersInProgress.contains(order)) {
            throw new OrderAlreadyInProgressException("Order is already in progress");
        }
        ordersInProgress.add(order);
    }

    public void removeOrderInProgress(OrderImpl order) {
        ordersInProgress.remove(order);
    }

    public void completeOrder(OrderImpl order) {
        ordersInProgress.remove(order);
        completedOrders.add(order);

    }
}
