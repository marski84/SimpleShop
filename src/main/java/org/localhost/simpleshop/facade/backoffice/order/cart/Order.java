package org.localhost.simpleshop.facade.backoffice.order.cart;

public interface Order {
    double calculateTotalOrderPrice();
    void completeOrder();
    void addProduct(CartItem product);
    void removeProductFromCart(String productId);
    void setAdditionalDiscount(double discount);
}
