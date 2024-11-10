package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.Assertions;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.model.product.ProductInfo;

class ProductInfoTest {

    private ProductInfo productInfo;

    @BeforeEach
    void setup() {
        productInfo = new ProductInfo(1_500);
    }

    @Test
    void 상품정보의_금액을_조회한다() {
        assertSimpleTest(() -> {
            assertThat(productInfo.getPrice()).isEqualTo(1_500);
        });
    }

    @Test
    void 상품정보에_프로모션을_추가한다() {
        assertSimpleTest(() -> {
            Promotion promotion = new Promotion(
                    "탄산",
                    2,
                    1,
                    new Period(
                            LocalDate.now().minusDays(10),
                            LocalDate.now()
                    )
            );
            productInfo.setPromotion(promotion);
            productInfo.setPromotionQuantity(new Quantity(10));
            assertThat(productInfo.getPromotionQuantity().getCount()).isEqualTo(10);
            assertThat(productInfo.isPromotionPeriod()).isTrue();
        });
    }

    @Test
    void 프로모션중이_아니라면_프로모션_상품_개수는_0개이다() {
        assertSimpleTest(() -> {
            Promotion promotion = new Promotion(
                    "탄산",
                    2,
                    1,
                    new Period(
                            LocalDate.now().minusDays(10),
                            LocalDate.now().minusDays(1)
                    )
            );
            productInfo.setPromotion(promotion);
            productInfo.setPromotionQuantity(new Quantity(10));
            assertThat(productInfo.isPromotionPeriod()).isFalse();
            assertThat(productInfo.getPromotionQuantity().getCount()).isZero();
        });
    }
}
