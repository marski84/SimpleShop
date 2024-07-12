package org.localhost.simpleshop.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {
    void registerProduct(ProductImpl product);
    List<ProductImpl> getAvailableProducts();
    Optional<ProductImpl> getProductById(String productId);
}
