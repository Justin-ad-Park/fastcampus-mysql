package com.example.pmo.richdomainmodel.domain.account;

import com.example.pmo.richdomainmodel.common.account.exception.InsufficientBalanceException;
import com.example.pmo.richdomainmodel.common.account.exception.InvalidAmountException;

import java.math.BigDecimal;

public class Account {
    private Long accountId;

    private String accountName;

    private Long createTime = System.currentTimeMillis();
    private BigDecimal balance = BigDecimal.ZERO;

    public Account(Long preAllocatedId) {
        this.accountId = preAllocatedId;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public BigDecimal balance() {
        return this.balance;
    }

    public void debit(BigDecimal amount) throws InsufficientBalanceException {
        if(this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("잔고부족");
        }

        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) throws InvalidAmountException {
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("입금액 오류");
        }

        this.balance = this.balance.add(amount);
    }

}
