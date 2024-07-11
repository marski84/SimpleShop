package org.localhost.simpleshop.cart;

public interface Order {
    double calculateTotalOrderPrice();
    void completeOrder();
}
