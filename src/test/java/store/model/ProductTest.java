package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.Test;
import store.model.product.Product;

class ProductTest {

    @Test
    void 일반_상품을_생성한다() {
        assertSimpleTest(() -> {
            Product product = new Product(
                    "콜라",
                    1000,
                    null,
                    new Quantity(10)
            );
            assertThat(product.getName()).isEqualTo("콜라");
            assertThat(product.getPrice()).isEqualTo(1000);
            assertThat(product.getPromotion()).isNull();
            assertThat(product.getRegularQuantity().getCount()).isEqualTo(10);
            assertThat(product.getPromotionQuantity().getCount()).isZero();
            assertThat(product.isPromotionPeriod()).isFalse();
        });
    }

    @Test
    void 프로모션_상품을_생성한다() {
        assertSimpleTest(() -> {
            Period period = new Period(
                    DateTimes.now().minusDays(10).toLocalDate(),
                    DateTimes.now().toLocalDate()
            );
            Promotion promotion = new Promotion(
                    "탄산",
                    2,
                    1,
                    period
            );
            Product product = new Product(
                    "콜라",
                    1000,
                    promotion,
                    new Quantity(10)
            );
            assertThat(product.getName()).isEqualTo("콜라");
            assertThat(product.getPrice()).isEqualTo(1000);
            assertThat(product.getPromotion()).isNotNull();
            assertThat(product.getPromotionQuantity().getCount()).isEqualTo(10);
            assertThat(product.getRegularQuantity().getCount()).isZero();
            assertThat(product.isPromotionPeriod()).isTrue();
        });
    }
}
