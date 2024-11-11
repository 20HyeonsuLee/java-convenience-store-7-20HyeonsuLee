package store.model;

public class Order {
    private final String name;
    private Integer quantity;

    public Order(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void increaseQuantity(Integer count) {
        this.quantity += count;
    }

    public void decreaseQuantity(Integer count) {
        this.quantity -= count;
    }
}
