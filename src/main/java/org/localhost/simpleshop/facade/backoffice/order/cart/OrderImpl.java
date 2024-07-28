package org.localhost.simpleshop.facade.backoffice.order.cart;

import lombok.Getter;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.OrderFinishedException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
public class OrderImpl implements Order {
    private final String id;
    private final Set<CartItem> products;
    private double discount;
    private boolean completed;

    public OrderImpl(Set<CartItem> products) {
        this.id = UUID.randomUUID().toString();
        this.completed = false;
        this.products = new HashSet<>(Objects.requireNonNull(products, "Products cannot be null"));
    }


    void addProduct(CartItem product) {
        Objects.requireNonNull(product, "Product cannot be null");
        if (completed) {
            throw new OrderFinishedException("Cannot add product to completed order");
        }
        this.products.add(product);
    }


    @Override
    public double calculateTotalOrderPrice() {
        double total = products.stream()
                .mapToDouble(CartItem::getPrice)
                .sum();
        return total * (1 - discount / 100);
    }


    @Override
    public void completeOrder() {
        if (completed) return;
        completed = true;
    }

    public Set<CartItem> getProducts() {
        return Set.copyOf(products);
    }
}
