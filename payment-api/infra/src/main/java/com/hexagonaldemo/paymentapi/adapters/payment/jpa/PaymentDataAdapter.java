package com.hexagonaldemo.paymentapi.adapters.payment.jpa;

import com.hexagonaldemo.paymentapi.adapters.payment.jpa.entity.PaymentEntity;
import com.hexagonaldemo.paymentapi.adapters.payment.jpa.repository.PaymentJpaRepository;
import com.hexagonaldemo.paymentapi.common.model.Status;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.model.PaymentState;
import com.hexagonaldemo.paymentapi.payment.command.PaymentCreate;
import com.hexagonaldemo.paymentapi.payment.port.PaymentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentDataAdapter implements PaymentPort {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment create(PaymentCreate paymentCreate) {
        var paymentEntity = new PaymentEntity();
        paymentEntity.setAccountId(paymentCreate.getAccountId());
        paymentEntity.setPrice(paymentCreate.getPrice());
        paymentEntity.setReferenceCode(paymentCreate.getReferenceCode());
        paymentEntity.setState(PaymentState.SUCCESS);
        paymentEntity.setStatus(Status.ACTIVE);

        var savedPaymentEntity = paymentJpaRepository.save(paymentEntity);
        return toModel(savedPaymentEntity);
    }

    private Payment toModel(PaymentEntity paymentEntity) {
        return Payment.builder()
                .id(paymentEntity.getId())
                .price(paymentEntity.getPrice())
                .accountId(paymentEntity.getAccountId())
                .referenceCode(paymentEntity.getReferenceCode())
                .state(paymentEntity.getState())
                .build();
    }
}