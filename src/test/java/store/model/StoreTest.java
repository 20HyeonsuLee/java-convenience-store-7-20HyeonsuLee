package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import org.assertj.core.error.ShouldBePeriod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.model.product.Product;
import store.model.product.Products;

class StoreTest {

    private Store store;

    @BeforeEach
    void setup() {
        store = new Store();
        store.addProduct(new Product(
                "콜라",
                1000,
                null,
                new Quantity(10)
        ));
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
        store.addProduct(new Product(
                "콜라",
                1000,
                promotion,
                new Quantity(10)
        ));
    }

    @Test
    void 프로모션_받을_수_있는데_가져오지_않은_상품의_개수를_반환한다() {
        assertSimpleTest(() -> {
            assertThat(store.getRequiredFreeQuantity(new Order(
                    "콜라",
                    new Quantity(8)
            ))).isEqualTo(1);
        });
    }

    @Test
    void 프로모션_받을_수_없을_때_가져오지_않은_상품의_개수를_반환한다() {
        assertSimpleTest(() -> assertThat(store.getRequiredFreeQuantity(new Order(
                "콜라",
                new Quantity(7)
        ))).isZero());
    }

    @Test
    void 프로모션에_맞게_상품을_가져왔을때_더_가져와야_하는_상품은_없다() {
        assertSimpleTest(() -> assertThat(store.getRequiredFreeQuantity(new Order(
                "콜라",
                new Quantity(6)
        ))).isZero());
    }

    @Test
    void 프로모션_재고_보다_많이_상품을_가져왔을때_더_가져와야_하는_상품은_없다() {
        assertSimpleTest(() -> assertThat(store.getRequiredFreeQuantity(new Order(
                "콜라",
                new Quantity(11)
        ))).isZero());
    }

    @Test
    void 프로모션을_받을_수_없을_만큼의_재고가_있다면_프로모션을_받지_못한다() {
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
            store.addProduct(new Product(
                    "콜라",
                    1000,
                    promotion,
                    new Quantity(2)
            ));
            assertThat(store.getRequiredRegularQuantity(new Order(
                    "콜라",
                    new Quantity(2)
            ))).isEqualTo(2);
        });
    }
}
