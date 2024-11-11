package store.model;

public class ReceiptDetail implements Comparable<ReceiptDetail> {

    private final String name;
    private final Integer quantity;
    private final Integer price;

    public ReceiptDetail(String name, Integer quantity, Integer price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public int compareTo(ReceiptDetail other) {
        return this.quantity.compareTo(other.getQuantity());
    }
}
