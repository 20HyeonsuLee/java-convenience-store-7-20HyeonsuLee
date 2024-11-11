package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class QuantityTest {

    @Test
    void 재고는_음수일수_없다() {
        assertThatThrownBy(() -> new Quantity(-1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 재고를_생성한다() {
        assertSimpleTest(() -> {
            Quantity quantity = new Quantity(5);
            assertThat(quantity.getCount()).isEqualTo(5);
        });
    }

    @Test
    void 재고보다_적은_수량을_감소시킨다() {
        assertSimpleTest(() -> {
            Quantity quantity = new Quantity(5);
            quantity.decreaseBy(3);
            assertThat(quantity.getCount()).isEqualTo(2);
        });
    }

    @Test
    void 재고보다_많은_수량을_감소시킨다() {
        assertSimpleTest(() -> {
            Quantity quantity = new Quantity(5);
            quantity.decreaseBy(10);
            assertThat(quantity.getCount()).isEqualTo(0);
        });
    }
}
