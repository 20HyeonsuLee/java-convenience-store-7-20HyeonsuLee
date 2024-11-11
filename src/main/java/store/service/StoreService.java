package store.service;

import java.util.ArrayList;
import java.util.List;
import store.dto.InputProductDTO;
import store.dto.InputPromotionDTO;
import store.dto.OutputProductDTO;
import store.exception.QuantityOverflowException;
import store.model.Order;
import store.model.Period;
import store.model.Promotion;
import store.model.Stock;
import store.model.Receipt;
import store.model.Store;
import store.model.Product;

public class StoreService {

    private static final String EMPTY_PROMOTION = "";

    private final Store store = new Store();

    public void uploadData(List<InputProductDTO> products, List<InputPromotionDTO> promotions) {
        uploadPromotions(promotions);
        uploadProducts(products);
    }

    public void validateOrderQuantity(Order order) {
        Product product = store.findProduct(order.getName());
        if (product.getTotalQuantity() < order.getQuantity()) {
            throw new QuantityOverflowException();
        }
    }

    public Receipt buy(List<Order> orders, boolean isMembership) {
        return store.buyOrders(orders, isMembership);
    }

    public Integer getRequiredFreeQuantity(Order order) {
        return store.getRequiredFreeQuantity(order);
    }

    public Integer getRequiredRegularQuantity(Order order) {
        return store.getRequiredRegularQuantity(order);
    }

    public void reOrderPromotion(Order order, boolean isConfirmed) {
        if (isConfirmed) {
            order.increaseQuantity(getRequiredFreeQuantity(order));
        }
    }

    public boolean isPromotionOrder(Order order) {
        return store.findProduct(order.getName()).isPromotionPeriod();
    }

    public void reOrderOrder(Order order, boolean isConfirmed) {
        if (!isConfirmed) {
            order.decreaseQuantity(store.getRequiredRegularQuantity(order));
        }
    }

    public List<OutputProductDTO> getAllProducts() {
        List<OutputProductDTO> outputProducts = new ArrayList<>();
        store.getProducts().getProducts().forEach(product -> {
            if (product.getPromotion() != null) {
                outputProducts.add(createPromotionProductDTO(product));
            }
            outputProducts.add(createRegularProductDTO(product));
        });
        return outputProducts;
    }

    private void uploadPromotions(List<InputPromotionDTO> promotions) {
        promotions.forEach(promotion -> store.addPromotion(new Promotion(
                promotion.name(),
                promotion.buy(),
                promotion.get(),
                new Period(
                        promotion.startDate(),
                        promotion.endDate()
                )
        )));
    }

    private void uploadProducts(List<InputProductDTO> products) {
        products.forEach(product -> store.addProduct(new Product(
                product.name(),
                product.price(),
                store.getPromotion(product.promotion()),
                new Stock(product.quantity())
        )));
    }

    private OutputProductDTO createRegularProductDTO(Product product) {
        return new OutputProductDTO(
                product.getName(),
                product.getPrice(),
                product.getRegularQuantity().count(),
                EMPTY_PROMOTION
        );
    }

    private OutputProductDTO createPromotionProductDTO(Product product) {
        return new OutputProductDTO(
                product.getName(),
                product.getPrice(),
                product.getPromotionQuantity().count(),
                product.getPromotion().getName()
        );
    }
}
