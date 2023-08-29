package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.api.mileage.Mileage;
import com.sds.icagile.cafe.api.mileage.MileageApiService;
import com.sds.icagile.cafe.exception.BizException;
import com.sds.icagile.cafe.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MileagePaymentService implements IPaymentService {

    private final MileageApiService mileageApiService;

    @Override
    public double getMileagePoint(double totalCost) {
        return 0;
    }

    @Override
    public void pay(int customerId, Order order, double mileagePoint) {
        int customerMileage = mileageApiService.getMileages(customerId);
        if(customerMileage >= order.getTotalCost()) {
            Mileage mileage = new Mileage(customerId, order.getId(), order.getTotalCost());
            mileageApiService.minusMileages(customerId, mileage);
        } else {
            throw new BizException("mileage is not enough");
        }
    }
}
