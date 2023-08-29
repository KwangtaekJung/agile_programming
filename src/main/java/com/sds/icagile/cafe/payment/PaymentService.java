package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentServiceFactory paymentServiceFactory;

    public double getMileagePoint(PaymentType payment, double totalCost) {
        return this.paymentServiceFactory.getService(payment).getMileagePoint(totalCost);
    }

    public void pay(int customerId, PaymentType payment, Order order, double mileagePoint) {
       this.paymentServiceFactory.getService(payment).pay(customerId, order, mileagePoint);
    }

}
