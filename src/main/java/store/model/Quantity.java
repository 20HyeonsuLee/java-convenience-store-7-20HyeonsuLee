package store.model;

public class Quantity {

    private Integer count;

    public Quantity(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public Integer decreaseBy(Integer quantity) {
        if (hasEnoughQuantity(quantity)) {
            decreaseQuantity(quantity);
            return 0;
        }
        return getRemainingQuantity(quantity);
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

    public void increaseBy(Integer quantity) {
        this.count += quantity;
    }

    public Quantity minus(Quantity quantity) {
        return new Quantity(this.count - quantity.count);
    }

    public boolean isLessThan(Quantity quantity) {
        return this.count < quantity.getCount();
    }

    public boolean isGreaterThan(Quantity quantity) {
        return this.count > quantity.getCount();
    }

}
