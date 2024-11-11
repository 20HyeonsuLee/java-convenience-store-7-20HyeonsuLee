package store.controller;

import java.util.List;
import java.util.function.Supplier;
import store.model.Order;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;
import store.view.Parser;

public class StoreController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final StoreService storeService = new StoreService();

    public void run() {
        printProduct();
        List<Order> orders = handleInput(this::inputOrder);

    }

    private void validateOrdersQuantity(List<Order> orders) {
        orders.forEach(storeService::validateOrderQuantity);
    }

    private void printProduct() {
        storeService.getAllProducts();
        outputView.printProducts(storeService.getAllProducts());
    }

    private List<Order> inputOrder() {
        List<Order> orders = Parser.parseOrders(inputView.inputOrder());
        validateOrdersQuantity(orders);
        return orders;
    }

    private <T> T handleInput(Supplier<T> inputSupplier) {
        while (true) {
            try {
                return inputSupplier.get();
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }
}
