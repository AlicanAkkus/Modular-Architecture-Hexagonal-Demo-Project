package com.hexagonaldemo.paymentapi.integration;

import com.hexagonaldemo.paymentapi.AbstractIT;
import com.hexagonaldemo.paymentapi.IT;
import com.hexagonaldemo.paymentapi.adapters.balance.jpa.BalanceDataAdapter;
import com.hexagonaldemo.paymentapi.adapters.balance.jpa.repository.BalanceJpaRepository;
import com.hexagonaldemo.paymentapi.adapters.balance.jpa.repository.BalanceTransactionJpaRepository;
import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

@IT
@Sql(scripts = "classpath:sql/balances.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BalanceDataAdapterIT extends AbstractIT {

    @Autowired
    BalanceDataAdapter balanceDataAdapter;

    @Autowired
    BalanceJpaRepository balanceJpaRepository;

    @Autowired
    BalanceTransactionJpaRepository balanceTransactionJpaRepository;

    @Test
    void should_retrieve_balance() {
        // when
        Balance balance = balanceDataAdapter.retrieve(1001L);

        // then
        assertThat(balance).isNotNull()
                .returns(1L, from(Balance::getId))
                .returns(1001L, from(Balance::getAccountId))
                .returns(BigDecimal.valueOf(99.91), from(Balance::getAmount));
    }

    @Test
    void should_create_balance_when_balance_not_found() {
        // when
        Balance balance = balanceDataAdapter.retrieve(666L);

        assertThat(balance).isNotNull()
                .returns(2L, from(Balance::getId))
                .returns(666L, from(Balance::getAccountId))
                .returns(BigDecimal.ZERO, from(Balance::getAmount));
    }

    @Test
    void should_update_balance() {
        // given
        Balance balance = Balance.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(99.91))
                .accountId(1001L)
                .build();
        BalanceTransactionCreate balanceTransactionCreate = BalanceTransactionCreate.builder()
                .type(BalanceTransactionType.WITHDRAW)
                .amount(BigDecimal.valueOf(11.1))
                .accountId(1001L)
                .build();


        // when
        Balance updatedBalance = balanceDataAdapter.update(balance, balanceTransactionCreate);

        // then
        assertThat(updatedBalance).isNotNull()
                .returns(1L, from(Balance::getId))
                .returns(1001L, from(Balance::getAccountId))
                .returns(BigDecimal.valueOf(88.81), from(Balance::getAmount));
    }

    @Test
    void should_delete_all_balances() {
        // when
        balanceDataAdapter.deleteAll();

        // then
        var balanceEntities = balanceJpaRepository.findAll();
        assertThat(balanceEntities).isEmpty();
    }
}
