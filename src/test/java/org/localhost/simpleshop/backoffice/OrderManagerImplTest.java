package org.localhost.simpleshop.backoffice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.localhost.simpleshop.facade.backoffice.order.OrderManagerImpl;
import org.localhost.simpleshop.facade.backoffice.order.OrderService;
import org.localhost.simpleshop.facade.backoffice.order.cart.CartItem;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.OrderNotFoundException;
import org.localhost.simpleshop.facade.backoffice.order.cart.OrderImpl;
import org.localhost.simpleshop.facade.backoffice.order.product.ProductImpl;
import org.localhost.simpleshop.facade.backoffice.order.product.ProductServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderManagerImplTest {
    private static final ProductServiceImpl productService = new ProductServiceImpl();
    private static final OrderService orderService = new OrderService();
    private static final OrderManagerImpl objectUnderTest = new OrderManagerImpl(productService, orderService);

    @BeforeAll
    static void init() {
        for (int i = 1; i <= 40; i++) {
            objectUnderTest.registerNewProduct(
                    new CartItem(new ProductImpl("Product " + i, "Category " + (i % 5)))
                            .setPrice(Math.random() * 100.4)
                            .setTax(Math.random() * 20.3)
                            .setDiscount(Math.random() * 10)
                            .build()
            );
        }
    }


    @Test
    @DisplayName("it should add new Order to ordersInProgress List")
    void createNewOrder() {
//        given

        int initialListSize = objectUnderTest.getOrdersInProgress().size();
        OrderImpl testOrder = new OrderImpl();
        objectUnderTest.getProductsWithDiscount().forEach(
                testOrder::addProduct
        );
//        when
        objectUnderTest.createNewOrder(testOrder);
//        then
        assertEquals(initialListSize + 1, objectUnderTest.getOrdersInProgress().size());
    }

    @Test
    @DisplayName("it should throw when new Order is null")
    void doNotCreateNewOrderWhenNull() {
//        given
        int initialListSize = objectUnderTest.getOrdersInProgress().size();
//        OrderImpl testOrder = new OrderImpl(objectUnderTest.getProductsWithDiscount(), 10);
//        when, then
        assertThrows(NullPointerException.class, () -> objectUnderTest.createNewOrder(null));
        assertEquals(initialListSize, objectUnderTest.getOrdersInProgress().size());
    }

    @Test
    @DisplayName("It should throw when order does not exist")
    void removeOrderThrowsWhenOrderDoesNotExist() {
//        given
        String orderId = "xyz";
//        when, then
        assertThrows(OrderNotFoundException.class, () -> objectUnderTest.removeOrder(orderId));
    }

    @Test
    @DisplayName("It should remove order when order exists")
    void removeOrder() {
//        given
        int initialListSize = objectUnderTest.getOrdersInProgress().size();
        OrderImpl testOrder = new OrderImpl();
        objectUnderTest.getProductsWithDiscount().forEach(testOrder::addProduct);
//        when
        objectUnderTest.createNewOrder(testOrder);
        objectUnderTest.removeOrder(testOrder.getId());
//        then
        assertEquals(initialListSize, objectUnderTest.getOrdersInProgress().size());
    }

    @Test
    @DisplayName("It should complete order and move it to completed orders list")
    void testCompleteOrder() {
//        given
        OrderImpl testOrder = new OrderImpl();
        objectUnderTest.getProductsWithDiscount().forEach(testOrder::addProduct);

//        when
        objectUnderTest.createNewOrder(testOrder);
        int initialOrdersInProgressSize = objectUnderTest.getOrdersInProgress().size();
        int initialCompletedOrdersSize = objectUnderTest.getCompletedOrders().size();
//    then
        OrderImpl result = objectUnderTest.completeOrder(testOrder.getId());
        assertTrue(result.isCompleted());
        assertEquals(initialOrdersInProgressSize - 1, objectUnderTest.getOrdersInProgress().size());
        assertEquals(initialCompletedOrdersSize + 1, objectUnderTest.getCompletedOrders().size());
    }

    @Test
    @DisplayName("complete order should throw when order was mot added")
    void completeOrderShouldThrowWhenOrderDoesNotExist() {
//        given
        OrderImpl testOrder = new OrderImpl();
        objectUnderTest.getProductsWithDiscount().forEach(testOrder::addProduct);

//        when, then
        assertThrows(OrderNotFoundException.class, () -> objectUnderTest.completeOrder(testOrder.getId()));
    }

    @Test
    @DisplayName("it should add product to products list")
    void testAddProduct() {
//        given
        CartItem product = new CartItem(new ProductImpl("test", "test")
        ).setPrice(111).setTax(22).setDiscount(231)
                .build();
        long initialProductListSize = objectUnderTest.getProductsWithCategory().size();
//       when
        objectUnderTest.registerNewProduct(product);
//        then
        assertEquals(initialProductListSize + 1, objectUnderTest.getProductsWithDiscount().size());
    }

    @Test
    @DisplayName("it should remove product form available products list")
    void testRemoveProduct() {
//        given
        CartItem productToDelete = productService.getAvailableProducts()
                .stream()
                .peek(System.out::println)
                .toList()
                .get(1);
        long initialProductListSize = objectUnderTest.getProductsWithCategory().size();
//        when
        boolean testResult = objectUnderTest.removeProduct(productToDelete.getProduct().getId());
//        then
        assertTrue(testResult);
        assertEquals(
                initialProductListSize - 1,
                objectUnderTest.getProductsWithCategory().size()
        );
        assertEquals(
                initialProductListSize - 1,
                productService.getAvailableProducts().size()
        );
    }

    @Test
    @DisplayName("it should throw when new product is null")
    void doNotCreateProductWhenNull() {
//        given
        int initialListSize = objectUnderTest.getProductsWithCategory().size();
//        when, then
        assertThrows(NullPointerException.class, () -> objectUnderTest.createNewOrder(null));
        assertEquals(initialListSize, objectUnderTest.getProductsWithCategory().size());
    }

    @Test
    @DisplayName("it should not remove product from available products list if product was not found")
    void testDoNotRemoveProductIfNoProductFound() {
//        given
        CartItem product = new CartItem(new ProductImpl("test", "test")
        ).setPrice(111).setTax(22).setDiscount(231)
                .build();
        long initialProductListSize = objectUnderTest.getProductsWithCategory().size();

        objectUnderTest.registerNewProduct(product);
//        when
        boolean testResult = objectUnderTest.removeProduct(product.getProduct().getId());
//        then
        assertFalse(testResult);
        assertEquals(
                initialProductListSize,
                objectUnderTest.getProductsWithCategory().size()
        );
        assertEquals(
                initialProductListSize,
                productService.getAvailableProducts().size()
        );
    }

    @Test
    void testGetProductsWithByCategory() {
    }

    @Test
    @DisplayName("should getProductsWithDiscount method return products with discount")
    void testGetProductsWithDiscount() {
//      given, when
        boolean allProductsHaveDiscount = objectUnderTest.getProductsWithDiscount().stream()
                .allMatch(product -> product.getDiscount() > 0);
        boolean noProductWithoutDiscount = objectUnderTest.getProductsWithDiscount().stream()
                .noneMatch(product -> product.getDiscount() == 0);
//        then
        assertTrue(allProductsHaveDiscount);
        assertTrue(noProductWithoutDiscount);
    }


    @Test
    void getProductsWithByCategory() {
    }

    @Test
    void getProductsWithDiscount() {
    }
}