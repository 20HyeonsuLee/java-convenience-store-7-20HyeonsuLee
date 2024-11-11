package store.view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import store.dto.InputProductDTO;
import store.dto.InputPromotionDTO;

public class DataReader {

    public static List<InputProductDTO> loadProductsFromFile() {
        ClassLoader classLoader = DataReader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("products.md");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines()
                .skip(1) // 첫 번째 줄(헤더) 건너뛰기
                .map(InputProductDTO::from)
                .toList();
    }

    public static List<InputPromotionDTO> loadPromotionsFromFile() {
        ClassLoader classLoader = DataReader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("promotions.md");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines()
                .skip(1) // 첫 번째 줄(헤더) 건너뛰기
                .map(InputPromotionDTO::from)
                .toList();
    }
}
