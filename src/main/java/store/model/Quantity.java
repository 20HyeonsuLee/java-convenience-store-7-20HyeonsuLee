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

    public void minus(Integer count) {
        validateCount(count);
        this.count = count;
    }

    private void validateCount(Integer count) {
        if (this.count - count < 0) {
            throw new IllegalArgumentException("구매할 수량만큼의 재고가 존재하지 않습니다.");
        }
    }
}
