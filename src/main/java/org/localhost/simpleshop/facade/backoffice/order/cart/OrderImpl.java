package org.localhost.simpleshop.facade.backoffice.order.cart;

import lombok.Getter;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.OrderFinishedException;
import org.localhost.simpleshop.facade.backoffice.order.exceptions.ProductNotFoundException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
public class OrderImpl implements Order {
    private final String id;
    private final Set<CartItem> products = new HashSet<>();
    private double discount;
    private boolean completed = false;

    public OrderImpl() {
        this.id = UUID.randomUUID().toString();
    }


    public void addProduct(CartItem product) {
        Objects.requireNonNull(product, "Product cannot be null");
        if (completed) {
            throw new OrderFinishedException("Cannot add product to completed order");
        }
        products.stream()
                .filter(chosenProduct -> chosenProduct.equals(product))
                .findFirst()
                .ifPresentOrElse(
                        chosenProduct -> chosenProduct.setQuantity(chosenProduct.getQuantity() + 1),
                        () -> products.add(product)
                );
    }

    public void removeProductFromCart(String productId) {
        products.stream()
                .filter(productInCart -> productInCart.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        product -> {
                            if (product.getQuantity() > 1) {
                                product.setQuantity(product.getQuantity() - 1);
                            } else {
                                products.remove(product);
                            }

                        },
                        () -> {
                            throw new ProductNotFoundException("Product not found in cart");
                        }
                );
    }

    public void setAdditionalDiscount(double discount) {
        Objects.requireNonNull(discount);
        this.discount = discount;
    }


    public double calculateTotalOrderPrice() {
        double total = products.stream()
                .mapToDouble(CartItem::calculatePrice)
                .sum();
        return total - (total * discount);
    }


    public void completeOrder() {
        if (completed) {
            throw new OrderFinishedException("Order already completed");
        }
        completed = true;
    }

    public Set<CartItem> getProducts() {
        return Set.copyOf(products);
    }
}
