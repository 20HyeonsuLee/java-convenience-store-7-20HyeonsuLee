package store.model;

public class Product {
    private final String name;
    private Integer price;
    private Stock regularStock;
    private Stock promotionStock;
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

    private void setRegular(Promotion promotion, Stock stock) {
        if (promotion == null) {
            this.regularStock = stock;
            this.promotionStock = new Stock(0);
        }
    }

    private void setPromotion(Promotion promotion, Stock stock) {
        if (promotion != null) {
            this.promotionStock = stock;
            this.regularStock = new Stock(0);
        }
    }

    public String getName() {
        return name;
    }

    public Stock getRegularStock() {
        return regularStock;
    }

    public Stock getPromotionStock() {
        if (!isPromotionPeriod()) {
            return new Stock(0);
        }
        return promotionStock;
    }

    public Integer getTotalQuantity() {
        return regularStock.count() + promotionStock.count();
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

    public void setRegularStock(Stock regularStock) {
        this.regularStock = regularStock;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void setPromotionStock(Stock quantity) {
        this.promotionStock = quantity;
    }

    public boolean isPromotionPeriod() {
        if (promotion == null) {
            return false;
        }
        return promotion.isPeriod();
    }
}
