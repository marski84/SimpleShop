package org.localhost.simpleshop.facade.backoffice.order;

import org.junit.jupiter.api.*;
import org.localhost.simpleshop.facade.backoffice.order.cart.CartItem;
import org.localhost.simpleshop.facade.backoffice.order.cart.OrderImpl;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.OrderAlreadyInProgressException;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.OrderNotFoundException;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.ProductNotFoundException;
import org.localhost.simpleshop.facade.backoffice.order.product.ProductImpl;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    private final static OrderServiceImpl objectUnderTest = new OrderServiceImpl();
    private final static CartItem firstTestProduct = new CartItem(new ProductImpl("test", "test")
    ).setPrice(555).setTax(0.22).setDiscount(0.5)
            .build();

    private final static CartItem secondTestProduct = new CartItem(new ProductImpl("test", "test")
    ).setPrice(123523).setTax(0.7).setDiscount(0.8)
            .build();

    private static final OrderImpl testOrder = new OrderImpl();
    private static final OrderImpl secondTestOrder = new OrderImpl();

    @Test
    @DisplayName("it should add new order")
    void shouldAddNewOrder() {
//        given, when
        OrderImpl testOrder = new OrderImpl();
        objectUnderTest.createOrder(testOrder);
//        then
        assertEquals(
                testOrder,
                objectUnderTest.getOrdersInProgress().stream()
                        .filter(newOrder -> newOrder.getId().equals(testOrder.getId()))
                        .findFirst().get()
        );
    }

    @Test
    @DisplayName("it should throw when attempt to add duplicate order")
    void shouldThrowWhenDuplicateOrderIsAdded() {
//        given, when
        OrderImpl testOrder = new OrderImpl();
        objectUnderTest.createOrder(testOrder);
//        then
        assertThrows(OrderAlreadyInProgressException.class,
                () -> objectUnderTest.createOrder(testOrder)
        );
    }

    @Test
    @DisplayName("it should return previously added order")
    void shouldReturnPreviouslyAddedOrder() {
//        given
        OrderImpl testOrder = new OrderImpl();
        objectUnderTest.createOrder(testOrder);
//        when
        OrderImpl foundOrder = objectUnderTest.getOrder(testOrder.getId());
//        then
        assertEquals(testOrder, foundOrder);
    }

    @Test
    @DisplayName("it should throw when trying to get non existing order")
    void shouldThrowWhenTryingToGetNonExistingOrder() {
//        given, when, then
        assertThrows(
                OrderNotFoundException.class,
                () -> objectUnderTest.getOrder("non-existing")
        );
    }


    @Test
    @DisplayName("it should return orders in progress")
    void getOrdersInProgressShouldReturnOrders() {
//        given
        int initialOrdersInProgressAmount = objectUnderTest.getOrdersInProgress().size();
        objectUnderTest.createOrder(testOrder);
        objectUnderTest.createOrder(secondTestOrder);
//        when
        Set<OrderImpl> ordersInProgress = objectUnderTest.getOrdersInProgress();
//        then
        assertEquals(initialOrdersInProgressAmount + 2, ordersInProgress.size());
    }

    @Test
    @DisplayName("it should remove added order")
    void removeOrderShouldRemoveAddedOrder() {
//        given
        OrderImpl newTestOrder = new OrderImpl();
        OrderImpl nextTestOrder = new OrderImpl();

        objectUnderTest.createOrder(newTestOrder);
        objectUnderTest.createOrder(nextTestOrder);
        int initialOrdersInProgressAmount = objectUnderTest.getOrdersInProgress().size();
//        when
        objectUnderTest.removeOrderInProgress(testOrder.getId());
//        then
        assertEquals(
                initialOrdersInProgressAmount - 1,
                objectUnderTest.getOrdersInProgress().size()
        );
    }

    @Test
    @DisplayName("it should throw when attempting to remove order from empty list")
    void removeOrderShouldThrowWhenOrderToRemoveWasNotFound() {
//        given, when, then
        assertThrows(
                OrderNotFoundException.class,
                () -> objectUnderTest.removeOrderInProgress("notvalidId")
        );
    }

    @Test
    @DisplayName("it should throw when order in present in the list but not valid id is given")
    void removeOrderShouldThrowWhenOrderHasNotValidId() {
//        given
        OrderImpl newTestOrder = new OrderImpl();
        objectUnderTest.createOrder(newTestOrder);
//        when, then
        assertThrows(
                OrderNotFoundException.class,
                () -> objectUnderTest.removeOrderInProgress("notvalidId")
        );
    }

    @Test
    @DisplayName("it should return empty list of completed orders")
    void getCompletedOrdersShouldReturnEmptySet() {
//        given
        int startingOrdersInProgressSize = objectUnderTest.getOrdersInProgress().size();
        int startingCompletedOrdersSize = objectUnderTest.getCompletedOrders().size();
//        when
        objectUnderTest.getOrdersInProgress().forEach(order -> objectUnderTest.completeOrder(order.getId()));
        int expectedOrdersInProgressAmount = startingOrdersInProgressSize + startingCompletedOrdersSize;
        Set<OrderImpl> completedOrders = objectUnderTest.getCompletedOrders();
//        then
        assertEquals(expectedOrdersInProgressAmount, completedOrders.size());
    }

    @Test
    @DisplayName("it should return list of completed orders")
    void getCompletedOrders() {
//        given. when
        OrderImpl completedOrder = new OrderImpl();
        objectUnderTest.createOrder(completedOrder);
        objectUnderTest.completeOrder(completedOrder.getId());
        Set<OrderImpl> completedOrders = objectUnderTest.getCompletedOrders();
//        then
        assertFalse(objectUnderTest.getOrdersInProgress().contains(completedOrder));
        assertTrue(completedOrders.contains(completedOrder));
    }


    @Test
    @DisplayName("it should remove product from order")
    void removeProductFromOrder() {
//        given
        testOrder.addProduct(firstTestProduct);
        testOrder.addProduct(secondTestProduct);
        objectUnderTest.createOrder(testOrder);
//        when
        objectUnderTest.removeProductFromOrder(
                firstTestProduct.getProduct().getId(),
                testOrder.getId()
        );
//        then
        assertFalse(testOrder.getProducts().contains(firstTestProduct));
    }


    @Test
    @DisplayName("it should throw when product was not found")
    void removeProductFromOrderShouldThrowWhenProductWasNotFound() {
//        given
        objectUnderTest.createOrder(testOrder);
//        when then
        assertThrows(
                ProductNotFoundException.class,
                () -> objectUnderTest.removeProductFromOrder(
                        firstTestProduct.getProduct().getId(),
                        testOrder.getId())
        );
    }

    @Test
    @DisplayName("it should return order total price")
    void getTotalPrice() {
//        given
        OrderImpl order = new OrderImpl();
        order.addProduct(firstTestProduct);
        order.addProduct(secondTestProduct);
        double expectedOrderPrice = order.calculateTotalOrderPrice();
        objectUnderTest.createOrder(order);
//        when
        double resultTotalPrice = objectUnderTest.getOrderTotalPrice(order.getId());
//        then
        assertEquals(expectedOrderPrice, resultTotalPrice);
    }


}