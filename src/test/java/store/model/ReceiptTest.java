package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReceiptTest {

    private Receipt receipt;

    @BeforeEach
    void setup() {
        receipt = new Receipt();
        receipt.addOrderProduct(new ReceiptDetail("콜라", 10, 15_000));
        receipt.addOrderProduct(new ReceiptDetail("마늘", 5, 5_000));
        receipt.addFreeProduct(new ReceiptDetail("콜라", 3, 3_000));
        receipt.addMembershipDiscountPrice(3_000);
    }

    @Test
    void 영수증의_상품을_정렬한다() {
        assertSimpleTest(() -> {
            assertThat(receipt.getOrderReceipt().get(0).getName()).isEqualTo("마늘");
            assertThat(receipt.getOrderReceipt().get(1).getName()).isEqualTo("콜라");
        });
    }

    @Test
    void 영수증의_총금액을_계산한다() {
        assertSimpleTest(() -> {
            assertThat(receipt.computeAmount()).isEqualTo(14_000);
        });
    }

    @Test
    void 영수증의_행사할인_금액을_계산한다() {
        assertSimpleTest(() -> {
            assertThat(receipt.computePromotionDiscountPrice()).isEqualTo(3_000);
        });
    }

    @Test
    void 영수증의_총_상품_개수를_반환한다() {
        assertSimpleTest(() -> {
            assertThat(receipt.computeTotalCount()).isEqualTo(15);
        });
    }

    @Test
    void 영수증_상품의_총_금액을_반환한다() {
        assertSimpleTest(() -> {
            assertThat(receipt.computeTotalPrice()).isEqualTo(20000);
        });
    }
}
