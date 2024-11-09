package store.model;

public class Promotion {

    private String name;
    private Integer buy;
    private Integer get;
    private Period period;

    public Promotion(String name, Integer buy, Integer get, Period period) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public Integer getBuy() {
        return buy;
    }

    public Integer getGet() {
        return get;
    }

    public Period getPeriod() {
        return period;
    }
}
