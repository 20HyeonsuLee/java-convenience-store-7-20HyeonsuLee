package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void 일반_상품을_생성한다() {
        assertSimpleTest(() -> {
            Product product = new Product(
                    "콜라",
                    1000,
                    null,
                    new Stock(10)
            );
            assertThat(product.getName()).isEqualTo("콜라");
            assertThat(product.getPrice()).isEqualTo(1000);
            assertThat(product.getPromotion()).isNull();
            assertThat(product.getRegularStock().count()).isEqualTo(10);
            assertThat(product.getPromotionStock().count()).isZero();
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
                    new Stock(10)
            );
            assertThat(product.getName()).isEqualTo("콜라");
            assertThat(product.getPrice()).isEqualTo(1000);
            assertThat(product.getPromotion()).isNotNull();
            assertThat(product.getPromotionStock().count()).isEqualTo(10);
            assertThat(product.getRegularStock().count()).isZero();
            assertThat(product.isPromotionPeriod()).isTrue();
        });
    }
}
