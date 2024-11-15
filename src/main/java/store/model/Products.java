package store.model;

import java.util.ArrayList;
import java.util.List;
import store.exception.ProductNotFountException;

public class Products {
    private final List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.stream()
                .filter(existingProduct -> existingProduct.getName().equals(product.getName()))
                .findFirst()
                .ifPresentOrElse(
                        existingProduct -> updateProduct(existingProduct, product),
                        () -> products.add(product)
                );
    }

    public Product find(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(ProductNotFountException::new);
    }

    public Integer getAppliedPromotionCount(Order order) {
        Product product = find(order.getName());
        if (!product.isPromotionPeriod()) {
            return 0;
        }
        int promotionStock = product.getPromotionStock().count();
        return Math.min(promotionStock, order.getQuantity()) / product.getTotalQuantityForPromotion();
    }

    public Integer getTotalProductQuantityWithPromotion(Order order) {
        Product product = find(order.getName());
        if (!product.isPromotionPeriod()) {
            return 0;
        }
        return getAppliedPromotionCount(order) * product.getTotalQuantityForPromotion();
    }

    private void updateProduct(Product existingProduct, Product newProduct) {
        updateRegular(existingProduct, newProduct);
        updatePromotion(existingProduct, newProduct);
        existingProduct.setPrice(newProduct.getPrice());
    }

    private void updateRegular(Product existingProduct, Product newProduct) {
        if (!newProduct.isPromotionProduct()) {
            existingProduct.setRegularStock(newProduct.getRegularStock());
        }
    }

    private void updatePromotion(Product existingProduct, Product newProduct) {
        if (newProduct.isPromotionProduct()) {
            existingProduct.setPromotionStock(newProduct.getPromotionStock());
            existingProduct.setPromotion(newProduct.getPromotion());
        }
    }
}
