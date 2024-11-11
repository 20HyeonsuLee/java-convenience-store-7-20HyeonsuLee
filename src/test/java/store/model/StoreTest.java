package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StoreTest {

    private Store store;

    private static final boolean MEMBERSHIP = true;

    @BeforeEach
    void setup() {
        store = new Store();
        store.addProduct(new Product("콜라", 1000, null, new Stock(10)));
        store.addProduct(new Product("콜라", 1000, createPromotion(), new Stock(10)));
    }

    private Promotion createPromotion() {
        Period period = new Period(
                DateTimes.now().minusDays(10).toLocalDate(),
                DateTimes.now().toLocalDate()
        );
        return new Promotion("탄산", 2, 1, period);
    }

    private Order createOrder(String name, int quantity) {
        return new Order(name, quantity);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 8",   // 프로모션 받을 수 있는데 가져오지 않은 상품 개수
            "0, 7",   // 프로모션 받을 수 없을 때 가져오지 않은 상품 개수
            "0, 6",   // 프로모션에 맞게 가져왔을 때 더 가져와야 할 상품 없음
            "0, 11"   // 프로모션 재고보다 많이 가져왔을 때 더 가져와야 할 상품 없음
    })
    void 프로모션_상품의_가져오지_않은_개수_확인(int expectedFreeQuantity, int orderQuantity) {
        assertSimpleTest(() -> {
            Order order = createOrder("콜라", orderQuantity);
            assertThat(store.getRequiredFreeQuantity(order)).isEqualTo(expectedFreeQuantity);
        });
    }

    @Test
    void 프로모션을_받을_수_없을_만큼의_재고가_있다면_프로모션을_받지_못한다() {
        assertSimpleTest(() -> {
            store.addProduct(new Product("콜라", 1000, createPromotion(), new Stock(2)));
            assertThat(store.getRequiredRegularQuantity(createOrder("콜라", 2))).isEqualTo(2);
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
            assertThat(store.getProducts().find("콜라").getPromotionStock().count()).isZero();
            assertThat(store.getProducts().find("콜라").getRegularStock().count()).isEqualTo(9);
        });
    }
}
