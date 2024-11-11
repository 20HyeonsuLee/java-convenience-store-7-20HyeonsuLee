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

    public void printEmptyOrder() {
        OUTPUT_EMPTY_ORDER.print();
    }

    public void printProducts(List<OutputProductDTO> products) {
        PrintStringBuilder stringBuilder = new PrintStringBuilder();
        stringBuilder.appendLine(OUTPUT_WELCOME_MESSAGE.format());
        products.forEach(product -> stringBuilder.appendLine(OUTPUT_PRODUCT_INFO_MESSAGE.format(
                product.name(),
                product.price(),
                product.quantity(),
                product.promotion()
        )));
        stringBuilder.print();
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
        stringBuilder.appendLine(OUTPUT_TOTAL_ORDER_PRICE.format("총구매액", outputReceiptDTO.totalCount(), outputReceiptDTO.totalPrice()));
        stringBuilder.appendLine(OUTPUT_PROMOTION_DISCOUNT_PRICE.format("행사할인", outputReceiptDTO.promotionDiscount()));
        stringBuilder.appendLine(OUTPUT_MEMBERSHIP_DISCOUNT_PRICE.format("멤버십할인", outputReceiptDTO.memberShipDiscount()));
        stringBuilder.appendLine(OUTPUT_PAYMENT_PRICE.format("내실돈", outputReceiptDTO.amount()));
    }
}
