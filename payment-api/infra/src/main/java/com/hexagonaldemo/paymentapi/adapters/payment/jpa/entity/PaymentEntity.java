package com.hexagonaldemo.paymentapi.adapters.payment.jpa.entity;

import com.hexagonaldemo.paymentapi.common.entity.AbstractEntity;
import com.hexagonaldemo.paymentapi.payment.model.PaymentState;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "payment")
@Table(name = "payment")
@Where(clause = "status <> -1")
public class PaymentEntity extends AbstractEntity {

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String referenceCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentState state;
}