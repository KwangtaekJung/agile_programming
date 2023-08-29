package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final CashPaymentService cashPaymentService;
    private final CardPaymentService cardPaymentService;
    private final MileagePaymentService mileagePaymentService;

    public double getMileagePoint(PaymentType payment, double totalCost) {
        if (payment == PaymentType.CASH) {
            return cashPaymentService.getMileagePoint(totalCost);
        } else if (payment == PaymentType.CARD) {
            return cardPaymentService.getMileagePoint(totalCost);
        } else if (payment == PaymentType.MILEAGE) {
            return mileagePaymentService.getMileagePoint(totalCost);
        }
        return 0.0;
    }

    public void pay(int customerId, PaymentType payment, Order order, double mileagePoint) {
        if(payment == PaymentType.CASH) {
            cashPaymentService.pay(customerId, order, mileagePoint);
        } else if (payment == PaymentType.CARD) {
            cardPaymentService.pay(customerId, order, mileagePoint);
        } else if (payment == PaymentType.MILEAGE) {
            mileagePaymentService.pay(customerId, order, mileagePoint);
        }
    }

}
