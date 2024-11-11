package store.view;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.exception.InputFormatException;
import store.exception.OrderFormatException;
import store.model.Order;

public class Parser {

    private static final Pattern PATTERN = Pattern.compile("^\\[(.+?)-(\\d+)]$");
    private static final String CONFIRM_COMMAND = "Y";
    private static final String NOT_CONFIRM_COMMAND = "N";
    private static final String ORDER_COMMAND_SEPERATOR = ",";
    private static final int PRODUCT_NAME_GROUP_INDEX = 1;
    private static final int QUANTITY_GROUP_INDEX = 2;

    private static final Map<String, Boolean> confirms = Map.of(
            CONFIRM_COMMAND, true,
            NOT_CONFIRM_COMMAND, false
    );

    public static boolean parseConfirm(String confirm) {
        if (!confirms.containsKey(confirm)) {
            throw new InputFormatException();
        }
        return confirms.get(confirm);
    }

    public static List<Order> parseOrders(String order) {
        return Arrays.stream(order.split(ORDER_COMMAND_SEPERATOR))
                .map(Parser::parseOrder)
                .toList();
    }

    private static Order parseOrder(String order) {
        Matcher matcher = PATTERN.matcher(order);
        if (!matcher.matches()) {
            throw new OrderFormatException();
        }
        String name = matcher.group(PRODUCT_NAME_GROUP_INDEX);
        Integer quantity = parseQuantity(matcher.group(QUANTITY_GROUP_INDEX));
        return new Order(name, quantity);
    }

    private static Integer parseQuantity(String quantity) {
        try {
            return Integer.parseInt(quantity);
        }catch (NumberFormatException exception) {
            throw new InputFormatException();
        }
    }
}
