package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.model.product.Product;
import store.model.product.Products;

class ProductsTest {

    private Products products;

    @BeforeEach
    void setup() {
        products = new Products();
    }

    @Test
    void 일반_상품을_추가한다() {
        assertSimpleTest(() -> {
            products.addProduct(new Product(
                    "콜라",
                    1000,
                    null,
                    new Quantity(10)
            ));
            assertThat(products.find("콜라").getName()).isEqualTo("콜라");
            assertThat(products.find("콜라").getPrice()).isEqualTo(1000);
            assertThat(products.find("콜라").isPromotionProduct()).isFalse();
            assertThat(products.find("콜라").getRegularQuantity().getCount()).isEqualTo(10);
            assertThat(products.find("콜라").getPromotionQuantity().getCount()).isZero();
            assertThat(products.find("콜라").isPromotionPeriod()).isFalse();

        });
    }

    @Test
    void 프로모션_상품을_추가한다() {
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
            products.addProduct(new Product(
                    "콜라",
                    1000,
                    promotion,
                    new Quantity(10)
            ));
            assertThat(products.find("콜라").getName()).isEqualTo("콜라");
            assertThat(products.find("콜라").getPrice()).isEqualTo(1000);
            assertThat(products.find("콜라").isPromotionProduct()).isTrue();
            assertThat(products.find("콜라").getRegularQuantity().getCount()).isZero();
            assertThat(products.find("콜라").getPromotionQuantity().getCount()).isEqualTo(10);
            assertThat(products.find("콜라").isPromotionPeriod()).isTrue();
        });
    }

    @Test
    void 프로모션_상품이_이미_존재하는_경우_일반_상품을_추가한다() {
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
            products.addProduct(new Product(
                    "콜라",
                    1000,
                    promotion,
                    new Quantity(10)
            ));
            products.addProduct(new Product(
                    "콜라",
                    1000,
                    null,
                    new Quantity(10)
            ));
            assertThat(products.find("콜라").getName()).isEqualTo("콜라");
            assertThat(products.find("콜라").getPrice()).isEqualTo(1000);
            assertThat(products.find("콜라").isPromotionProduct()).isTrue();
            assertThat(products.find("콜라").getRegularQuantity().getCount()).isEqualTo(10);
            assertThat(products.find("콜라").getPromotionQuantity().getCount()).isEqualTo(10);
            assertThat(products.find("콜라").isPromotionPeriod()).isTrue();
        });
    }

    @Test
    void 일반_상품이_이미_존재하는_경우_프로모션_상품을_추가한다() {
        assertSimpleTest(() -> {
            products.addProduct(new Product(
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
            products.addProduct(new Product(
                    "콜라",
                    1000,
                    promotion,
                    new Quantity(10)
            ));
            assertThat(products.find("콜라").getName()).isEqualTo("콜라");
            assertThat(products.find("콜라").getPrice()).isEqualTo(1000);
            assertThat(products.find("콜라").isPromotionProduct()).isTrue();
            assertThat(products.find("콜라").getRegularQuantity().getCount()).isEqualTo(10);
            assertThat(products.find("콜라").getPromotionQuantity().getCount()).isEqualTo(10);
            assertThat(products.find("콜라").isPromotionPeriod()).isTrue();
        });
    }

    @Test
    void 일반_상품이_이미_존재하는_경우_새로운_상품으로_대체한다() {
        assertSimpleTest(() -> {
            products.addProduct(new Product(
                    "콜라",
                    1000,
                    null,
                    new Quantity(10)
            ));
            products.addProduct(new Product(
                    "콜라",
                    1500,
                    null,
                    new Quantity(15)
            ));
            assertThat(products.find("콜라").getName()).isEqualTo("콜라");
            assertThat(products.find("콜라").getPrice()).isEqualTo(1500);
            assertThat(products.find("콜라").isPromotionProduct()).isFalse();
            assertThat(products.find("콜라").getRegularQuantity().getCount()).isEqualTo(15);
            assertThat(products.find("콜라").getPromotionQuantity().getCount()).isZero();
            assertThat(products.find("콜라").isPromotionPeriod()).isFalse();
        });
    }

    @Test
    void 일반_상품만_존재하는_경우프로모션을_받을_수_있는_회수를_조회한다() {
        assertSimpleTest(() -> {
            products.addProduct(new Product(
                    "콜라",
                    1000,
                    null,
                    new Quantity(10)
            ));
            assertThat(products.getPromotableCount(new Order("콜라", new Quantity(10)))).isZero();
        });
    }

    @Test
    void 프로모션을_받을_수_있는_회수를_조회한다() {
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
            products.addProduct(new Product(
                    "콜라",
                    1000,
                    promotion,
                    new Quantity(10)
            ));
            assertThat(products.getPromotableCount(new Order(
                    "콜라",
                    new Quantity(10)
            ))).isEqualTo(3);
        });
    }

    @Test
    void 프로모션을_받을_수_있는_상품의_개수를_조회한다() {
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
            products.addProduct(new Product(
                    "콜라",
                    1000,
                    promotion,
                    new Quantity(10)
            ));
            assertThat(products.getPromotableQuantity(new Order(
                    "콜라",
                    new Quantity(10)
            ))).isEqualTo(9);
        });
    }

    @Test
    void 프로모션_상품보다_많이_구매하는_경우_받을_수_있는_회수를_조회한다() {
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
            products.addProduct(new Product(
                    "콜라",
                    1000,
                    promotion,
                    new Quantity(10)
            ));
            assertThat(products.getPromotableCount(new Order(
                    "콜라",
                    new Quantity(15)
            ))).isEqualTo(3);
        });
    }
}
