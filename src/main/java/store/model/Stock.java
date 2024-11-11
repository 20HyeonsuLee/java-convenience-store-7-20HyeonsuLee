package store.model;

public class Stock {

    private Integer count;

    public Stock(Integer count) {
        this.count = count;
    }

    public Integer count() {
        return count;
    }

    public Integer decreaseBy(Integer quantity) {
        if (hasEnoughQuantity(quantity)) {
            decreaseQuantity(quantity);
            return 0;
        }
        return getRemainingQuantity(quantity);
    }

    public void increaseBy(Integer quantity) {
        this.count += quantity;
    }

    private boolean hasEnoughQuantity(Integer quantity) {
        return this.count >= quantity;
    }

    private void decreaseQuantity(Integer quantity) {
        this.count -= quantity;
    }

    private Integer getRemainingQuantity(Integer quantity) {
        int remaining = quantity - this.count;
        decreaseQuantity(this.count);
        return remaining;
    }
}
