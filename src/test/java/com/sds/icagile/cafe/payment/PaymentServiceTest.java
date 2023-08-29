package com.sds.icagile.cafe.payment;

import com.sds.icagile.cafe.order.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    public static final int CUSTOMER_ID = 24264;
    public static final PaymentType PAYMENT_CASH = PaymentType.CASH;
    public static final PaymentType PAYMENT_CARD = PaymentType.CARD;
    public static final PaymentType PAYMENT_MILEAGE = PaymentType.MILEAGE;

    private PaymentService subject;

    @Mock
    private CashPaymentService mockCashPaymentService;

    @Mock
    private CardPaymentService mockCardPaymentService;

    @Mock
    private MileagePaymentService mockMileagePaymentService;

    @BeforeEach
    public void setUp() {
        when(mockCashPaymentService.getPaymentType()).thenReturn(PaymentType.CASH);
        when(mockCardPaymentService.getPaymentType()).thenReturn(PaymentType.CARD);
        when(mockMileagePaymentService.getPaymentType()).thenReturn(PaymentType.MILEAGE);

        PaymentServiceFactory paymentServiceFactory = new PaymentServiceFactory(
                Arrays.asList(mockCardPaymentService, mockCashPaymentService, mockMileagePaymentService) );

        subject = new PaymentService(paymentServiceFactory);
    }

    @Test
    public void 현금_결제로_마일리지계산시_CashPaymentService_getMileagePoint가_실행된다() {
        //given

        //when
        subject.getMileagePoint(PAYMENT_CASH, 200.0);

        //then
        verify(mockCashPaymentService, times(1)).getMileagePoint(200.0);
    }

    @Test
    public void 카드_결제로_마일리지계산시_CardPaymentService_getMileagePoint가_실행된다() {
        //given

        //when
        subject.getMileagePoint(PAYMENT_CARD, 200.0);

        //then
        verify(mockCardPaymentService, times(1)).getMileagePoint(200.0);
    }

    @Test
    public void 마일리지_결제로_마일리지계산시_CardPaymentService_getMileagePoint가_실행된다() {
        //given

        //when
        subject.getMileagePoint(PAYMENT_MILEAGE, 200.0);

        //then
        verify(mockMileagePaymentService, times(1)).getMileagePoint(200.0);
    }

    @Test
    public void 현금_결제시_CashPaymentService_pay가_실행된다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);

        //when
        subject.pay(CUSTOMER_ID, PAYMENT_CASH, order, 200.0);

        //then
        verify(mockCashPaymentService, times(1)).pay(CUSTOMER_ID, order, 200.0);
    }

    @Test
    public void 카드_결제시_CardPaymentService_pay가_실행된다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);

        //when
        subject.pay(CUSTOMER_ID, PAYMENT_CARD, order, 100.0);

        //then
        verify(mockCardPaymentService, times(1)).pay(CUSTOMER_ID, order, 100.0);
    }

    @Test
    public void 마일리지_결제시_CardPaymentService_pay가_실행된다() {
        //given
        Order order = new Order();
        order.setTotalCost(2000.0);

        //when
        subject.pay(CUSTOMER_ID, PAYMENT_MILEAGE, order, 0.0);

        //then
        verify(mockMileagePaymentService, times(1)).pay(CUSTOMER_ID, order, 0.0);
    }
}
