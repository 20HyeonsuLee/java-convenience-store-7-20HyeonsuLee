package store.dto;

import store.exception.InputFormatException;

public record InputProductDTO(
        String name,
        Integer price,
        Integer quantity,
        String promotion
) {
    public static InputProductDTO from(String line) {
        String[] parsed = line.split(",");
        return new InputProductDTO(
                parsed[0],
                parseInteger(parsed[1]),
                parseInteger(parsed[2]),
                parsePromotion(parsed[3])
        );
    }

    public static String parsePromotion(String promotion) {
        if (promotion.equals("null")) {
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
