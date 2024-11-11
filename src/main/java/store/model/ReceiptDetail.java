package store.model;

public class ReceiptDetail implements Comparable<ReceiptDetail> {
    String name;
    Quantity quantity;
    Integer price;
    public ReceiptDetail(String name, Quantity quantity, Integer price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public int compareTo(ReceiptDetail other) {
        return this.quantity.getCount().compareTo(other.getQuantity().getCount());
    }
}
