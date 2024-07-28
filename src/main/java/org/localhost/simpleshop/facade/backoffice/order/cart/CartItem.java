package org.localhost.simpleshop.facade.backoffice.order.cart;

import lombok.Getter;
import org.localhost.simpleshop.facade.backoffice.order.product.Product;

@Getter
public class CartItem {
    private final Product product;
    private int quantity;
    private double price;
    private double tax;
    private double discount;

    public CartItem(Product product) {
        this.product = product;
    }

    public CartItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public CartItem setPrice(double price) {
        this.price = price;
        return this;
    }

    public CartItem setTax(double tax) {
        this.tax = tax;
        return this;
    }

    public CartItem setDiscount(double discount) {
        this.discount = discount;
        return this;
    }

    public CartItem build() {
        return this;
    }

    public double calculatePrice() {
        double netPrice =  price * quantity;
        double taxPrice = price + (tax * netPrice);
        double finalPrice = taxPrice - (tax * discount);
        return finalPrice;
    }
}
