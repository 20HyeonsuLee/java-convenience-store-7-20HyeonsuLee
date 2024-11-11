package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoreTest {

    private Store store;

    private static final boolean MEMBERSHIP = true;

    @BeforeEach
    void setup() {
        store = new Store();
        store.addProduct(new Product(
                "콜라",
                1000,
                null,
                new Stock(10)
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
                new Stock(10)
        ));
    }

    @Test
    void 프로모션_받을_수_있는데_가져오지_않은_상품의_개수를_반환한다() {
        assertSimpleTest(() -> {
            assertThat(store.getRequiredFreeQuantity(new Order(
                    "콜라",
                    8
            ))).isEqualTo(1);
        });
    }

    @Test
    void 프로모션_받을_수_없을_때_가져오지_않은_상품의_개수를_반환한다() {
        assertSimpleTest(() -> assertThat(store.getRequiredFreeQuantity(new Order(
                "콜라",
                7
        ))).isZero());
    }

    @Test
    void 프로모션에_맞게_상품을_가져왔을때_더_가져와야_하는_상품은_없다() {
        assertSimpleTest(() -> assertThat(store.getRequiredFreeQuantity(new Order(
                "콜라",
                6
        ))).isZero());
    }

    @Test
    void 프로모션_재고_보다_많이_상품을_가져왔을때_더_가져와야_하는_상품은_없다() {
        assertSimpleTest(() -> assertThat(store.getRequiredFreeQuantity(new Order(
                "콜라",
                11
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
                    new Stock(2)
            ));
            assertThat(store.getRequiredRegularQuantity(new Order(
                    "콜라",
                    2
            ))).isEqualTo(2);
        });
    }

    @Test
    void 상점에서_상품을_구매한다() {
        assertSimpleTest(() -> {
            Receipt receipt = store.buyOrders(List.of(new Order("콜라", 11)), MEMBERSHIP);
            assertThat(receipt.getOrderReceipt().get(0).getName()).isEqualTo("콜라");
            assertThat(receipt.getOrderReceipt().get(0).getQuantity()).isEqualTo(11);
            assertThat(receipt.getFreeReceipt().get(0).getQuantity()).isEqualTo(3);
            assertThat(receipt.computePromotionDiscountPrice()).isEqualTo(3_000);
            assertThat(receipt.computeTotalCount()).isEqualTo(11);
            assertThat(receipt.computeTotalPrice()).isEqualTo(11_000);
            assertThat(receipt.computeAmount()).isEqualTo(7_400);
            assertThat(store.getProducts().find("콜라").getPromotionQuantity().count()).isZero();
            assertThat(store.getProducts().find("콜라").getRegularQuantity().count()).isEqualTo(9);
        });
    }

}
