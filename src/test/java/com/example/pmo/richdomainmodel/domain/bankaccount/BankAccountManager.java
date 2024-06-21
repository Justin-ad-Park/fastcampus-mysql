package com.example.pmo.richdomainmodel.domain.bankaccount;

import com.example.pmo.richdomainmodel.domain.common.exception.InsufficientBalanceException;
import com.example.pmo.richdomainmodel.domain.common.exception.InvalidAmountException;

import java.math.BigDecimal;

/**
 * 도메인 모델
 */
public class BankAccountManager {
    public BankAccountBo getAccount(Long accountId) {
        //
        return null;
    }

    public BankAccountBo debit(Long accountId, BigDecimal amount) throws InsufficientBalanceException {
        BankAccountBo bankAccountBo = this.getAccount(accountId);

        bankAccountBo.debit(amount);

        return bankAccountBo;
    }

    public BankAccountBo credit(Long accountId, BigDecimal amount) throws InvalidAmountException {
        BankAccountBo bankAccountBo = this.getAccount(accountId);

        bankAccountBo.credit(amount);

        return bankAccountBo;

    }

}
