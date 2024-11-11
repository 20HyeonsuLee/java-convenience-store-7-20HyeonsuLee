package store.view;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import store.dto.InputProductDTO;
import store.dto.InputPromotionDTO;

class DataReaderTest {

    @Test
    void Products파일을_읽어드린다() throws FileNotFoundException {
        assertSimpleTest(() -> {
            List<InputProductDTO> products = DataReader.loadProductsFromFile();
            assertThat(new InputProductDTO("콜라", 1000, 10, "탄산2+1")).isEqualTo(products.get(0));
            assertThat(new InputProductDTO("콜라", 1000, 10, null)).isEqualTo(products.get(1));
            assertThat(new InputProductDTO("사이다", 1000, 8, "탄산2+1")).isEqualTo(products.get(2));
            assertThat(new InputProductDTO("사이다", 1000, 7, null)).isEqualTo(products.get(3));
            assertThat(new InputProductDTO("오렌지주스", 1800, 9, "MD추천상품")).isEqualTo(products.get(4));
            assertThat(new InputProductDTO("탄산수", 1200, 5, "탄산2+1")).isEqualTo(products.get(5));
            assertThat(new InputProductDTO("물", 500, 10, null)).isEqualTo(products.get(6));
            assertThat(new InputProductDTO("비타민워터", 1500, 6, null)).isEqualTo(products.get(7));
            assertThat(new InputProductDTO("감자칩", 1500, 5, "반짝할인")).isEqualTo(products.get(8));
            assertThat(new InputProductDTO("감자칩", 1500, 5, null)).isEqualTo(products.get(9));
            assertThat(new InputProductDTO("초코바", 1200, 5, "MD추천상품")).isEqualTo(products.get(10));
            assertThat(new InputProductDTO("초코바", 1200, 5, null)).isEqualTo(products.get(11));
            assertThat(new InputProductDTO("에너지바", 2000, 5, null)).isEqualTo(products.get(12));
            assertThat(new InputProductDTO("정식도시락", 6400, 8, null)).isEqualTo(products.get(13));
            assertThat(new InputProductDTO("컵라면", 1700, 1, "MD추천상품")).isEqualTo(products.get(14));
            assertThat(new InputProductDTO("컵라면", 1700, 10, null)).isEqualTo(products.get(15));
        });
    }

    @Test
    void Promotions파일을_읽어드린다() throws FileNotFoundException {
        assertSimpleTest(() -> {
            List<InputPromotionDTO> promotions = DataReader.loadPromotionsFromFile();
            assertThat(new InputPromotionDTO("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")));
            assertThat(new InputPromotionDTO("MD추천상품", 1, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")));
            assertThat(new InputPromotionDTO("반짝할인", 1, 1, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-30")));
        });
    }
}
