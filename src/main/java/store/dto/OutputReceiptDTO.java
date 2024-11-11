package store.dto;

import java.util.List;
import store.model.Receipt;
import store.model.ReceiptDetail;

public record OutputReceiptDTO(
        List<ReceiptDetailDTO> order,
        List<ReceiptDetailDTO> free,
        Integer totalCount,
        Integer totalPrice,
        Integer promotionDiscount,
        Integer memberShipDiscount,
        Integer amount
) {
    public static OutputReceiptDTO from(Receipt receipt) {
        return new OutputReceiptDTO(
                receipt.getOrderReceipt().stream().map(ReceiptDetailDTO::from).toList(),
                receipt.getFreeReceipt().stream().map(ReceiptDetailDTO::from).toList(),
                receipt.computeTotalCount(),
                receipt.computeTotalPrice(),
                -receipt.computePromotionDiscountPrice(),
                -receipt.getMembershipDiscountPrice(),
                receipt.computeAmount()
        );
    }

    public record ReceiptDetailDTO(
            String name,
            Integer quantity,
            Integer price
    ) {
        public static ReceiptDetailDTO from(ReceiptDetail receiptDetail) {
            return new ReceiptDetailDTO(
                    receiptDetail.getName(),
                    receiptDetail.getQuantity().getCount(),
                    receiptDetail.getPrice()
            );
        }
    }
}
