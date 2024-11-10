package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class PromotionTest {

    @Test
    void 프로모션을_생성한다() {
        assertSimpleTest(() -> {
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
            assertThat(promotion.getRequiredQuantity()).isEqualTo(1);
            assertThat(promotion.getFreeQuantity()).isEqualTo(1);
            assertThat(promotion.getName()).isEqualTo("반짝할인");
            assertThat(promotion.isPeriod()).isTrue();
        });
    }

    @Test
    void 오늘이_프로모션_기간이라면_isPeriod_는_true이다() {
        assertSimpleTest(() -> {
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
            assertThat(promotion.isPeriod()).isTrue();
        });
    }

    @Test
    void 오늘이_프로모션_기간이_아니라면_isPeriod_는_false이다() {
        assertSimpleTest(() -> {
            Period period = new Period(
                    LocalDate.now().minusDays(10),
                    LocalDate.now().minusDays(1)
            );
            Promotion promotion = new Promotion(
                    "반짝할인",
                    1,
                    1,
                    period
            );
            assertThat(promotion.isPeriod()).isFalse();
        });
    }
}
