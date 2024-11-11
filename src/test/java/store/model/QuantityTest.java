package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class QuantityTest {

    @Test
    void 재고를_생성한다() {
        assertSimpleTest(() -> {
            Stock quantity = new Stock(5);
            assertThat(quantity.count()).isEqualTo(5);
        });
    }

    @Test
    void 재고보다_적은_수량을_감소시킨다() {
        assertSimpleTest(() -> {
            Stock quantity = new Stock(5);
            quantity.decreaseBy(3);
            assertThat(quantity.count()).isEqualTo(2);
        });
    }

    @Test
    void 재고보다_많은_수량을_감소시킨다() {
        assertSimpleTest(() -> {
            Stock quantity = new Stock(5);
            quantity.decreaseBy(10);
            assertThat(quantity.count()).isZero();
        });
    }
}
