package store.model.product;

public class ProductName implements Comparable<ProductName> {
    private final String name;

    public ProductName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(ProductName other) {
        return this.name.compareTo(other.name);
    }
}
