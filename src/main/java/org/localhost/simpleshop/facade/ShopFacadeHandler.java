package org.localhost.simpleshop.facade;

import org.localhost.simpleshop.facade.backoffice.order.OrderManagerImpl;
import org.localhost.simpleshop.facade.backoffice.order.cart.CartItem;
import org.localhost.simpleshop.facade.backoffice.order.cart.OrderImpl;
import org.localhost.simpleshop.facade.backoffice.order.product.Product;
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

    public void addProductToOrder(CartItem product, String orderId) {
        orderManager.addProductToOrder(product, orderId);
    }

    public double getOrderPrice() {
        return 0;
    }

    public void addProductToCart(Product product, int quantity) {}

    public OrderImpl updateOrder(OrderImpl order) {
        return order;
    }

    public OrderImpl deleteOrder(OrderImpl order) {
        return order;
    }



    }
