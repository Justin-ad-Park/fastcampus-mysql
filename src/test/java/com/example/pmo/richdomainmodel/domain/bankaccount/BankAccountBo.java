package com.example.pmo.richdomainmodel.domain.bankaccount;

import com.example.pmo.richdomainmodel.domain.common.exception.InsufficientBalanceException;
import com.example.pmo.richdomainmodel.domain.common.exception.InvalidAmountException;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class BankAccountBo {

    private Long accountId;
    private BigDecimal balance;

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
