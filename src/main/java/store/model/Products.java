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
        if (product.getPromotionQuantity().getCount() <= order.getQuantity().getCount()) {
            return product.getPromotionQuantity().getCount() / product.getTotalQuantityForPromotion();
        }
        return order.getQuantity().getCount() / product.getTotalQuantityForPromotion();
    }

    public Integer getPromotableQuantity(Order order) {
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
            existingProduct.setRegularQuantity(newProduct.getRegularQuantity());
        }
    }

    private void updatePromotion(Product existingProduct, Product newProduct) {
        if (newProduct.isPromotionProduct()) {
            existingProduct.setPromotionQuantity(newProduct.getPromotionQuantity());
            existingProduct.setPromotion(newProduct.getPromotion());
        }
    }
}
