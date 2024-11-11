package store.dto;

import static store.dto.Constant.DATA_FILE_FIELD_SEPERATOR;
import static store.dto.Constant.NOT_PROMOTION;

import store.exception.InputFormatException;

public record InputProductDTO(
        String name,
        Integer price,
        Integer quantity,
        String promotion
) {
    public static InputProductDTO from(String line) {
        String[] parsed = line.split(DATA_FILE_FIELD_SEPERATOR);
        return new InputProductDTO(
                parsed[0],
                parseInteger(parsed[1]),
                parseInteger(parsed[2]),
                parsePromotion(parsed[3])
        );
    }

    public static String parsePromotion(String promotion) {
        if (promotion.equals(NOT_PROMOTION)) {
            return null;
        }
        return promotion;
    }

    public static Integer parseInteger(String price) {
        try {
            return Integer.parseInt(price);
        }catch (NumberFormatException exception) {
            throw new InputFormatException();
        }
    }
}
