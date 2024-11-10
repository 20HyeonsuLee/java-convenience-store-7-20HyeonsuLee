package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.model.product.ProductName;
import store.model.product.Products;

class ProductsTest {

    private Products products;

    @BeforeEach
    void setup() {
        products = new Products();
    }

    @Test
    void 같은_일반_상품을_추가한다() {
        assertSimpleTest(() -> {
            ProductName 콜라 = new ProductName("콜라");
            products.putNormal(콜라, 1_500, new Quantity(10));
            products.putNormal(콜라, 1_500, new Quantity(10));
            assertThat(products.getProductInfo(콜라).getRegularQuantity().getCount()).isEqualTo(20);
        });
    }

    @Test
    void 프로모션_상품과_일반_상품을_추가한다() {
        assertSimpleTest(() -> {
            ProductName 콜라 = new ProductName("콜라");
            Period period = new Period(
                    LocalDate.now().minusDays(10),
                    LocalDate.now()
            );
            Promotion promotion = new Promotion(
                    "반짝할인",
                    1,
                    1,
                    period
            );
            products.putNormal(콜라, 1_500, new Quantity(10));
            products.putPromotion(콜라, 1_500, new Quantity(5), promotion);
            assertThat(products.getProductInfo(콜라).getRegularQuantity().getCount()).isEqualTo(10);
            assertThat(products.getProductInfo(콜라).getPromotionQuantity().getCount()).isEqualTo(5);
        });
    }

    @Test
    void 프로모션_상품은_한가지만_존재할_수_있다() {
        assertSimpleTest(() -> {
            ProductName 콜라 = new ProductName("콜라");
            Period period = new Period(
                    LocalDate.now().minusDays(10),
                    LocalDate.now()
            );
            Promotion promotion = new Promotion(
                    "반짝할인",
                    1,
                    1,
                    period
            );
            products.putPromotion(콜라, 1_500, new Quantity(5), promotion);
            products.putPromotion(콜라, 1_500, new Quantity(15), promotion);
            assertThat(products.getProductInfo(콜라).getPromotionQuantity().getCount()).isEqualTo(15);
        });
    }

    @Test
    void 일반_상품만_있다면_프로모션_상품의_개수는_0이다() {
        assertSimpleTest(() -> {
            ProductName 콜라 = new ProductName("콜라");
            products.putNormal(콜라, 1_500, new Quantity(10));
            assertThat(products.getProductInfo(콜라).getPromotionQuantity().getCount()).isZero();
        });
    }

    @Test
    void 프로모션_상품만_있다면_일반_상품의_개수는_0이다() {
        assertSimpleTest(() -> {
            ProductName 콜라 = new ProductName("콜라");
            Period period = new Period(
                    LocalDate.now().minusDays(10),
                    LocalDate.now()
            );
            Promotion promotion = new Promotion(
                    "반짝할인",
                    1,
                    1,
                    period
            );
            products.putPromotion(콜라, 1_500, new Quantity(10), promotion);
            assertThat(products.getProductInfo(콜라).getRegularQuantity().getCount()).isZero();
        });
    }

    @Test
    void 프로모션_상품을_포함한_특정_상품의_전체_개수를_조회힌다() {
        assertSimpleTest(() -> {
            ProductName 콜라 = new ProductName("콜라");
            Period period = new Period(
                    LocalDate.now().minusDays(10),
                    LocalDate.now()
            );
            Promotion promotion = new Promotion(
                    "반짝할인",
                    1,
                    1,
                    period
            );
            products.putNormal(콜라, 1_500, new Quantity(10));
            products.putPromotion(콜라, 1_500, new Quantity(5), promotion);
            assertThat(products.getTotalQuantity(콜라).getCount()).isEqualTo(15);
        });
    }

    @Test
    void 더_담았을_때_프로모션_혜택을_받을_수_있다면_추가로_담아야_하는_개수를_반환한다() {
        assertSimpleTest(() -> {
            ProductName 콜라 = new ProductName("콜라");
            Period period = new Period(
                    LocalDate.now().minusDays(10),
                    LocalDate.now()
            );
            Promotion promotion = new Promotion(
                    "반짝할인",
                    2,
                    1,
                    period
            );
            products.putPromotion(콜라, 1_500, new Quantity(10), promotion);
            assertThat(products.getRequiredPromotionCount(콜라, new Quantity(8))).isEqualTo(1);
        });
    }

    @Test
    void 프로모션에_맞게_딱_맞게_담았다면_추가로_담아야_하는_개수를_0개로_반환한다() {
        assertSimpleTest(() -> {
            ProductName 콜라 = new ProductName("콜라");
            Period period = new Period(
                    LocalDate.now().minusDays(10),
                    LocalDate.now()
            );
            Promotion promotion = new Promotion(
                    "반짝할인",
                    2,
                    1,
                    period
            );
            products.putPromotion(콜라, 1_500, new Quantity(10), promotion);
            assertThat(products.getRequiredPromotionCount(콜라, new Quantity(9))).isZero();
        });
    }

    @Test
    void 더이상_프로모션을_받을_수_없다면_0을_반환한다() {
        assertSimpleTest(() -> {
            ProductName 콜라 = new ProductName("콜라");
            Period period = new Period(
                    LocalDate.now().minusDays(10),
                    LocalDate.now()
            );
            Promotion promotion = new Promotion(
                    "반짝할인",
                    2,
                    1,
                    period
            );
            products.putPromotion(콜라, 1_500, new Quantity(11), promotion);
            assertThat(products.getRequiredPromotionCount(콜라, new Quantity(10))).isZero();
        });
    }

    @Test
    void 프로모션을_받을_수_있는_상품의_개수를_반환한다() {
        assertSimpleTest(() -> {
            ProductName 콜라 = new ProductName("콜라");
            Period period = new Period(
                    LocalDate.now().minusDays(10),
                    LocalDate.now()
            );
            Promotion promotion = new Promotion(
                    "반짝할인",
                    2,
                    1,
                    period
            );
            products.putPromotion(콜라, 1_500, new Quantity(11), promotion);
            assertThat(products.getPromotableBuyQuantity(콜라, new Quantity(10)).getCount()).isEqualTo(9);
        });
    }

    @Test
    void 프로모션_상품의_개수보다_많이_입력한_경우_프로모션_받을_수_있는_상품의_개수를_반환한다() {
        assertSimpleTest(() -> {
            ProductName 콜라 = new ProductName("콜라");
            Period period = new Period(
                    LocalDate.now().minusDays(10),
                    LocalDate.now()
            );
            Promotion promotion = new Promotion(
                    "반짝할인",
                    2,
                    1,
                    period
            );
            products.putPromotion(콜라, 1_500, new Quantity(12), promotion);
            assertThat(products.getPromotableBuyQuantity(콜라, new Quantity(15)).getCount()).isEqualTo(12);
        });
    }
}
