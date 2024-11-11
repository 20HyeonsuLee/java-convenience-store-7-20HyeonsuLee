package store.view;


public enum Message {
    INPUT_ORDER_MESSAGE("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    INPUT_REORDER_MESSAGE("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"),
    INPUT_MEMBERSHIP_MESSAGE("멤버십 할인을 받으시겠습니까? (Y/N)"),
    INPUT_ADDTIONAL_FREE_PRODUCT_MESSAGE("현재 %s(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    INPUT_NOT_PROMOTION_PRODUCT_MESSAGE("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
    OUTPUT_WELCOME_MESSAGE("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n"),
    OUTPUT_PRODUCT_INFO_MESSAGE("- %s %,d원 %s %s"),
    OUTPUT_RECEIPT_TITLE_DIV("==============W 편의점================"),
    OUTPUT_RECEIPT_FREE_DIV("=============증\t\t정==============="),
    OUTPUT_RECEIPT_RESULT_DIV("===================================="),
    OUTPUT_RECEIPT_HEADER(String.format("%-17s%-12s%-10s", "상품명", "수량", "금액")),
    OUTPUT_RECEIPT_ORDER_CONTENT("%-18s%-8d%,10d"),
    OUTPUT_RECEIPT_PROMOTION_CONTENT("%-17s %-8d"),
    OUTPUT_TOTAL_ORDER_PRICE("%-17s%-8d%,10d"),
    OUTPUT_PROMOTION_DISCOUNT_PRICE("%-25s%,10d"),
    OUTPUT_MEMBERSHIP_DISCOUNT_PRICE("%-25s%,10d"),
    OUTPUT_PAYMENT_PRICE("%-25s%,10d"),
    OUTPUT_EMPTY_ORDER("주문할 상품이 없습니다. 프로그램을 다시 시작합니다.")
    ;

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public void print(Object... values) {
        System.out.println(format(values));
    }

    public String format(Object... values) {
        return String.format(message, values);
    }
}
