package org.localhost.simpleshop.facade.backoffice.order;

import org.localhost.simpleshop.facade.backoffice.order.cart.CartItem;
import org.localhost.simpleshop.facade.backoffice.order.cart.OrderImpl;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.OrderNotFoundException;
import org.localhost.simpleshop.facade.backoffice.order.product.ProductServiceImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class OrderManagerImpl implements OrderManager {
    private final ProductServiceImpl productService;
    private final OrderServiceImpl orderServiceImpl;
    private final List<CartItem> productsWithCategories = new ArrayList<>();
    private final List<CartItem> productsWithDiscount = new ArrayList<>();


    public OrderManagerImpl(ProductServiceImpl productService, OrderServiceImpl orderServiceImpl) {
        this.productService = productService;
        this.orderServiceImpl = orderServiceImpl;
        refreshProductLists();
    }


    @Override
    public void createNewOrder(OrderImpl order) {
        Objects.requireNonNull(order);
        orderServiceImpl.createOrder(order);
    }

    @Override
    public OrderImpl removeOrder(String orderId) {
        return orderServiceImpl.getOrdersInProgress().stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .map(order -> {
                    orderServiceImpl.removeOrderInProgress(order.getId());
                    return order;
                })
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public OrderImpl completeOrder(String orderId) {
        Objects.requireNonNull(orderId);
        return orderServiceImpl.getOrdersInProgress().stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .map(order -> {
                    orderServiceImpl.completeOrder(order.getId());
                    return order;
                })
                .orElseThrow(OrderNotFoundException::new);
    }

    private OrderImpl findOrderInProgress(String orderId) {
        return null;
    }

    public void addProductToOrder(CartItem product, String orderId) {
        Objects.requireNonNull(orderId);
        Objects.requireNonNull(product);
        OrderImpl order = orderServiceImpl.getOrder(orderId);
        order.addProduct(product);
    }

    public void removeProductFromOrder(String productId, String orderId) {
        Objects.requireNonNull(productId);
        Objects.requireNonNull(orderId);
        orderServiceImpl.removeProductFromOrder(productId, orderId);
    }

    @Override
    public void registerNewProduct(CartItem product) {
        Objects.requireNonNull(product);
        productService.registerProduct(product);
        refreshProductLists();
    }


    public void removeProduct(String productId) {
        Objects.requireNonNull(productId);

        productService.removeProduct(productId);
        refreshProductLists();
    }

    @Override
    public Set<CartItem> getProductsWithCategory() {
        return Set.copyOf(productsWithCategories);
    }

    @Override
    public Set<CartItem> getProductsWithDiscount() {
        return Set.copyOf(productsWithDiscount);
    }

    @Override
    public double getOrderPrice(String orderId) {
        if (orderId == null) {
            throw new OrderNotFoundException();
        }
        return orderServiceImpl.getOrderTotalPrice(orderId);
    }

    public Set<OrderImpl> getCompletedOrders() {
        return orderServiceImpl.getCompletedOrders();
    }

    public Set<OrderImpl> getOrdersInProgress() {
        return orderServiceImpl.getOrdersInProgress();
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
