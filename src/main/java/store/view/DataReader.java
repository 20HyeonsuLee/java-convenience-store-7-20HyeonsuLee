package store.view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import store.dto.InputProductDTO;
import store.dto.InputPromotionDTO;

public class DataReader {

    private static final String PRODUCT_FILE_PATH = "products.md";
    private static final String PROMOTION_FILE_PATH = "promotions.md";

    public static List<InputProductDTO> loadProductsFromFile() {
        ClassLoader classLoader = DataReader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(PRODUCT_FILE_PATH);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines()
                .skip(1)
                .map(InputProductDTO::from)
                .toList();
    }

    public static List<InputPromotionDTO> loadPromotionsFromFile() {
        ClassLoader classLoader = DataReader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(PROMOTION_FILE_PATH);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines()
                .skip(1)
                .map(InputPromotionDTO::from)
                .toList();
    }
}
