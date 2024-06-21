package com.example.pmo.richdomainmodel.domain.point;

import com.example.pmo.richdomainmodel.domain.common.exception.InsufficientBalanceException;
import com.example.pmo.richdomainmodel.domain.common.exception.InvalidAmountException;

import java.math.BigDecimal;

public class Point {
    private Long userId;

    private Long createTime = System.currentTimeMillis();
    private BigDecimal balance = BigDecimal.ZERO;

    public Point(Long preAllocatedId) {
        this.userId = preAllocatedId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public BigDecimal balance() {
        return this.balance;
    }

    public void debit(BigDecimal amount) throws InsufficientBalanceException {
        if(this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("포인트 부족");
        }

        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) throws InvalidAmountException {
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("충전액 오류");
        }

        this.balance = this.balance.add(amount);
    }

}
