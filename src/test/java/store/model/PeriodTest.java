package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.Test;

class PeriodTest {

    @Test
    void 프로모션_기간은_시작일이_종료일보다_클경우_예외가_발생한다() {
        assertThatThrownBy(() -> new Period(
                DateTimes.now().plusDays(1).toLocalDate(),
                DateTimes.now().toLocalDate()
        )).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 현재_날짜가_프로모션_진행중인지_반환한다() {
        assertSimpleTest(() -> {
            Period period = new Period(
                    DateTimes.now().minusDays(1).toLocalDate(),
                    DateTimes.now().toLocalDate()
            );
            assertThat(period.isPeriod()).isTrue();
        });
    }
}
