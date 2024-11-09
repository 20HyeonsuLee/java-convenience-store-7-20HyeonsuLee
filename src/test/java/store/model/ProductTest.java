package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void 상품을_생성한다() {
        assertSimpleTest(() -> {
            Product product = new Product(
                    "콜라",
                    1000,
                    new Quantity(10),
                    null
            );
            assertThat(product.getName()).isEqualTo("콜라");
            assertThat(product.getPrice()).isEqualTo(1000);
            assertThat(product.getQuantity().getCount()).isEqualTo(10);
            assertThat(product.getPromotion()).isNull();
        });
    }

    @Test
    void 상품의_금액이_0원보다_작은_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new Product(
                "콜라",
                -1,
                new Quantity(10),
                null
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 고객이_상품을_구매한다() {
        assertSimpleTest(() -> {
            Product product = new Product(
                    "콜라",
                    1000,
                    new Quantity(4),
                    null
            );
            product.buy(2);
            assertThat(product.getQuantity().getCount()).isEqualTo(2);
        });
    }

    @Test
    void 고객이_상품을_구매할때_재고가_없다면_예외가_발생한다() {
        assertThatThrownBy(() -> {
            Product product = new Product(
                    "콜라",
                    1000,
                    new Quantity(4),
                    null
            );
            product.buy(5);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
