package org.localhost.simpleshop.cart;

import lombok.Getter;
import lombok.Setter;
import org.localhost.simpleshop.product.ProductImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class OrderImpl implements Order {

    private String id;
    List<ProductImpl> products;
    private double discount;
    private boolean isCompleted;

    public OrderImpl(List<ProductImpl> products, double discount) {
        this.id = UUID.randomUUID().toString();
        this.isCompleted = false;
        this.products = products;
        this.discount = discount;
    }


    void addProduct(ProductImpl product) {
        Objects.requireNonNull(product);
        this.products.add(product);
    }


    @Override
    public double calculateTotalOrderPrice() {
        return 0;
    }

    @Override
    public void completeOrder() {
        if (isCompleted) return;
        isCompleted = true;
    }
}
