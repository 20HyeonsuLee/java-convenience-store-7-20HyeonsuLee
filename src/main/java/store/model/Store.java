package store.model;

import java.util.List;
import store.model.product.Product;
import store.model.product.Products;

public class Store {

    private final Products products = new Products();
    private final Promotions promotions = new Promotions();

    public Products getProducts() {
        return products;
    }

    public Product findProduct(String name) {
        return products.find(name);
    }

    public void addProduct(Product product) {
        products.addProduct(product);
    }

    public Promotion getPromotion(String name) {
        return promotions.getPromotion(name);
    }

    public Integer getRequiredFreeQuantity(Order order) {
        String name = order.getName();
        Integer quantity = order.getQuantity().getCount();
        Product product = products.find(name);
        if (!product.isPromotionPeriod()) {
            return 0;
        }
        if (product.getPromotionQuantity().getCount() < quantity + product.getPromotion().getFreeQuantity()) {
            return 0;
        }
        if (quantity % product.getTotalQuantityForPromotion().getCount() == product.getPromotion().getRequiredQuantity()) {
            return product.getPromotion().getFreeQuantity();
        }
        return 0;
    }

    public Integer getRequiredRegularQuantity(Order order) {
        Product product = products.find(order.getName());
        if (!product.isPromotionPeriod()) {
            return 0;
        }
        return order.getQuantity().getCount() - products.getPromotableQuantity(order);
    }

}
