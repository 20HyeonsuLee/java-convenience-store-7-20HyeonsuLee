package store.model.product;

import java.util.Map;
import java.util.TreeMap;
import store.model.Promotion;
import store.model.Quantity;

public class Products {
    private final Map<ProductName, ProductInfo> products = new TreeMap<>();

    public void putNormal(ProductName productName, Integer price, Quantity quantity) {
        ProductInfo productInfo = products.getOrDefault(productName, new ProductInfo(price));
        productInfo.increaseRegularQuantity(quantity);
        products.put(productName, productInfo);
    }

    public void putPromotion(ProductName productName, Integer price, Quantity quantity, Promotion promotion) {
        ProductInfo productInfo = products.getOrDefault(productName, new ProductInfo(price));
        productInfo.setPromotion(promotion);
        productInfo.setPromotionQuantity(quantity);
        products.put(productName, productInfo);
    }

    public Quantity getTotalQuantity(ProductName productName) {
        return products.get(productName).getTotalQuantity();
    }

    public Integer getRequiredPromotionCount(ProductName productName, Quantity quantity) {
        ProductInfo productInfo = getProductInfo(productName);
        if (!productInfo.isPromotionPeriod()) {
            return 0;
        }
        int notPromotedCount = quantity.getCount() % productInfo.getTotalQuantityForPromotion();
        int requiredQuantityForPromotion = (int) (Math.ceil((double) quantity.getCount() / productInfo.getTotalQuantityForPromotion()) * productInfo.getTotalQuantityForPromotion());
        if (
                notPromotedCount != 0 &&
                requiredQuantityForPromotion <= productInfo.getPromotionQuantity().getCount()
        ) {
            return productInfo.getTotalQuantityForPromotion() - notPromotedCount;
        }
       return 0;
    }

    public Quantity getPromotableBuyQuantity(ProductName productName, Quantity quantity) {
        ProductInfo productInfo = getProductInfo(productName);
        if (productInfo.getPromotionQuantity().getCount() <= quantity.getCount()) {
            return new Quantity(productInfo.getPromotionQuantity().getCount() / productInfo.getTotalQuantityForPromotion() * productInfo.getTotalQuantityForPromotion());
        }
        return new Quantity(quantity.getCount() / productInfo.getTotalQuantityForPromotion() * productInfo.getTotalQuantityForPromotion());
    }

    public ProductInfo getProductInfo(ProductName productName) {
        if (!products.containsKey(productName)) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
        return products.get(productName);
    }
}
