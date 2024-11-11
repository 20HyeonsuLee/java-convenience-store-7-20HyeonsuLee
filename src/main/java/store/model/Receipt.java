package store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Receipt {

    private final List<ReceiptDetail> orderReceipt = new ArrayList<>();
    private final List<ReceiptDetail> freeReceipt = new ArrayList<>();
    private double membershipDiscountPrice = 0;

    public List<ReceiptDetail> getOrderReceipt() {
        Collections.sort(orderReceipt);
        return orderReceipt;
    }

    public List<ReceiptDetail> getFreeReceipt() {
        Collections.sort(freeReceipt);
        return freeReceipt;
    }

    public void addOrderProduct(ReceiptDetail receiptDetail) {
        orderReceipt.add(receiptDetail);
    }

    public void addFreeProduct(ReceiptDetail receiptDetail) {
        freeReceipt.add(receiptDetail);
    }

    public void addMembershipDiscountPrice(double price) {
        membershipDiscountPrice += price;
    }

    public Integer computeTotalCount() {
        return orderReceipt.stream().mapToInt(ReceiptDetail::getQuantity).sum();
    }

    public Integer computeTotalPrice() {
        return orderReceipt.stream().mapToInt(ReceiptDetail::getPrice).sum();
    }

    public Integer computeAmount() {
        return computeTotalPrice() - computePromotionDiscountPrice() - getMembershipDiscountPrice();
    }

    public Integer computePromotionDiscountPrice() {
        return freeReceipt.stream().mapToInt(ReceiptDetail::getPrice).sum();
    }

    public Integer getMembershipDiscountPrice() {
        return (int) membershipDiscountPrice;
    }

}
