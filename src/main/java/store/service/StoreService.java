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
import store.model.Quantity;
import store.model.Receipt;
import store.model.Store;
import store.model.product.Product;

public class StoreService {

    private final Store store = new Store();

    public void uploadData(List<InputProductDTO> products, List<InputPromotionDTO> promotions) {
        uploadPromotions(promotions);
        uploadProducts(products);
    }

    public void validateOrderQuantity(Order order) {
        Product product = store.findProduct(order.getName());
        if (product.getTotalQuantity().isLessThan(order.getQuantity())) {
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
            order.getQuantity().increaseBy(getRequiredFreeQuantity(order));
        }
    }

    public boolean isPromotionOrder(Order order) {
        return store.findProduct(order.getName()).isPromotionPeriod();
    }

    public void reOrderOrder(Order order, boolean isConfirmed) {
        if (isConfirmed) {
            order.getQuantity().increaseBy(getRequiredFreeQuantity(order));
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
                new Quantity(product.quantity())
        )));
    }

    private OutputProductDTO createRegularProductDTO(Product product) {
        return new OutputProductDTO(
                product.getName(),
                product.getPrice(),
                product.getRegularQuantity().getCount(),
                ""
        );
    }

    private OutputProductDTO createPromotionProductDTO(Product product) {
        return new OutputProductDTO(
                product.getName(),
                product.getPrice(),
                product.getPromotionQuantity().getCount(),
                product.getPromotion().getName()
        );
    }
}
