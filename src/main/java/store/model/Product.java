package store.model;

public class Product {

    private String name;
    private Integer price;
    private Quantity quantity;
    private Promotion promotion;

    public Product(String name, Integer price, Quantity quantity, Promotion promotion) {
        validatePrice(price);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    private void validatePrice(Integer price) {
        if (price < 0) {
            throw new IllegalArgumentException("금액은 0원 이상이여야 합니다.");
        }
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void buy(Integer count) {
        quantity.minus(count);
    }
}
