package store.controller;

import java.util.List;
import java.util.function.Supplier;
import store.dto.InputProductDTO;
import store.dto.InputPromotionDTO;
import store.dto.OutputReceiptDTO;
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
        reOrderRegular(orders);
        orders = removeEmptyOrders(orders);
        if (orders.isEmpty()) {
            outputView.printEmptyOrder();
            run();
            return;
        }
        outputView.printReceipt(OutputReceiptDTO.from(storeService.buy(
                orders,
                handleInput(this::inputMembershipConfirm)
        )));
        retryIfConfirmed();
    }

    private void retryIfConfirmed() {
        boolean retryConfirm = handleInput(this::inputReOrderConfirm);
        if (retryConfirm) {
            run();
        }
    }

    private boolean inputReOrderConfirm() {
        String confirm = inputView.inputReOrder();
        return Parser.parseConfirm(confirm);
    }

    private boolean inputMembershipConfirm() {
        String confirm = inputView.inputMemberShip();
        return Parser.parseConfirm(confirm);
    }

    private void validateOrdersQuantity(List<Order> orders) {
        orders.forEach(storeService::validateOrderQuantity);
    }

    private void reOrderRegular(List<Order> orders) {
        orders.forEach(order -> {
            int requiredRegularQuantity = storeService.getRequiredRegularQuantity(order);
            if (storeService.isPromotionOrder(order) && requiredRegularQuantity > 0) {
                storeService.reOrderOrder(order,
                        handleInput(() -> inputReOrderRegular(order, requiredRegularQuantity)));
            }
        });
    }

    private List<Order> removeEmptyOrders(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getQuantity() != 0)
                .toList();
    }

    private boolean inputReOrderRegular(Order order, Integer count) {
        String confirm = inputView.inputNotPromotionProduct(order.getName(), count);
        return Parser.parseConfirm(confirm);
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
