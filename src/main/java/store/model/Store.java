package store.model;

import java.util.List;

public class Store {

    private final Products products = new Products();
    private final Promotions promotions = new Promotions();
    private final double MEMBERSHIP_DISCOUNT_PERCENT = 0.3;

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

    public void addPromotion(Promotion promotion) {
        promotions.putPromotion(promotion);
    }

    public Integer getRequiredFreeQuantity(Order order) {
        Product product = products.find(order.getName());
        Integer quantity = order.getQuantity();
        if (!product.isPromotionPeriod()) {
            return 0;
        }
        if (product.getPromotionQuantity().count() < quantity + product.getPromotion().getFreeQuantity()) {
            return 0;
        }
        if (quantity % product.getTotalQuantityForPromotion() == product.getPromotion().getRequiredQuantity()) {
            return product.getPromotion().getFreeQuantity();
        }
        return 0;
    }

    public Integer getRequiredRegularQuantity(Order order) {
        Product product = products.find(order.getName());
        if (!product.isPromotionPeriod()) {
            return 0;
        }
        return order.getQuantity() - products.getPromotableQuantity(order);
    }

    public Receipt buyOrders(List<Order> orders, boolean isMembership) {
        Receipt receipt = new Receipt();
        orders.forEach(order -> buy(order, receipt, isMembership));
        return receipt;
    }

    private void buy(Order order, Receipt receipt, boolean isMembership) {
        Product product = products.find(order.getName());
        Integer promotableQuantity = products.getPromotableQuantity(order);
        Integer promotableCount = products.getAppliedPromotionCount(order);
        product.getPromotionQuantity().decreaseBy(promotableQuantity);
        Integer reminderQuantity = order.getQuantity() - promotableQuantity;
        addMembershipDiscountPrice(receipt, product, isMembership, reminderQuantity);
        reminderQuantity = product.getPromotionQuantity().decreaseBy(reminderQuantity);
        product.getRegularQuantity().decreaseBy(reminderQuantity);
        addOrderProduct(receipt, order, product);
        addFreeProduct(receipt, promotableCount, order, product);
    }

    private void addMembershipDiscountPrice(Receipt receipt, Product product, boolean isMembership, Integer quantity) {
        if (isMembership) {
            receipt.addMembershipDiscountPrice((quantity * product.getPrice()) * MEMBERSHIP_DISCOUNT_PERCENT);
        }
    }

    private void addOrderProduct(Receipt receipt, Order order, Product product) {
        receipt.addOrderProduct(new ReceiptDetail(
                order.getName(),
                order.getQuantity(),
                order.getQuantity() * product.getPrice()
        ));
    }

    private void addFreeProduct(Receipt receipt, Integer promotionCount, Order order, Product product) {
        if (promotionCount > 0) {
            receipt.addFreeProduct(new ReceiptDetail(
                    order.getName(),
                    product.getPromotion().getFreeQuantity() * promotionCount,
                    product.getPrice() * promotionCount
            ));
        }
    }
}
