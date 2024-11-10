package store.model;

public class Promotion {

    private String name;
    private Integer requiredQuantity;
    private Integer freeQuantity;
    private Period period;

    public Promotion(String name, Integer requiredQuantity, Integer freeQuantity, Period period) {
        this.name = name;
        this.requiredQuantity = requiredQuantity;
        this.freeQuantity = freeQuantity;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public Integer getRequiredQuantity() {
        return requiredQuantity;
    }

    public Integer getFreeQuantity() {
        return freeQuantity;
    }

    public boolean isPeriod() {
        return period.isPeriod();
    }

    public Integer getTotalQuantityForPromotion() {
        return requiredQuantity + freeQuantity;
    }
}
