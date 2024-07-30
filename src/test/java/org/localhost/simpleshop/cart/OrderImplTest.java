package org.localhost.simpleshop.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.localhost.simpleshop.facade.backoffice.order.cart.CartItem;
import org.localhost.simpleshop.facade.backoffice.order.cart.OrderImpl;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.OrderFinishedException;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.ProductNotFoundException;
import org.localhost.simpleshop.facade.backoffice.order.product.ProductImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderImplTest {

    private final OrderImpl objectUnderTest = new OrderImpl();
    private final CartItem testCartItem = new CartItem(
            new ProductImpl("testProduct", "testCategory")
    ).setPrice(111).setQuantity(1).setDiscount(0.1).build();

    @Test
    @DisplayName("it should add product to order")
    void addProduct() {
//        given
        int startingProductAmount = objectUnderTest.getProducts().size();
//        when
        objectUnderTest.addProduct(testCartItem);
//        then
        assertEquals(startingProductAmount + 1, objectUnderTest.getProducts().size());
    }

    @Test
    @DisplayName("it should increase cart item quantity")
    void increaseCartItemQuantity() {
//        given
        int startingProductAmount = objectUnderTest.getProducts().size();
//        when
        objectUnderTest.addProduct(testCartItem);
        objectUnderTest.addProduct(testCartItem);
//        then
        assertEquals(startingProductAmount + 1, objectUnderTest.getProducts().size());
        int expectedProductQuantity = objectUnderTest.getProducts().stream().
                filter(product ->
                        product.getProduct().getId().equals(testCartItem.getProduct().getId())
                )
                .findFirst().get().getQuantity();

        assertEquals(expectedProductQuantity, 2);
    }
    @Test
    @DisplayName("it should decrease cart item quantity")
    void decreaseCartItemQuantity() {
//        given
        int startingProductAmount = objectUnderTest.getProducts().size();
        objectUnderTest.addProduct(testCartItem);
        objectUnderTest.addProduct(testCartItem);

        int startingProductQuantity = objectUnderTest.getProducts().stream().
                filter(product ->
                        product.getProduct().getId().equals(testCartItem.getProduct().getId())
                )
                .findFirst().get().getQuantity();
//        when
        objectUnderTest.removeProductFromCart(testCartItem.getProduct().getId());
        int expectedProductQuantity = objectUnderTest.getProducts().stream().
                filter(product ->
                        product.getProduct().getId().equals(testCartItem.getProduct().getId())
                )
                .findFirst().get().getQuantity();
//        then
        assertEquals(startingProductAmount + 1, objectUnderTest.getProducts().size());
        assertEquals(startingProductQuantity  -1, expectedProductQuantity);
    }

    @Test
    @DisplayName("it should throw when user attempts to remove product which is not in the cart")
    void removeProductShouldThrowWhenProductIsNotInCart() {
//        given, when, then
        assertThrows(
                ProductNotFoundException.class,
                ()-> objectUnderTest.removeProductFromCart("invalidTestId")
        );

    }



    @Test
    @DisplayName("it should throw when product added to completed order")
    void addProductShouldThrowWhenOrderCompleted() {
//        given
        objectUnderTest.completeOrder();
//        when, then
        assertTrue(objectUnderTest.isCompleted());
        assertThrows(OrderFinishedException.class, () -> objectUnderTest.addProduct(testCartItem));
    }

    @Test
    @DisplayName("it should calculate price for the order when order without additional discount")
    void calculateTotalOrderPrice() {
//        given
        objectUnderTest.addProduct(testCartItem);
        objectUnderTest.addProduct(testCartItem);
        objectUnderTest.addProduct(testCartItem);
//        when
        double itemPrice = testCartItem.calculatePrice();
        double orderPrice = objectUnderTest.calculateTotalOrderPrice();
//        then
        assertEquals(itemPrice, orderPrice);
    }

    @Test
    @DisplayName("it should calculate price for the order when order has additional discount")
    void calculateTotalOrderPriceWithAdditionalDiscount() {
//        given
        final double ADDITIONAL_DISCOUNT = 0.1;
        objectUnderTest.addProduct(testCartItem);
        objectUnderTest.addProduct(testCartItem);
        objectUnderTest.addProduct(testCartItem);
        objectUnderTest.setAdditionalDiscount(ADDITIONAL_DISCOUNT);
//        when
        double itemPrice = testCartItem.calculatePrice() - (testCartItem.calculatePrice() * ADDITIONAL_DISCOUNT);
        double orderPrice = objectUnderTest.calculateTotalOrderPrice();
//        then
        assertEquals(itemPrice, orderPrice);
    }

    @Test
    @DisplayName("it should throw when user tries to complete already completed order")
    void throwWhenCompleteOrder() {
//        given
        objectUnderTest.completeOrder();
//        when, then
        assertThrows(OrderFinishedException.class, objectUnderTest::completeOrder);
    }

    @Test
    @DisplayName("it should complete order")
    void completeOrder() {
//        given
        boolean startingOrderStatus = objectUnderTest.isCompleted();
//        when
        objectUnderTest.completeOrder();
//        then
        assertEquals(!startingOrderStatus, objectUnderTest.isCompleted());
    }
}