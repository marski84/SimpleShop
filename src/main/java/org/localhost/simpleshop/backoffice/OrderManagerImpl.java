package org.localhost.simpleshop.backoffice;

import org.localhost.simpleshop.cart.OrderImpl;
import org.localhost.simpleshop.product.ProductImpl;
import org.localhost.simpleshop.product.ProductServiceImpl;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderManagerImpl implements OrderManager {
    private final ProductServiceImpl productService;
    private final List<ProductImpl> productsWithCategories = new ArrayList<>();
    private final List<ProductImpl> productsWithDiscount = new ArrayList<>();
    private final List<OrderImpl> completedOrders = new ArrayList<>();
    private final List<OrderImpl> ordersInProgress = new ArrayList<>();

    public OrderManagerImpl(ProductServiceImpl productService) {
        this.productService = productService;
        refreshProductLists();
    }


    @Override
    public void createNewOrder(OrderImpl order) {
        Objects.requireNonNull(order);
        ordersInProgress.add(order);
    }

    @Override
    public OrderImpl removeOrder(String orderId) {
        return ordersInProgress.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .map(order -> {
                    ordersInProgress.remove(order);
                    return order;
                })
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public OrderImpl completeOrder(String orderId) {
        return ordersInProgress.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .map(order -> {
                    order.completeOrder();
                    completedOrders.add(order);
                    ordersInProgress.remove(order);
                    return order;
                })
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public void addProduct(ProductImpl product) {
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
    public List<ProductImpl> getProductsWithCategory() {
        return List.copyOf(productsWithCategories);
    }

    @Override
    public List<ProductImpl> getProductsWithDiscount() {
        return List.copyOf(productsWithDiscount);
    }

    List<OrderImpl> getCompletedOrders() {
        return List.copyOf(completedOrders);
    }

    List<OrderImpl> getOrdersInProgress() {
        return List.copyOf(ordersInProgress);
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
