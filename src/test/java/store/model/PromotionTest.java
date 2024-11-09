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
            assertThat(promotion.getBuy()).isEqualTo(1);
            assertThat(promotion.getGet()).isEqualTo(1);
            assertThat(promotion.getName()).isEqualTo("반짝할인");
            assertThat(promotion.getPeriod().getStartDate()).isEqualTo(period.getStartDate());
            assertThat(promotion.getPeriod().getEndDate()).isEqualTo(period.getEndDate());
        });
    }
}
