package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.api.mileage.Mileage;
import com.sds.icagile.cafe.api.mileage.MileageApiService;
import com.sds.icagile.cafe.exception.BizException;
import com.sds.icagile.cafe.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final CashPaymentService cashPaymentService;
    private final CardPaymentService cardPaymentService;
    private final MileagePaymentService mileagePaymentService;

    public double getMileagePoint(int payment, double totalCost) {
        if (payment == 1) {
            return cashPaymentService.getMileagePoint(totalCost);
        } else if (payment == 2) {
            return cardPaymentService.getMileagePoint(totalCost);
        } else if (payment == 3) {
            return mileagePaymentService.getMileagePoint(totalCost);
        }
        return 0.0;
    }

    public void pay(int customerId, int payment, Order order, double mileagePoint) {
        if(payment == 1) {
            cashPaymentService.pay(customerId, order, mileagePoint);
        } else if (payment == 2) {
            cardPaymentService.pay(customerId, order, mileagePoint);
        } else if (payment == 3) {
            mileagePaymentService.pay(customerId, order, mileagePoint);
        }
    }

}
