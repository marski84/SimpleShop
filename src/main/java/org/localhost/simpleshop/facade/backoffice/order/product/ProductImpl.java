package org.localhost.simpleshop.facade.backoffice.order.product;
import java.util.UUID;

public final class ProductImpl implements Product {
    private final String id;
    private final String name;
    private final String category;

    public ProductImpl(String name, String category) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.category = category;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCategory() {
        return category;
    }
}
