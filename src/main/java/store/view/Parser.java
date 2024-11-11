package store.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.exception.InputFormatException;
import store.exception.OrderFormatException;
import store.model.Order;
import store.model.Quantity;

public class Parser {

    private static final Pattern PATTERN = Pattern.compile("^\\[(.+?)-(\\d+)]$");

    private static final Map<String, Boolean> confirms = Map.of(
            "Y", true,
            "N", false
    );

    public static boolean parseConfirm(String confirm) {
        if (!confirms.containsKey(confirm)) {
            throw new InputFormatException();
        }
        return confirms.get(confirm);
    }

    public static List<Order> parseOrders(String order) {
        return Arrays.stream(order.split(","))
                .map(Parser::parseOrder)
                .toList();
    }

    private static Order parseOrder(String order) {
        Matcher matcher = PATTERN.matcher(order);
        if (!matcher.matches()) {
            throw new OrderFormatException();
        }
        String name = matcher.group(1);
        Integer quantity = parseQuantity(matcher.group(2));
        return new Order(name, new Quantity(quantity));
    }

    private static Integer parseQuantity(String quantity) {
        try {
            return Integer.parseInt(quantity);
        }catch (NumberFormatException exception) {
            throw new InputFormatException();
        }
    }
}
