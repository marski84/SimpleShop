package org.localhost.simpleshop.product;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class ProductImpl implements Product {
    private String id;
    private String name;
    private String category;
    private double price;
    private double tax;
    private double discount;



    @Override
    public double calculateFinalPrice() {
        return 0;
    }

    public ProductImpl(String name, String category, double price, double tax, double discount) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.category = category;
        this.price = price;
        this.tax = tax;
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductImpl product)) return false;
        return Double.compare(price, product.price) == 0 && Double.compare(tax, product.tax) == 0 && Double.compare(discount, product.discount) == 0 && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, price, tax, discount);
    }
}
