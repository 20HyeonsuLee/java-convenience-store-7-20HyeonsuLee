package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class QuantityTest {

    @Test
    void 재고를_생성한다() {
        assertSimpleTest(() -> {
            Stock quantity = new Stock(5);
            assertThat(quantity.count()).isEqualTo(5);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "5, 3, 2",   // 재고보다 적은 수량을 감소시키는 경우
            "5, 10, 0"   // 재고보다 많은 수량을 감소시키는 경우
    })
    void 재고를_감소시킨다(int initialStock, int decreaseBy, int expectedStock) {
        assertSimpleTest(() -> {
            Stock quantity = new Stock(initialStock);
            quantity.decreaseBy(decreaseBy);
            assertThat(quantity.count()).isEqualTo(expectedStock);
        });
    }
}
