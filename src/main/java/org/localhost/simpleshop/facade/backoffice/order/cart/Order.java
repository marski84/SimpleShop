package org.localhost.simpleshop.facade.backoffice.order.cart;

public interface Order {
    double calculateTotalOrderPrice();
    void completeOrder();
}
