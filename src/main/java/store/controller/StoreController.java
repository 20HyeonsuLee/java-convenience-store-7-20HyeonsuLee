package store.controller;

import java.util.List;
import java.util.function.Supplier;
import store.dto.InputProductDTO;
import store.dto.InputPromotionDTO;
import store.model.Order;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;
import store.view.Parser;

public class StoreController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final StoreService storeService = new StoreService();

    public void uploadData() {
        uploadShopData();
    }

    public void run() {
        printProduct();
        List<Order> orders = handleInput(this::inputOrder);
        reOrderPromotion(orders);
    }

    private void validateOrdersQuantity(List<Order> orders) {
        orders.forEach(storeService::validateOrderQuantity);
    }

    private void reOrderPromotion(List<Order> orders) {
        orders.forEach(order -> {
            int requiredQuantity = storeService.getRequiredFreeQuantity(order);
            if (requiredQuantity > 0) {
                storeService.reOrderPromotion(order, handleInput(() -> inputReOrderPromotion(order, requiredQuantity)));
            }
        });
    }

    private boolean inputReOrderPromotion(Order order, Integer count) {
        String confirm = inputView.inputAdditionalFreeProduct(order.getName(), count);
        return Parser.parseConfirm(confirm);
    }

    private void printProduct() {
        storeService.getAllProducts();
        outputView.printProducts(storeService.getAllProducts());
    }

    private void uploadShopData() {
        List<InputProductDTO> products = handleInput(this::inputProducts);
        List<InputPromotionDTO> promotions = handleInput(this::inputPromotions);
        storeService.uploadData(products, promotions);
    }

    private List<Order> inputOrder() {
        List<Order> orders = Parser.parseOrders(inputView.inputOrder());
        validateOrdersQuantity(orders);
        return orders;
    }

    private List<InputProductDTO> inputProducts() {
        return inputView.inputProducts();
    }

    private List<InputPromotionDTO> inputPromotions() {
        return inputView.inputPromotions();
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
