package org.localhost.simpleshop.backoffice;
import org.localhost.simpleshop.cart.Order;
import org.localhost.simpleshop.product.Product;
import org.localhost.simpleshop.product.ProductImpl;
import org.localhost.simpleshop.product.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderManagerImpl implements OrderManager {
    private final ProductServiceImpl productService;
    List<ProductImpl> productsWithCategories = new ArrayList<>();
    List<ProductImpl> productsWithDiscount = new ArrayList<>();
    List<Order> completedOrders = new ArrayList<>();
    List<Order> ordersInProgress = new ArrayList<>();

    public OrderManagerImpl(ProductServiceImpl productService) {
        this.productService = productService;
        this.productsWithCategories.addAll(productService.getAvailableProducts());
        productsWithDiscount = productService.getAvailableProducts().stream()
                .filter(product -> product.getDiscount() > 0)
                .toList();
    }


    @Override
    public Order completeOrder(String orderId) {
        return null;
    }



    @Override
    public void addProduct(ProductImpl product) {
        Objects.requireNonNull(product);
        productService.registerProduct(product);
        productsWithCategories = productService.getAvailableProducts();
    }

    @Override
    public void removeProduct(String productId) {

    }

    @Override
    public List<Product> getProductsWithByCategory(String category) {
        return List.copyOf(productsWithCategories);
    }

    @Override
    public List<Product> getProductsWithDiscount() {
        return List.copyOf(productsWithDiscount);
    }

    @Override
    public Order createOrder(Order order) {
        Objects.requireNonNull(order);

        return null;
    }

//    public static List<ProductImpl> getAvailableProductsList() {
//        return List.of(this.productsWithCategories);
//    }

}
