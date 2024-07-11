package org.localhost.simpleshop.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface ProductService {
    void registerProduct(ProductImpl product);
    List<ProductImpl> getAvailableProducts();
}
