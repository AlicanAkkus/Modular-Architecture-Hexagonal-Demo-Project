package com.hexagonaldemo.paymentapi.adapters.balance.jpa;

import com.hexagonaldemo.paymentapi.adapters.balance.jpa.entity.BalanceEntity;
import com.hexagonaldemo.paymentapi.adapters.balance.jpa.entity.BalanceTransactionEntity;
import com.hexagonaldemo.paymentapi.adapters.balance.jpa.repository.BalanceJpaRepository;
import com.hexagonaldemo.paymentapi.adapters.balance.jpa.repository.BalanceTransactionJpaRepository;
import com.hexagonaldemo.paymentapi.balance.usecase.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.common.exception.PaymentApiBusinessException;
import com.hexagonaldemo.paymentapi.common.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceDataAdapter implements BalancePort {

    private final BalanceJpaRepository balanceJpaRepository;
    private final BalanceTransactionJpaRepository balanceTransactionJpaRepository;

    @Override
    public Balance retrieve(Long accountId) {
        return balanceJpaRepository.findByAccountId(accountId)
                .orElseGet(() -> createBalance(accountId))
                .toModel();
    }

    @Override
    @Transactional
    public Balance update(Balance balance, BalanceTransactionCreate balanceTransactionCreate) {
        createBalanceTransaction(balance, balanceTransactionCreate);
        return updateBalance(balance.withAmount(balance.getAmount().add(balanceTransactionCreate.getAmountAsNumeric())));
    }

    @Override
    public void deleteAll() {
        balanceJpaRepository.deleteAll();
    }

    private void createBalanceTransaction(Balance balance, BalanceTransactionCreate balanceTransactionCreate) {
        var balanceTransactionEntity = new BalanceTransactionEntity();
        balanceTransactionEntity.setBalanceId(balance.getId());
        balanceTransactionEntity.setAmount(balanceTransactionCreate.getAmount());
        balanceTransactionEntity.setType(balanceTransactionCreate.getType());
        balanceTransactionEntity.setStatus(Status.ACTIVE);
        balanceTransactionJpaRepository.save(balanceTransactionEntity);
    }

    private Balance updateBalance(Balance balance) {
        var balanceEntity = balanceJpaRepository.findById(balance.getId())
                .orElseThrow(()-> new PaymentApiBusinessException("paymentapi.balance.notFound"));

        balanceEntity.setAmount(balance.getAmount());
        balanceEntity.setAccountId(balance.getAccountId());
        return balanceJpaRepository.save(balanceEntity).toModel();
    }

    private BalanceEntity createBalance(Long accountId) {
        var balanceEntity = new BalanceEntity();
        balanceEntity.setAmount(BigDecimal.ZERO);
        balanceEntity.setAccountId(accountId);
        balanceEntity.setStatus(Status.ACTIVE);
        return balanceJpaRepository.save(balanceEntity);
    }
}