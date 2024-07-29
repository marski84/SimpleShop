package org.localhost.simpleshop.facade.backoffice.order;

import org.localhost.simpleshop.facade.backoffice.order.cart.CartItem;
import org.localhost.simpleshop.facade.backoffice.order.cart.OrderImpl;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.OrderNotFoundException;
import org.localhost.simpleshop.facade.backoffice.order.product.ProductImpl;
import org.localhost.simpleshop.facade.backoffice.order.product.ProductServiceImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class OrderManagerImpl implements OrderManager {
    private final ProductServiceImpl productService;
    private final OrderService orderService;
    private final List<CartItem> productsWithCategories = new ArrayList<>();
    private final List<CartItem> productsWithDiscount = new ArrayList<>();


    public OrderManagerImpl(ProductServiceImpl productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
        refreshProductLists();
    }


    @Override
    public void createNewOrder(OrderImpl order) {
        Objects.requireNonNull(order);
        orderService.createOrder(order);
    }

    @Override
    public OrderImpl removeOrder(String orderId) {
        return orderService.getOrdersInProgress().stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .map(order -> {
                    orderService.removeOrderInProgress(order);
                    return order;
                })
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public OrderImpl completeOrder(String orderId) {
        Objects.requireNonNull(orderId);
        return orderService.getOrdersInProgress().stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .map(order -> {
                    order.completeOrder();
                    orderService.completeOrder(order);
                    return order;
                })
                .orElseThrow(OrderNotFoundException::new);
    }

    public void addProductToOrder(CartItem product, String orderId) {
        Objects.requireNonNull(orderId);
        Objects.requireNonNull(product);
        OrderImpl order = orderService.getOrder(orderId);
        order.addProduct(product);
    }

    @Override
    public void registerNewProduct(CartItem product) {
        Objects.requireNonNull(product);
        productService.registerProduct(product);
        refreshProductLists();
    }

    @Override
    public boolean removeProduct(String productId) {
        Objects.requireNonNull(productId);
        boolean result = productService.removeProduct(productId);
        if (result) {
            refreshProductLists();
        }
        return result;
    }

    @Override
    public Set<CartItem> getProductsWithCategory() {
        return Set.copyOf(productsWithCategories);
    }

    @Override
    public Set<CartItem> getProductsWithDiscount() {
        return Set.copyOf(productsWithDiscount);
    }

    public Set<OrderImpl> getCompletedOrders() {
        return orderService.getCompletedOrders();
    }

    public Set<OrderImpl> getOrdersInProgress() {
        return orderService.getOrdersInProgress();
    }

    private void refreshProductLists() {
        productsWithCategories.clear();
        productsWithDiscount.clear();

        this.productsWithCategories.addAll(productService.getAvailableProducts());

        productsWithDiscount.addAll(productService.getAvailableProducts().stream()
                .filter(product -> product.getDiscount() > 0)
                .toList());
    }
}
