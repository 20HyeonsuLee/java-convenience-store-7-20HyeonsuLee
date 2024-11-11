package store.model;

public class Order {
    private final String name;
    private final Quantity quantity;

    public Order(String name, Quantity quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
