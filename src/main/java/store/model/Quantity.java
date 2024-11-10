package store.model;

public class Quantity {

    private Integer count;

    public Quantity(Integer count) {
        validateQuantityCount(count);
        this.count = count;
    }

    private void validateQuantityCount(Integer count) {
        if (count < 0) {
            throw new IllegalStateException("재고 수량은 0개 이상이여야 합니다.");
        }
    }

    public Integer getCount() {
        return count;
    }

    public void decreaseBy(Integer quantity) {
        this.count -= quantity;
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
