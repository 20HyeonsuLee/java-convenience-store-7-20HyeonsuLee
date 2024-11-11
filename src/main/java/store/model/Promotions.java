package store.model;

import java.util.Map;
import java.util.TreeMap;

public class Promotions {
    private final Map<String, Promotion> promotions = new TreeMap<>();

    public void putPromotion(Promotion promotion) {
        promotions.put(promotion.getName(), promotion);
    }

    public Promotion getPromotion(String name) {
        if (name == null) {
            return null;
        }
        return promotions.get(name);
    }
}
