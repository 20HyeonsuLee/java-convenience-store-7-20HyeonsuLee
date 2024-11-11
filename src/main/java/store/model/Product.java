package store.model;

public class Product {
    private final String name;
    private Integer price;
    private Stock regularQuantity;
    private Stock promotionQuantity;
    private Promotion promotion;

    public Product(String name, Integer price, Promotion promotion, Stock quantity) {
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

    private void setRegular(Promotion promotion, Stock quantity) {
        if (promotion == null) {
            this.regularQuantity = quantity;
            this.promotionQuantity = new Stock(0);
        }
    }

    private void setPromotion(Promotion promotion, Stock quantity) {
        if (promotion != null) {
            this.promotionQuantity = quantity;
            this.regularQuantity = new Stock(0);
        }
    }

    public String getName() {
        return name;
    }

    public Stock getRegularQuantity() {
        return regularQuantity;
    }

    public Stock getPromotionQuantity() {
        if (!isPromotionPeriod()) {
            return new Stock(0);
        }
        return promotionQuantity;
    }

    public Integer getTotalQuantity() {
        return regularQuantity.count() + promotionQuantity.count();
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getTotalQuantityForPromotion() {
        return promotion.getTotalQuantityForPromotion();
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setRegularQuantity(Stock regularQuantity) {
        this.regularQuantity = regularQuantity;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void setPromotionQuantity(Stock quantity) {
        this.promotionQuantity = quantity;
    }

    public boolean isPromotionPeriod() {
        if (promotion == null) {
            return false;
        }
        return promotion.isPeriod();
    }
}
