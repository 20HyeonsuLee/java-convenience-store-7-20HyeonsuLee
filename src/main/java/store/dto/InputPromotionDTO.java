package store.dto;

import static store.dto.Constant.DATA_FILE_FIELD_SEPERATOR;

import java.time.LocalDate;
import store.exception.InputFormatException;

public record InputPromotionDTO(
        String name,
        Integer buy,
        Integer get,
        LocalDate startDate,
        LocalDate endDate
) {
    public static InputPromotionDTO from(String line) {
        String[] parsed = line.split(DATA_FILE_FIELD_SEPERATOR);
        return new InputPromotionDTO(
                parsed[0],
                parseInteger(parsed[1]),
                parseInteger(parsed[2]),
                parseDate(parsed[3]),
                parseDate(parsed[4])
        );
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date);
    }

    public static Integer parseInteger(String price) {
        try {
            return Integer.parseInt(price);
        }catch (NumberFormatException exception) {
            throw new InputFormatException();
        }
    }
}
