package store.model;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PeriodTest {

    @Test
    void 프로모션_기간은_시작일이_종료일보다_클경우_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> new Period(
                LocalDate.now().plusDays(1),
                LocalDate.now()
        )).isInstanceOf(IllegalStateException.class);
    }
}
