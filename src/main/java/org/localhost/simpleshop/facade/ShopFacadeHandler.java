package org.localhost.simpleshop.facade;

import org.localhost.simpleshop.facade.backoffice.order.OrderManagerImpl;
import org.localhost.simpleshop.facade.backoffice.order.cart.CartItem;
import org.localhost.simpleshop.facade.backoffice.order.cart.OrderImpl;
import org.springframework.stereotype.Service;

@Service
public class ShopFacadeHandler {
    private final OrderManagerImpl orderManager;

    public ShopFacadeHandler(OrderManagerImpl orderManager) {
        this.orderManager = orderManager;
    }

    public void createOrder(OrderImpl order) {
        orderManager.createNewOrder(order);
    }

    public OrderImpl deleteOrder(OrderImpl order) {
        return orderManager.removeOrder(order.getId());
    }


    public OrderImpl completeOrder(String orderId) {
        return orderManager.completeOrder(orderId);
    }

    public void addProductToOrder(CartItem product, String orderId) {
        orderManager.addProductToOrder(product, orderId);
    }

    public void removeProductFromOrder(String productId, String orderId) {
        orderManager.removeProductFromOrder(productId, orderId);
    }

    public double getOrderPrice(String orderId) {
        return orderManager.getOrderPrice(orderId);
    }
}
