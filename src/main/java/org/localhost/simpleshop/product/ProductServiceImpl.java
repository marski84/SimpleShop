package org.localhost.simpleshop.product;

import java.util.*;

public class ProductServiceImpl implements ProductService {
    private final List<ProductImpl> availableProducts = new ArrayList<>();

    @Override
    public void registerProduct(ProductImpl product) {
        Objects.requireNonNull(product);
        availableProducts.add(product);
    }

    @Override
    public List<ProductImpl> getAvailableProducts() {
        return List.copyOf(availableProducts);
    }

    public ProductServiceImpl() {
        for (int i = 1; i <= 40; i++) {
            availableProducts.add(new ProductImpl(
                    "Product" + i,
                    "Category" + (i % 5), // Categories will be Category0 to Category4
                    Math.random() * 100.4,  // Price between 0 and 100
                    Math.random() * 20.3,   // Tax between 0 and 20
                    Math.random() * 10    // Discount between 0 and 10
            ));
        }
    }
}
