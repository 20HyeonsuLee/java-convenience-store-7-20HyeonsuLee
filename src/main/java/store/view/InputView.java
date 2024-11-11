package store.view;

import static store.view.Message.INPUT_ADDTIONAL_FREE_PRODUCT_MESSAGE;
import static store.view.Message.INPUT_MEMBERSHIP_MESSAGE;
import static store.view.Message.INPUT_NOT_PROMOTION_PRODUCT_MESSAGE;
import static store.view.Message.INPUT_ORDER_MESSAGE;
import static store.view.Message.INPUT_REORDER_MESSAGE;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import store.dto.InputProductDTO;
import store.dto.InputPromotionDTO;

public class InputView {

    public List<InputProductDTO> inputProducts() {
        return DataReader.loadProductsFromFile();
    }

    public List<InputPromotionDTO> inputPromotions() {
        return DataReader.loadPromotionsFromFile();
    }

    public String inputOrder() {
        INPUT_ORDER_MESSAGE.print();
        return Console.readLine();
    }

    public String inputReOrder() {
        INPUT_REORDER_MESSAGE.print();
        return Console.readLine();
    }

    public String inputMemberShip() {
        INPUT_MEMBERSHIP_MESSAGE.print();
        return Console.readLine();
    }

    public String inputAdditionalFreeProduct(String name, Integer count) {
        INPUT_ADDTIONAL_FREE_PRODUCT_MESSAGE.print(name, count);
        return Console.readLine();
    }

    public String inputNotPromotionProduct(String name, Integer count) {
        INPUT_NOT_PROMOTION_PRODUCT_MESSAGE.print(name, count);
        return Console.readLine();
    }
}
