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

    public Integer getPromotableCount(Order order) {
        Product productInfo = find(order.getName());
        Quantity quantity = order.getQuantity();
        if (!productInfo.isPromotionPeriod()) {
            return 0;
        }
        if (productInfo.getPromotionQuantity().getCount() <= quantity.getCount()) {
            return productInfo.getPromotionQuantity().getCount() / productInfo.getTotalQuantityForPromotion().getCount();
        }
        return quantity.getCount() / productInfo.getTotalQuantityForPromotion().getCount();
    }

    public Integer getPromotableQuantity(Order order) {
        Product productInfo = find(order.getName());
        if (!productInfo.isPromotionPeriod()) {
            return 0;
        }
        return getPromotableCount(order) * productInfo.getTotalQuantityForPromotion().getCount();
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
