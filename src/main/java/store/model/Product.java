package store.model;

public class Product {
    private final String name;
    private Integer price;
    private Quantity regularQuantity;
    private Quantity promotionQuantity;
    private Promotion promotion;

    public Product(String name, Integer price, Promotion promotion, Quantity quantity) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        setRegular(promotion, quantity);
        setPromotion(promotion, quantity);
    }

    public boolean isPromotionProduct() {
        return promotion != null;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    private void setRegular(Promotion promotion, Quantity quantity) {
        if (promotion == null) {
            this.regularQuantity = quantity;
            this.promotionQuantity = new Quantity(0);
        }
    }

    private void setPromotion(Promotion promotion, Quantity quantity) {
        if (promotion != null) {
            this.promotionQuantity = quantity;
            this.regularQuantity = new Quantity(0);
        }
    }

    public String getName() {
        return name;
    }

    public Quantity getRegularQuantity() {
        return regularQuantity;
    }

    public Quantity getPromotionQuantity() {
        if (!isPromotionPeriod()) {
            return new Quantity(0);
        }
        return promotionQuantity;
    }

    public Quantity getTotalQuantity() {
        return new Quantity(regularQuantity.getCount() + getPromotionQuantity().getCount());
    }

    public Integer getPrice() {
        return price;
    }

    public Quantity getTotalQuantityForPromotion() {
        return new Quantity(promotion.getTotalQuantityForPromotion());
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setRegularQuantity(Quantity regularQuantity) {
        this.regularQuantity = regularQuantity;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void setPromotionQuantity(Quantity quantity) {
        this.promotionQuantity = quantity;
    }

    public boolean isPromotionPeriod() {
        if (promotion == null) {
            return false;
        }
        return promotion.isPeriod();
    }
}
