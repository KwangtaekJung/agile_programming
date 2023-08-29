package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.api.mileage.Mileage;
import com.sds.icagile.cafe.api.mileage.MileageApiService;
import com.sds.icagile.cafe.order.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardPaymentServiceTest {

    public static final int CUSTOMER_ID = 24264;

    private CardPaymentService subject;

    @Mock
    private MileageApiService mockMileageApiService;

    @Captor
    private ArgumentCaptor<Mileage> mileageCaptor;


    @BeforeEach
    public void setUp() {
        subject = new CardPaymentService(mockMileageApiService);
    }

    @Test
    public void 카드_결제시_mileage는_TotalCost의_5퍼센트이다() {
        //given

        //when
        double mileagePoint = subject.getMileagePoint(2000.0);

        //then
        assertThat(mileagePoint, is(100.0));
    }

    @Test
    public void 카드_결제시_TotalCost의_5퍼센트를_마일리지로_적립한다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);
        double mileagePoint = subject.getMileagePoint(2000.0);

        //when
        subject.pay(CUSTOMER_ID, order, mileagePoint);

        //then
        verify(mockMileageApiService, times(1)).saveMileages(eq(CUSTOMER_ID), mileageCaptor.capture());
        Mileage appliedMileage = mileageCaptor.getValue();
        assertThat(appliedMileage.getValue(), is(100.0));
    }

}
