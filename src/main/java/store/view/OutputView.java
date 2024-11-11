package store.view;

import static store.view.Message.OUTPUT_EMPTY_ORDER;
import static store.view.Message.OUTPUT_MEMBERSHIP_DISCOUNT_PRICE;
import static store.view.Message.OUTPUT_PAYMENT_PRICE;
import static store.view.Message.OUTPUT_PRODUCT_INFO_MESSAGE;
import static store.view.Message.OUTPUT_PROMOTION_DISCOUNT_PRICE;
import static store.view.Message.OUTPUT_RECEIPT_ORDER_CONTENT;
import static store.view.Message.OUTPUT_RECEIPT_FREE_DIV;
import static store.view.Message.OUTPUT_RECEIPT_HEADER;
import static store.view.Message.OUTPUT_RECEIPT_PROMOTION_CONTENT;
import static store.view.Message.OUTPUT_RECEIPT_RESULT_DIV;
import static store.view.Message.OUTPUT_RECEIPT_TITLE_DIV;
import static store.view.Message.OUTPUT_TOTAL_ORDER_PRICE;
import static store.view.Message.OUTPUT_WELCOME_MESSAGE;

import java.util.List;
import store.dto.OutputProductDTO;
import store.dto.OutputReceiptDTO;

public class OutputView {

    private static final String LACK_QUANTITY_MESSAGE = "재고 없음";
    private static final String TATAL_PRICE_HEADER = "총구매액";
    private static final String PROMOTION_HEADER = "행사할인";
    private static final String MEMBERSHIP_HEADER = "멤버십할인";
    private static final String AMOUNT_HEADER = "내실돈";


    public void printEmptyOrder() {
        OUTPUT_EMPTY_ORDER.print();
    }

    public void printProducts(List<OutputProductDTO> products) {
        PrintStringBuilder stringBuilder = new PrintStringBuilder();
        stringBuilder.appendLine(OUTPUT_WELCOME_MESSAGE.format());
        products.forEach(product -> stringBuilder.appendLine(OUTPUT_PRODUCT_INFO_MESSAGE.format(
                product.name(),
                product.price(),
                convertQuantityFormat(product.quantity()),
                product.promotion()
        )));
        stringBuilder.print();
    }

    private String convertQuantityFormat(Integer quantity) {
        if (quantity == 0) {
            return LACK_QUANTITY_MESSAGE;
        }
        return String.valueOf(quantity);
    }

    public void printReceipt(OutputReceiptDTO outputReceiptDTO) {
        PrintStringBuilder stringBuilder = new PrintStringBuilder();
        appendTitle(stringBuilder);
        appendOrderItems(stringBuilder, outputReceiptDTO);
        appendFreeItems(stringBuilder, outputReceiptDTO);
        appendSummary(stringBuilder, outputReceiptDTO);
        stringBuilder.print();
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    private void appendTitle(PrintStringBuilder stringBuilder) {
        stringBuilder.appendLine(OUTPUT_RECEIPT_TITLE_DIV.format());
        stringBuilder.appendLine(OUTPUT_RECEIPT_HEADER.format());
    }

    private void appendOrderItems(PrintStringBuilder stringBuilder, OutputReceiptDTO outputReceiptDTO) {
        outputReceiptDTO.order().forEach(receipt -> stringBuilder.appendLine(OUTPUT_RECEIPT_ORDER_CONTENT.format(
                receipt.name(),
                receipt.quantity(),
                receipt.price()
        )));
    }

    private void appendFreeItems(PrintStringBuilder stringBuilder, OutputReceiptDTO outputReceiptDTO) {
        stringBuilder.appendLine(OUTPUT_RECEIPT_FREE_DIV.format());
        outputReceiptDTO.free().forEach(receipt -> stringBuilder.appendLine(OUTPUT_RECEIPT_PROMOTION_CONTENT.format(
                receipt.name(),
                receipt.quantity()
        )));
    }

    private void appendSummary(PrintStringBuilder stringBuilder, OutputReceiptDTO outputReceiptDTO) {
        stringBuilder.appendLine(OUTPUT_RECEIPT_RESULT_DIV.format());
        stringBuilder.appendLine(OUTPUT_TOTAL_ORDER_PRICE.format(TATAL_PRICE_HEADER, outputReceiptDTO.totalCount(), outputReceiptDTO.totalPrice()));
        stringBuilder.appendLine(OUTPUT_PROMOTION_DISCOUNT_PRICE.format(PROMOTION_HEADER, outputReceiptDTO.promotionDiscount()));
        stringBuilder.appendLine(OUTPUT_MEMBERSHIP_DISCOUNT_PRICE.format(MEMBERSHIP_HEADER, outputReceiptDTO.memberShipDiscount()));
        stringBuilder.appendLine(OUTPUT_PAYMENT_PRICE.format(AMOUNT_HEADER, outputReceiptDTO.amount()));
    }
}
